package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.blatant.invwalk.InvWalkKeyLayout;
import gg.vape.utils.render.RenderBatchManager;
import gg.vape.utils.render.RenderBatchShaderProgram;
import gg.vape.utils.render.RenderMatrix4f;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

public class RenderBatchState {
    private FloatBuffer instanceDataBuffer = BufferUtils.createFloatBuffer((int)(this.instanceCapacity * INSTANCE_FLOAT_COUNT));
    private int cubeVertexBufferId;
    private int instanceBufferId;
    private static RenderBatchState instance;
    private int vertexArrayId;
    private int cubeIndexBufferId;
    private int previousFramebufferId;
    private int instanceCount;
    private int previousElementArrayBufferId;
    private int instanceCapacity = 4096;
    private static final int INSTANCE_FLOAT_COUNT;
    private int previousArrayBufferId;
    private int previousVertexArrayId;
    private boolean initialized = false;
    private int previousProgramId;
    private static final int CUBE_INDEX_COUNT;

    public void cleanup() {
        if (!this.initialized) {
            return;
        }
        GL15.glDeleteBuffers((int)this.cubeVertexBufferId);
        GL15.glDeleteBuffers((int)this.instanceBufferId);
        GL15.glDeleteBuffers((int)this.cubeIndexBufferId);
        GL30.glDeleteVertexArrays((int)this.vertexArrayId);
        this.initialized = false;
        Vape.debugLog("InstancedBlockRenderer cleaned up");
    }

    private void restoreGlBindings() {
        GL30.glBindVertexArray((int)this.previousVertexArrayId);
        GL20.glUseProgram((int)this.previousProgramId);
        GL15.glBindBuffer((int)34962, (int)this.previousArrayBufferId);
        GL15.glBindBuffer((int)34963, (int)this.previousElementArrayBufferId);
        GL30.glBindFramebuffer((int)36160, (int)this.previousFramebufferId);
    }

    public void addInstance(float x, float y, float z, float red, float green, float blue, float alpha) {
        if (this.instanceCount >= this.instanceCapacity) {
            this.growInstanceCapacity();
        }
        this.instanceDataBuffer.put(x);
        this.instanceDataBuffer.put(y);
        this.instanceDataBuffer.put(z);
        this.instanceDataBuffer.put(red);
        this.instanceDataBuffer.put(green);
        this.instanceDataBuffer.put(blue);
        this.instanceDataBuffer.put(alpha);
        ++this.instanceCount;
    }

