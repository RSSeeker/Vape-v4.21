package gg.vape.module.other;

import gg.vape.Vape;
import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPostRenderTick;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.none.ClientSettings;
import gg.vape.value.BooleanValue;
import gg.vape.value.NumberValue;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 * 动态模糊（帧混合后处理）。
 *
 * 每帧渲染结束后（SwapBuffers 前），把当前帧缓冲拷贝到 current 纹理，与上一帧
 * 混合结果（history 纹理）按 blurriness 混合后覆盖回帧缓冲，再把混合结果存入
 * history，形成残影/动态模糊效果。
 *
 * 仅在 1.17+（现代 GL 渲染器可用）且 26.2 之前注册。
 */
public class MotionBlur
extends Mod {
    private static final int MODULE_COLOR = new Color(52, 120, 246).getRGB();
    private final NumberValue blurrinessValue;
    private final BooleanValue velocityAdaptiveValue;
    private final BooleanValue smoothBlurValue;
    private final BooleanValue fpsModulateValue;
    private final BooleanValue clearColorValue;
    private final BooleanValue applyOnMenuValue;
    private final BooleanValue applyOnGameMenuValue;
    private boolean initialized;
    private int shaderProgram;
    private int currentTexture;
    private int historyTexture;
    private int quadVao;
    private int quadVbo;
    private int textureWidth;
    private int textureHeight;
    private boolean first;
    private float velocityFactor;
    private float curBlurriness;
    private long lastFrameNanos;
    private float lastViewX;
    private float lastViewY;

    private static final String VERTEX_SHADER = "#version 330 core\n"
            + "layout (location = 0) in vec2 aPos;\n"
            + "layout (location = 1) in vec2 aTexCoord;\n"
            + "out vec2 TexCoord;\n"
            + "void main()\n"
            + "{\n"
            + "    gl_Position = vec4(aPos, 0.0, 1.0);\n"
            + "    TexCoord = aTexCoord;\n"
            + "}\n";

    private static final String FRAGMENT_SHADER = "#version 330 core\n"
            + "out vec4 FragColor;\n"
            + "in vec2 TexCoord;\n"
            + "uniform sampler2D currentTexture;\n"
            + "uniform sampler2D historyTexture;\n"
            + "uniform float blurriness;\n"
            + "uniform float velocity_factor;\n"
            + "uniform bool renderRGB;\n"
            + "uniform bool smooth_blur;\n"
            + "vec4 blurHistory(vec2 uv)\n"
            + "{\n"
            + "    float offset = 0.0006 * velocity_factor;\n"
            + "    vec4 sum = vec4(0.0);\n"
            + "    sum += texture(historyTexture, uv + vec2(-offset, 0.0)) * 0.25;\n"
            + "    sum += texture(historyTexture, uv + vec2( offset, 0.0)) * 0.25;\n"
            + "    sum += texture(historyTexture, uv + vec2(0.0, -offset)) * 0.25;\n"
            + "    sum += texture(historyTexture, uv + vec2(0.0,  offset)) * 0.25;\n"
            + "    return sum;\n"
            + "}\n"
            + "void main()\n"
            + "{\n"
            + "    vec4 current = texture(currentTexture, TexCoord);\n"
            + "    vec4 history = texture(historyTexture, TexCoord);\n"
            + "    float cur_blurriness = blurriness;\n"
            + "    vec4 blurredHistory = history;\n"
            + "    if (velocity_factor > 0.0) {\n"
            + "        float base_blurriness = blurriness * 0.5;\n"
            + "        cur_blurriness = base_blurriness + velocity_factor * base_blurriness;\n"
            + "        if (smooth_blur) {\n"
            + "            blurredHistory = blurHistory(TexCoord);\n"
            + "        }\n"
            + "    }\n"
            + "    vec4 blurredColor = mix(current, blurredHistory, cur_blurriness);\n"
            + "    if (renderRGB) {\n"
            + "        FragColor = blurredColor;\n"
            + "    } else {\n"
            + "        float value1 = current.r;\n"
            + "        FragColor = mix(vec4(value1), blurredHistory, cur_blurriness);\n"
            + "    }\n"
            + "}\n";

    public MotionBlur() {
        super("MotionBlur", MODULE_COLOR, Category.OTHER,
                "Frame-blending motion blur");
        this.blurrinessValue = NumberValue.create((Object)this,
                "Blurriness", "#.#", "", 0.0, 5.0, 10.0, 0.1,
                "How strong the motion blur trail is");
        this.velocityAdaptiveValue = BooleanValue.create(this,
                "Velocity Adaptive", true,
                "Scales the blur strength with camera movement speed");
        this.smoothBlurValue = BooleanValue.create(this,
                "Smooth Blur", false,
                "Smooths the blur trail (may blur the game UI too)");
        this.fpsModulateValue = BooleanValue.create(this,
                "FPS Modulate", true,
                "Reduces blur strength at low frame rates");
        this.clearColorValue = BooleanValue.create(this,
                "Clear Color", false,
                "Renders a faded grayscale trail instead of RGB");
        this.applyOnMenuValue = BooleanValue.create(this,
                "Apply On Menu", true,
                "Keep applying the blur while the Vape GUI is open");
        this.applyOnGameMenuValue = BooleanValue.create(this,
                "Apply On Game Menu", true,
                "Keep applying the blur while a game menu (pause/inventory/chat) is open");
        this.addValue(this.blurrinessValue, this.velocityAdaptiveValue,
                this.smoothBlurValue, this.fpsModulateValue,
                this.clearColorValue, this.applyOnMenuValue,
                this.applyOnGameMenuValue);
        this.velocityFactor = 0.0f;
        this.curBlurriness = 5.0f;
    }

    @Override
    public void onEnable() {
        this.first = true;
        this.velocityFactor = 0.0f;
        this.curBlurriness = this.blurrinessValue.getValue().floatValue();
        this.lastFrameNanos = 0L;
    }

    @Override
    public void onDisable() {
        this.destroyResources();
    }

    @EventHandler
    public void onPostRenderTick(EventPostRenderTick event) {
        if (!this.isEnabled() || ForgeVersion.MC_26_2.d()) {
            return;
        }
        try {
            this.renderMotionBlur();
        }
        catch (Throwable throwable) {
            // 渲染失败不应影响游戏；记录一次并禁用模块避免刷屏。
            Vape.logThrowable(throwable);
            this.destroyResources();
            this.setEnabled(false);
        }
    }

    private void renderMotionBlur() {
        if (!this.applyOnMenuValue.getEffectiveValue().booleanValue()
                && !ClientSettings.INSTANCE.isInputEnabled()) {
            return;
        }
        if (!this.applyOnGameMenuValue.getEffectiveValue().booleanValue()
                && Minecraft.currentScreen().getObject() != null) {
            return;
        }
        int width = Minecraft.J();
        int height = Minecraft.h();
        if (width <= 0 || height <= 0) {
            return;
        }
        int previousFramebuffer = GL11.glGetInteger((int)36006);
        IntBuffer viewport = BufferUtils.createIntBuffer(4);
        gg.vape.wrapper.impl.GL11.X((int)2978, viewport);
        GL11.glViewport(0, 0, width, height);

        if (!this.initialized) {
            this.initializeTextures(width, height);
            this.initializeQuad();
            this.initializeShader();
            this.initialized = true;
            this.textureWidth = width;
            this.textureHeight = height;
            this.first = true;
        }
        if (this.textureWidth != width || this.textureHeight != height) {
            this.resizeTextures(width, height);
            this.first = true;
        }

        GL30.glBindFramebuffer((int)36008, (int)previousFramebuffer);
        this.copyToCurrent();
        if (this.first) {
            this.copyToHistory();
            this.first = false;
        }

        if (this.velocityAdaptiveValue.getEffectiveValue().booleanValue()) {
            this.updateVelocityFactor();
        } else {
            this.velocityFactor = 1.0f;
        }
        if (this.fpsModulateValue.getEffectiveValue().booleanValue()) {
            this.updateFpsModulation();
        } else {
            this.curBlurriness = this.blurrinessValue.getValue().floatValue();
        }

        GL30.glBindFramebuffer((int)36009, (int)previousFramebuffer);
        this.drawTexture();
        this.copyToHistory();

        GL30.glBindFramebuffer((int)36160, (int)previousFramebuffer);
        GL11.glViewport(viewport.get(0), viewport.get(1),
                viewport.get(2), viewport.get(3));
    }

    private void updateVelocityFactor() {
        float viewX = Minecraft.D().getPlayerViewX();
        float viewY = Minecraft.D().getPlayerViewY();
        float delta = Math.abs(viewX - this.lastViewX)
                + Math.abs(viewY - this.lastViewY);
        this.lastViewX = viewX;
        this.lastViewY = viewY;
        float target = Math.max(0.0f, Math.min(15.0f, delta * 10.0f - 1.0f)) / 15.0f;
        if (delta > 0.01f) {
            this.velocityFactor = target;
        }
    }

    private void updateFpsModulation() {
        long now = System.nanoTime();
        if (this.lastFrameNanos != 0L) {
            float fps = 1.0E9f / (float)(now - this.lastFrameNanos);
            float normalized = Math.max(0.0f, Math.min(1000.0f, fps)) / 1000.0f;
            float attenuation = (float)Math.pow(normalized, 0.2);
            this.curBlurriness = this.blurrinessValue.getValue().floatValue() * attenuation;
        } else {
            this.curBlurriness = this.blurrinessValue.getValue().floatValue();
        }
        this.lastFrameNanos = now;
    }

    private void initializeTextures(int width, int height) {
        this.currentTexture = GL11.glGenTextures();
        this.historyTexture = GL11.glGenTextures();
        this.configureTexture(this.currentTexture, width, height);
        this.configureTexture(this.historyTexture, width, height);
    }

    private void configureTexture(int textureId, int width, int height) {
        GL11.glBindTexture((int)3553, (int)textureId);
        GL11.glTexImage2D((int)3553, (int)0, (int)32856, width, height,
                (int)0, (int)6408, (int)5121, (java.nio.ByteBuffer)null);
        GL11.glTexParameteri((int)3553, (int)10241, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10240, (int)9729);
        GL11.glTexParameteri((int)3553, (int)10242, (int)33071);
        GL11.glTexParameteri((int)3553, (int)10243, (int)33071);
        GL11.glBindTexture((int)3553, (int)0);
    }

    private void resizeTextures(int width, int height) {
        this.configureTexture(this.currentTexture, width, height);
        this.configureTexture(this.historyTexture, width, height);
        this.textureWidth = width;
        this.textureHeight = height;
    }

    private void initializeQuad() {
        float[] vertexData = new float[]{
            -1.0f, -1.0f, 0.0f, 0.0f,
             1.0f, -1.0f, 1.0f, 0.0f,
            -1.0f,  1.0f, 0.0f, 1.0f,
             1.0f,  1.0f, 1.0f, 1.0f
        };
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexData.length);
        vertexBuffer.put(vertexData).flip();
        this.quadVao = GL30.glGenVertexArrays();
        this.quadVbo = GL15.glGenBuffers();
        GL30.glBindVertexArray(this.quadVao);
        GL15.glBindBuffer((int)34962, (int)this.quadVbo);
        GL15.glBufferData((int)34962, vertexBuffer, (int)35044);
        GL20.glVertexAttribPointer((int)0, (int)2, (int)5126, (boolean)false,
                (int)16, (long)0L);
        GL20.glEnableVertexAttribArray((int)0);
        GL20.glVertexAttribPointer((int)1, (int)2, (int)5126, (boolean)false,
                (int)16, (long)8L);
        GL20.glEnableVertexAttribArray((int)1);
        GL15.glBindBuffer((int)34962, (int)0);
        GL30.glBindVertexArray((int)0);
    }

    private void initializeShader() {
        int vertexShader = this.compileShader(35633, VERTEX_SHADER);
        int fragmentShader = this.compileShader(35632, FRAGMENT_SHADER);
        this.shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(this.shaderProgram, vertexShader);
        GL20.glAttachShader(this.shaderProgram, fragmentShader);
        GL20.glLinkProgram(this.shaderProgram);
        if (GL20.glGetProgrami(this.shaderProgram, 35714) == 0) {
            throw new IllegalStateException("MotionBlur shader link failed: "
                    + GL20.glGetProgramInfoLog(this.shaderProgram, 8224));
        }
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);
    }

    private int compileShader(int type, String source) {
        int shaderId = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderId, (CharSequence)source);
        GL20.glCompileShader(shaderId);
        if (GL20.glGetShaderi(shaderId, 35713) == 0) {
            throw new IllegalStateException("MotionBlur shader compile failed: "
                    + GL20.glGetShaderInfoLog(shaderId, 512));
        }
        return shaderId;
    }

    private void copyToCurrent() {
        GL11.glBindTexture((int)3553, (int)this.currentTexture);
        GL11.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0,
                (int)0, (int)0, this.textureWidth, this.textureHeight);
        GL11.glBindTexture((int)3553, (int)0);
    }

    private void copyToHistory() {
        GL11.glBindTexture((int)3553, (int)this.historyTexture);
        GL11.glCopyTexSubImage2D((int)3553, (int)0, (int)0, (int)0,
                (int)0, (int)0, this.textureWidth, this.textureHeight);
        GL11.glBindTexture((int)3553, (int)0);
    }

    private void drawTexture() {
        boolean depthEnabled = GL11.glIsEnabled((int)2929);
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)3042);
        int previousProgram = GL11.glGetInteger((int)35725);
        int previousTexture = GL11.glGetInteger((int)32873);

        GL20.glUseProgram(this.shaderProgram);
        GL13.glActiveTexture((int)33984);
        GL11.glBindTexture((int)3553, (int)this.currentTexture);
        GL20.glUniform1i(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"currentTexture"), 0);
        GL13.glActiveTexture((int)33985);
        GL11.glBindTexture((int)3553, (int)this.historyTexture);
        GL20.glUniform1i(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"historyTexture"), 1);
        GL20.glUniform1f(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"blurriness"), this.curBlurriness / 11.0f);
        GL20.glUniform1f(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"velocity_factor"), this.velocityFactor);
        GL20.glUniform1i(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"renderRGB"), this.clearColorValue.getEffectiveValue() ? 0 : 1);
        GL20.glUniform1i(GL20.glGetUniformLocation(this.shaderProgram,
                (CharSequence)"smooth_blur"), this.smoothBlurValue.getEffectiveValue() ? 1 : 0);

        GL30.glBindVertexArray(this.quadVao);
        GL11.glDrawArrays((int)5, (int)0, (int)4);
        GL30.glBindVertexArray((int)0);

        GL13.glActiveTexture((int)33984);
        GL11.glBindTexture((int)3553, (int)previousTexture);
        GL20.glUseProgram(previousProgram);
        if (depthEnabled) {
            GL11.glEnable((int)2929);
        }
        if (blendEnabled) {
            GL11.glEnable((int)3042);
        }
    }

    private void destroyResources() {
        if (this.shaderProgram != 0) {
            GL20.glDeleteProgram(this.shaderProgram);
        }
        if (this.currentTexture != 0) {
            GL11.glDeleteTextures(this.currentTexture);
        }
        if (this.historyTexture != 0) {
            GL11.glDeleteTextures(this.historyTexture);
        }
        if (this.quadVao != 0) {
            GL30.glDeleteVertexArrays(this.quadVao);
        }
        if (this.quadVbo != 0) {
            GL15.glDeleteBuffers(this.quadVbo);
        }
        this.shaderProgram = 0;
        this.currentTexture = 0;
        this.historyTexture = 0;
        this.quadVao = 0;
        this.quadVbo = 0;
        this.initialized = false;
    }
}