    public void render(RenderMatrix4f projectionMatrix, RenderMatrix4f viewMatrix, RenderMatrix4f modelMatrix) {
        boolean cullFaceEnabled;
        if (this.instanceCount == 0 || !this.initialized) {
            return;
        }
        RenderBatchShaderProgram shaderProgram = InvWalkKeyLayout.blockEspShader;
        if (shaderProgram == null) {
            return;
        }
        this.captureGlBindings();
        int targetFramebufferId = RenderBatchManager.getInstance().getTargetFramebufferId();
        if (targetFramebufferId != -1) {
            GL30.glBindFramebuffer((int)36160, (int)targetFramebufferId);
        }
        if (cullFaceEnabled = GL11.glIsEnabled((int)2884)) {
            GL11.glDisable((int)2884);
            GL30.glBindVertexArray((int)this.vertexArrayId);
            shaderProgram.bind();
            FloatBuffer projectionBuffer = projectionMatrix.toFloatBuffer();
            FloatBuffer viewBuffer = viewMatrix.toFloatBuffer();
            FloatBuffer modelBuffer = modelMatrix.toFloatBuffer();
            gg.vape.wrapper.impl.GL20.w(shaderProgram.projectionUniformLocation, false, projectionBuffer);
            gg.vape.wrapper.impl.GL20.w(shaderProgram.viewUniformLocation, false, viewBuffer);
            gg.vape.wrapper.impl.GL20.w(shaderProgram.modelUniformLocation, false, modelBuffer);
            this.instanceDataBuffer.flip();
            GL15.glBindBuffer((int)34962, (int)this.instanceBufferId);
            GL15.glBufferSubData((int)34962, (long)0L, (FloatBuffer)this.instanceDataBuffer);
            GL31.glDrawElementsInstanced((int)1, (int)CUBE_INDEX_COUNT, (int)5125, (long)0L, (int)this.instanceCount);
            GL11.glEnable((int)2884);
            this.restoreGlBindings();
            return;
        }
        GL30.glBindVertexArray((int)this.vertexArrayId);
        shaderProgram.bind();
        FloatBuffer projectionBuffer = projectionMatrix.toFloatBuffer();
        FloatBuffer viewBuffer = viewMatrix.toFloatBuffer();
        FloatBuffer modelBuffer = modelMatrix.toFloatBuffer();
        gg.vape.wrapper.impl.GL20.w(shaderProgram.projectionUniformLocation, false, projectionBuffer);
        gg.vape.wrapper.impl.GL20.w(shaderProgram.viewUniformLocation, false, viewBuffer);
        gg.vape.wrapper.impl.GL20.w(shaderProgram.modelUniformLocation, false, modelBuffer);
        this.instanceDataBuffer.flip();
        GL15.glBindBuffer((int)34962, (int)this.instanceBufferId);
        GL15.glBufferSubData((int)34962, (long)0L, (FloatBuffer)this.instanceDataBuffer);
        GL31.glDrawElementsInstanced((int)1, (int)CUBE_INDEX_COUNT, (int)5125, (long)0L, (int)this.instanceCount);
        this.restoreGlBindings();
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }
        if (InvWalkKeyLayout.blockEspShader == null) {
            InvWalkKeyLayout.initializeShaders();
        }
        this.vertexArrayId = GL30.glGenVertexArrays();
        GL30.glBindVertexArray((int)this.vertexArrayId);
        float[] cubeVertices = new float[]{0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f};
        FloatBuffer cubeVertexBuffer = BufferUtils.createFloatBuffer((int)cubeVertices.length);
        cubeVertexBuffer.put(cubeVertices).flip();
        this.cubeVertexBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer((int)34962, (int)this.cubeVertexBufferId);
        GL15.glBufferData((int)34962, (FloatBuffer)cubeVertexBuffer, (int)35044);
        GL20.glVertexAttribPointer((int)0, (int)3, (int)5126, (boolean)false, (int)12, (long)0L);
        GL20.glEnableVertexAttribArray((int)0);
        int[] cubeIndices = new int[]{0, 1, 1, 2, 2, 3, 3, 0, 4, 5, 5, 6, 6, 7, 7, 4, 0, 4, 1, 5, 2, 6, 3, 7};
        IntBuffer cubeIndexBuffer = BufferUtils.createIntBuffer((int)cubeIndices.length);
        cubeIndexBuffer.put(cubeIndices).flip();
        this.cubeIndexBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer((int)34963, (int)this.cubeIndexBufferId);
        GL15.glBufferData((int)34963, (IntBuffer)cubeIndexBuffer, (int)35044);
        this.instanceBufferId = GL15.glGenBuffers();
        GL15.glBindBuffer((int)34962, (int)this.instanceBufferId);
        GL15.glBufferData((int)34962, (long)((long)this.instanceCapacity * (long)INSTANCE_FLOAT_COUNT * 4L), (int)35048);
        GL20.glVertexAttribPointer((int)1, (int)3, (int)5126, (boolean)false, (int)28, (long)0L);
        GL20.glEnableVertexAttribArray((int)1);
        GL33.glVertexAttribDivisor((int)1, (int)1);
        GL20.glVertexAttribPointer((int)2, (int)4, (int)5126, (boolean)false, (int)28, (long)12L);
        GL20.glEnableVertexAttribArray((int)2);
        GL33.glVertexAttribDivisor((int)2, (int)1);
        GL30.glBindVertexArray((int)0);
        this.initialized = true;
        Vape.debugLog("InstancedBlockRenderer initialized (capacity: " + this.instanceCapacity + " blocks)");
    }

    private void growInstanceCapacity() {
        int newCapacity = this.instanceCapacity * 2;
        FloatBuffer expandedBuffer = BufferUtils.createFloatBuffer((int)(newCapacity * INSTANCE_FLOAT_COUNT));
        this.instanceDataBuffer.flip();
        expandedBuffer.put(this.instanceDataBuffer);
        this.instanceDataBuffer = expandedBuffer;
        GL15.glBindBuffer((int)34962, (int)this.instanceBufferId);
        GL15.glBufferData((int)34962, (long)((long)newCapacity * (long)INSTANCE_FLOAT_COUNT * 4L), (int)35048);
        this.instanceCapacity = newCapacity;
    }

    private RenderBatchState() {
    }

    public static void cleanupInstance() {
        if (instance != null) {
            instance.cleanup();
            instance = null;
        }
    }

    public static RenderBatchState getInstance() {
        if (instance == null) {
            instance = new RenderBatchState();
        }
        return instance;
    }

    public int getInstanceCount() {
        return this.instanceCount;
    }

    public void beginBatch() {
        if (!this.initialized) {
            this.initialize();
        }
        this.instanceDataBuffer.clear();
        this.instanceCount = 0;
    }

    static {
        CUBE_INDEX_COUNT = 24;
        INSTANCE_FLOAT_COUNT = 7;
    }


    private void captureGlBindings() {
        this.previousVertexArrayId = GL11.glGetInteger((int)34229);
        this.previousProgramId = GL11.glGetInteger((int)35725);
        this.previousArrayBufferId = GL11.glGetInteger((int)34964);
        this.previousElementArrayBufferId = GL11.glGetInteger((int)34965);
        this.previousFramebufferId = GL11.glGetInteger((int)36006);
    }
}

