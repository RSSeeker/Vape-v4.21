package gg.vape.utils.render;

import gg.vape.Vape;
import gg.vape.module.blatant.invwalk.InvWalkKeyLayout;
import gg.vape.rotation.LocalPlayerRotationUtil;
import gg.vape.utils.render.BufferedGuiRenderPrimitives;
import gg.vape.utils.render.GlCapabilityState;
import gg.vape.utils.render.GlImageTexture;
import gg.vape.utils.render.GlScissorRect;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.OpenGlDeviceInfo;
import gg.vape.utils.render.PrimitiveTopology;
import gg.vape.utils.render.RenderBatch;
import gg.vape.utils.render.RenderBatchBuffer;
import gg.vape.utils.render.RenderBatchBuilder;
import gg.vape.utils.render.RenderBatchShaderProgram;
import gg.vape.utils.render.RenderBatchState;
import gg.vape.utils.render.RenderBatchStateFlags;
import gg.vape.utils.render.RenderMatrixStack;
import gg.vape.utils.render.TextureAtlasRegistry;
import gg.vape.utils.render.VertexAttributeType;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.GlStateManager;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.TextureManagerHandle;
import gg.vape.wrapper.impl.TextureObjectHandle;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;
import java.util.function.Supplier;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderBatchManager {
    private int savedElementArrayBufferId;
    private RenderBatchBuilder lastWorldBuilder;
    private int targetFramebufferId = 999;
    private int savedArrayBufferId;
    private int savedFramebufferId;
    private int savedReadFramebufferId;
    private int previousTargetFramebufferId = 0;
    private static RenderBatchManager instance;
    private static final long FRAMEBUFFER_REFRESH_INTERVAL_MS = 1000L;
    public RenderBatchBuffer batchBuffer;
    private int savedTextureId;
    private int savedProgramId;
    private boolean batchResourcesBound;
    private int savedVertexArrayId;
    private final Deque<RenderBatchStateFlags> savedGlStates = new ArrayDeque<RenderBatchStateFlags>();
    private long lastFramebufferRefreshTime = 0L;
    private boolean framebufferFailureLogged;
    private int ownedFramebufferId = -1;
    private int ownedColorTextureId = -1;
    private int ownedDepthTextureId = -1;
    private Object ownedColorTexture;
    private Object ownedDepthTexture;
    private RenderBatchBuilder lastGuiBuilder;
    public ArrayList<RenderBatch> worldBatches = new ArrayList();
    public RenderBatchShaderProgram shaderProgram;
    public ArrayList<RenderBatch> guiBatches = new ArrayList();

    private boolean shouldStartNewBatch(RenderBatchBuilder builder, RenderBatchBuilder previousBuilder, boolean worldSpace) {
        if (previousBuilder == null) {
            return true;
        }
        if (builder.getCoordinateMode() != previousBuilder.getCoordinateMode()) {
            return true;
        }
        GlImageTexture texture = builder.getTexture();
        GlImageTexture previousTexture = previousBuilder.getTexture();
        if (texture != previousTexture) {
            if (texture == null || previousTexture == null) {
                return true;
            }
            if (texture.textureId != previousTexture.textureId) {
                return true;
            }
        }
        if (builder.getDrawSetupCallback() != null || previousBuilder.getDrawSetupCallback() != null) {
            return true;
        }
        PrimitiveTopology topology = builder.getTopology();
        PrimitiveTopology previousTopology = previousBuilder.getTopology();
        if (!Objects.equals((Object)topology, (Object)previousTopology)) {
            return true;
        }
        if (!builder.modelMatrix.contentEquals(previousBuilder.modelMatrix)) {
            return true;
        }
        GlCapabilityState capabilityState = builder.getCapabilityState();
        GlCapabilityState previousCapabilityState = previousBuilder.getCapabilityState();
        if (!Objects.equals(capabilityState, previousCapabilityState)) {
            return true;
        }
        GlScissorRect scissorRect = builder.getScissorRect();
        GlScissorRect previousScissorRect = previousBuilder.getScissorRect();
        if (!Objects.equals(scissorRect, previousScissorRect)) {
            return true;
        }
        return this.exceedsBatchCapacity(builder, worldSpace);
    }

    public int getLastBatchMergeCount(boolean worldSpace) {
        try {
            if (worldSpace) {
                return this.worldBatches.get(this.worldBatches.size() - 1).getMergedBuilderCount();
            }
            return this.guiBatches.get(this.guiBatches.size() - 1).getMergedBuilderCount();
        }
        catch (Exception exception) {
            return 0;
        }
    }

    public int getTargetFramebufferId() {
        long currentTime = System.currentTimeMillis();
        if (this.targetFramebufferId == 999 || currentTime - this.lastFramebufferRefreshTime >= FRAMEBUFFER_REFRESH_INTERVAL_MS && this.previousTargetFramebufferId == 0) {
            this.lastFramebufferRefreshTime = currentTime;
            int previousFramebufferId = this.targetFramebufferId;
            this.refreshTargetFramebuffer();
            if (previousFramebufferId == 999 || previousFramebufferId == this.targetFramebufferId || this.targetFramebufferId != -1) {
                // empty if block
            }
        }
        return this.targetFramebufferId;
    }

    public static RenderBatchManager getInstance() {
        if (!GuiRenderPrimitives.d()) {
            throw new IllegalStateException("Attempting to call RenderEngine on an older version of OpenGL");
        }
        if (instance == null) {
            instance = new RenderBatchManager();
        }
        return instance;
    }

    public void setFramebufferOverride(int framebufferId) {
        this.previousTargetFramebufferId = this.getTargetFramebufferId();
        this.targetFramebufferId = framebufferId;
    }

    private boolean exceedsBatchCapacity(RenderBatchBuilder builder, boolean worldSpace) {
        if (builder.getVertexData() == null || builder.getIndices() == null) {
            return false;
        }
        RenderBatch renderBatch = worldSpace ? this.worldBatches.get(this.worldBatches.size() - 1) : this.guiBatches.get(this.guiBatches.size() - 1);
        return renderBatch.getBuilders().size() >= 4500;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void flushGuiBatches(float partialTicks, boolean resetProjection) {
        if (this.guiBatches.isEmpty()) {
            return;
        }
        if (resetProjection) {
            LocalPlayerRotationUtil.resetProjectionMatrix();
        }
        this.captureGlBindings();
        this.saveAndPrepareGlState();
        try {
            for (RenderBatch renderBatch : this.guiBatches) {
                this.ensureBatchResourcesBound();
                boolean scissorTestEnabled = GL11.glIsEnabled((int)3089);
                if (renderBatch.hasScissorRect()) {
                    if (!scissorTestEnabled) {
                        OpenGlBackendHolder.backend.enableCapability(3089);
                    }
                    GlScissorRect glScissorRect = renderBatch.getScissorRect();
                    GL11.glScissor((int)glScissorRect.x, (int)glScissorRect.y, (int)glScissorRect.width, (int)glScissorRect.height);
                } else if (scissorTestEnabled) {
                    OpenGlBackendHolder.backend.disableCapability(3089);
                }
                if (!renderBatch.getStandaloneRenderCallbacks().isEmpty()) {
                    this.unbindBatchVertexArray();
                    this.restoreCapturedGlBindings();
                    OpenGlBackendHolder.backend.disableCapability(3089);
                    for (Supplier supplier : renderBatch.getStandaloneRenderCallbacks()) {
                        supplier.get();
                    }
                    this.captureGlBindings();
                    continue;
                }
                if (renderBatch.getDrawSetupCallback() != null) {
                    renderBatch.getDrawSetupCallback().get();
                }
                this.batchBuffer.stageBatch(renderBatch);
                if (!this.batchResourcesBound) continue;
                this.batchBuffer.draw();
            }
        }
        finally {
            this.guiBatches.clear();
            this.lastGuiBuilder = null;
            OpenGlBackendHolder.backend.disableCapability(3089);
            this.unbindBatchVertexArray();
            this.restoreSavedGlState();
            this.restoreCapturedGlBindings();
            BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
            if (resetProjection) {
                LocalPlayerRotationUtil.updateProjectionMatrix(partialTicks);
            }
        }
    }

    private void unbindBatchVertexArray() {
        if (this.batchResourcesBound) {
            GL30.glBindVertexArray((int)0);
            this.batchResourcesBound = false;
        }
    }

    public void flushGuiBatches(float partialTicks) {
        this.flushGuiBatches(partialTicks, true);
    }

    private static Throwable propagateThrowable(Throwable throwable) {
        return throwable;
    }

    public static String buildInitializationDiagnostics(String phase, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to initialize modern renderer\n");
        stringBuilder.append("Phase: ").append(phase).append('\n');
        OpenGlDeviceInfo.appendDeviceInfo(stringBuilder);
        try {
            stringBuilder.append("Current Program: ").append(GL11.glGetInteger((int)35725)).append('\n');
            stringBuilder.append("Current VAO: ").append(GL11.glGetInteger((int)34229)).append('\n');
            stringBuilder.append("Array Buffer: ").append(GL11.glGetInteger((int)34964)).append('\n');
            stringBuilder.append("Element Buffer: ").append(GL11.glGetInteger((int)34965)).append('\n');
            stringBuilder.append("Framebuffer: ").append(GL11.glGetInteger((int)36006)).append('\n');
            stringBuilder.append("GL Error: ").append(GL11.glGetError()).append('\n');
        }
        catch (Throwable throwable2) {
            stringBuilder.append("GL Diagnostics Error: ").append(throwable2.getClass().getSimpleName()).append(": ").append(throwable2.getMessage()).append('\n');
        }
        if (throwable != null) {
            stringBuilder.append("Exception: ").append(throwable.getClass().getName());
            if (throwable.getMessage() != null) {
                stringBuilder.append(": ").append(throwable.getMessage());
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }

    public void restoreRendererState() {
        GL30.glBindVertexArray((int)0);
        this.restoreSavedGlState();
        GL30.glBindVertexArray((int)this.savedVertexArrayId);
        GL20.glUseProgram((int)this.savedProgramId);
        GL11.glBindTexture((int)3553, (int)this.savedTextureId);
        GL30.glBindFramebuffer((int)36009, (int)this.savedFramebufferId);
        GL30.glBindFramebuffer((int)36008, (int)this.savedReadFramebufferId);
        GL15.glBindBuffer((int)34962, (int)this.savedArrayBufferId);
        GL15.glBindBuffer((int)34963, (int)this.savedElementArrayBufferId);
        this.batchResourcesBound = false;
    }

    public void queueWorldBatch(RenderBatchBuilder builder) {
        if (this.shouldStartNewBatch(builder, this.lastWorldBuilder, true)) {
            this.worldBatches.add(new RenderBatch(builder));
        } else {
            RenderBatch renderBatch = this.worldBatches.get(this.worldBatches.size() - 1);
            renderBatch.incrementMergedBuilderCount();
            renderBatch.addBuilder(builder);
        }
        this.lastWorldBuilder = builder;
    }

    private void restoreSavedGlState() {
        if (this.savedGlStates.isEmpty()) {
            return;
        }
        RenderBatchStateFlags renderBatchStateFlags = this.savedGlStates.pop();
        if (renderBatchStateFlags.blendEnabled) {
            GlStateManager.enableBlend();
        } else {
            GlStateManager.disableBlend();
        }
        if (renderBatchStateFlags.cullFaceEnabled) {
            OpenGlBackendHolder.backend.enableCapability(2884);
        } else {
            OpenGlBackendHolder.backend.disableCapability(2884);
        }
        if (renderBatchStateFlags.depthTestEnabled) {
            GlStateManager.enableDepth();
        } else {
            GlStateManager.disableDepth();
        }
        GL11.glDepthMask((boolean)renderBatchStateFlags.depthWriteEnabled);
    }

    public RenderBatchBuffer getBatchBuffer() {
        return this.batchBuffer;
    }

    public void queueGuiBatch(RenderBatchBuilder builder) {
        if (builder.getTexture() == null) {
            builder.setTexture(TextureAtlasRegistry.getInstance().get("vape_texture").getTexture());
        }
        if (this.shouldStartNewBatch(builder, this.lastGuiBuilder, false)) {
            this.guiBatches.add(new RenderBatch(builder));
        } else {
            RenderBatch renderBatch = this.guiBatches.get(this.guiBatches.size() - 1);
            renderBatch.incrementMergedBuilderCount();
            renderBatch.addBuilder(builder);
        }
        this.lastGuiBuilder = builder;
    }

    private void captureGlBindings() {
        this.savedVertexArrayId = GL11.glGetInteger((int)34229);
        this.savedProgramId = GL11.glGetInteger((int)35725);
        this.savedTextureId = GL11.glGetInteger((int)32873);
        this.savedFramebufferId = GL11.glGetInteger((int)36006);
        this.savedReadFramebufferId = GL11.glGetInteger((int)36010);
        this.savedArrayBufferId = GL11.glGetInteger((int)34964);
        this.savedElementArrayBufferId = GL11.glGetInteger((int)34965);
    }

    public static IllegalStateException initializationFailure(String phase, Throwable throwable) {
        return new IllegalStateException(RenderBatchManager.buildInitializationDiagnostics(phase, throwable), throwable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void flushWorldBatches(float partialTicks) {
        if (this.worldBatches.isEmpty()) {
            return;
        }
        this.captureGlBindings();
        this.saveAndPrepareGlState();
        try {
            LocalPlayerRotationUtil.updateProjectionMatrix(partialTicks);
            for (RenderBatch renderBatch : this.worldBatches) {
                this.ensureBatchResourcesBound();
                renderBatch.getCapabilityState().apply();
                if (!renderBatch.getStandaloneRenderCallbacks().isEmpty()) {
                    this.unbindBatchVertexArray();
                    this.restoreCapturedGlBindings();
                    GlStateManager.enableBlend();
                    for (Supplier<Void> supplier : renderBatch.getStandaloneRenderCallbacks()) {
                        supplier.get();
                    }
                    this.captureGlBindings();
                    continue;
                }
                this.batchBuffer.stageBatch(renderBatch);
                if (!this.batchResourcesBound) continue;
                this.batchBuffer.draw();
            }
        }
        finally {
            this.lastWorldBuilder = null;
            this.worldBatches.clear();
            this.unbindBatchVertexArray();
            this.restoreSavedGlState();
            this.restoreCapturedGlBindings();
            BufferedGuiRenderPrimitives.matrixStack = new RenderMatrixStack();
            LocalPlayerRotationUtil.resetProjectionMatrix();
        }
    }

    public void restoreFramebufferOverride() {
        this.targetFramebufferId = this.previousTargetFramebufferId;
        this.previousTargetFramebufferId = 0;
    }

    private void bindBatchResources() {
        int targetFramebufferId = this.getTargetFramebufferId();
        if (targetFramebufferId != -1) {
            GL30.glBindFramebuffer((int)36160, (int)targetFramebufferId);
        }
        this.batchBuffer.bindResources();
        this.batchResourcesBound = true;
    }

    public static void shutdown() {
        RenderBatchState.cleanupInstance();
        InvWalkKeyLayout.clearShaders();
        if (instance != null) {
            instance.deleteOwnedFramebuffer();
        }
        instance = null;
    }

    public void refreshTargetFramebuffer() {
        if (ForgeVersion.MC_1_21_10.d()) {
            try {
                TextureManagerHandle mainRenderTarget = Minecraft.getMainRenderTarget();
                TextureObjectHandle colorTexture;
                if (mainRenderTarget != null && mainRenderTarget.isNotNull() && (colorTexture = mainRenderTarget.getColorTexture()) != null && colorTexture.isNotNull()) {
                    TextureObjectHandle depthTexture = mainRenderTarget.getDepthTexture();
                    int depthTextureId = depthTexture != null && depthTexture.isNotNull() ? depthTexture.getId() : 0;
                    int resolvedFramebufferId;
                    if (ForgeVersion.MC_26_2.d()) {
                        resolvedFramebufferId = this.resolveOwnedFramebuffer(
                                colorTexture, depthTexture, colorTexture.getId(), depthTextureId);
                    } else {
                        resolvedFramebufferId = colorTexture.resolveFramebufferId(depthTextureId);
                    }
                    if (resolvedFramebufferId <= 0) {
                        this.targetFramebufferId = -1;
                        if (ForgeVersion.MC_26_2.d()) {
                            this.deleteOwnedFramebuffer();
                        }
                        this.logFramebufferFailure(
                                "framebuffer resolution returned " + resolvedFramebufferId, null);
                        return;
                    }
                    this.targetFramebufferId = resolvedFramebufferId;
                    return;
                }
                this.logFramebufferFailure("main render target or color texture is unavailable", null);
            }
            catch (RuntimeException | LinkageError error) {
                this.logFramebufferFailure("framebuffer resolution threw an exception", error);
            }
        }
        if (ForgeVersion.MC_26_2.d()) {
            this.deleteOwnedFramebuffer();
        }
        this.targetFramebufferId = -1;
    }

    private int resolveOwnedFramebuffer(TextureObjectHandle colorTexture,
            TextureObjectHandle depthTexture, int colorTextureId, int depthTextureId) {
        if (colorTextureId <= 0 || depthTexture == null || depthTexture.isNull()
                || depthTextureId <= 0) {
            return -1;
        }
        Object colorTextureObject = colorTexture.getObject();
        Object depthTextureObject = depthTexture.getObject();
        if (this.ownedFramebufferId > 0
                && this.ownedColorTextureId == colorTextureId
                && this.ownedDepthTextureId == depthTextureId
                && this.ownedColorTexture == colorTextureObject
                && this.ownedDepthTexture == depthTextureObject) {
            return this.ownedFramebufferId;
        }

        int previousDrawFramebufferId = GL11.glGetInteger((int)36006);
        int previousReadFramebufferId = GL11.glGetInteger((int)36010);
        int previousOwnedFramebufferId = this.ownedFramebufferId;
        int framebufferId = GL30.glGenFramebuffers();
        boolean framebufferCreated = false;
        try {
            if (framebufferId <= 0) {
                return -1;
            }
            GL30.glBindFramebuffer((int)36160, (int)framebufferId);
            GL30.glFramebufferTexture2D((int)36160, (int)36064, (int)3553,
                    (int)colorTextureId, (int)0);
            GL30.glFramebufferTexture2D((int)36160, (int)36096, (int)3553,
                    (int)depthTextureId, (int)0);
            GL11.glDrawBuffer((int)36064);
            GL11.glReadBuffer((int)36064);
            int status = GL30.glCheckFramebufferStatus((int)36160);
            if (status != 36053) {
                this.logFramebufferFailure("owned framebuffer is incomplete: 0x"
                        + Integer.toHexString(status), null);
                return -1;
            }
            framebufferCreated = true;
            this.ownedFramebufferId = framebufferId;
            this.ownedColorTextureId = colorTextureId;
            this.ownedDepthTextureId = depthTextureId;
            this.ownedColorTexture = colorTextureObject;
            this.ownedDepthTexture = depthTextureObject;
            return framebufferId;
        }
        finally {
            int restoredDrawFramebufferId = previousDrawFramebufferId == previousOwnedFramebufferId
                    ? (framebufferCreated ? framebufferId : 0) : previousDrawFramebufferId;
            int restoredReadFramebufferId = previousReadFramebufferId == previousOwnedFramebufferId
                    ? (framebufferCreated ? framebufferId : 0) : previousReadFramebufferId;
            GL30.glBindFramebuffer((int)36009, (int)restoredDrawFramebufferId);
            GL30.glBindFramebuffer((int)36008, (int)restoredReadFramebufferId);
            if (previousOwnedFramebufferId > 0) {
                GL30.glDeleteFramebuffers((int)previousOwnedFramebufferId);
            }
            if (!framebufferCreated) {
                if (framebufferId > 0) {
                    GL30.glDeleteFramebuffers((int)framebufferId);
                }
                this.clearOwnedFramebufferReference();
            }
        }
    }

    private void deleteOwnedFramebuffer() {
        if (this.ownedFramebufferId > 0) {
            GL30.glDeleteFramebuffers((int)this.ownedFramebufferId);
        }
        this.clearOwnedFramebufferReference();
    }

    private void clearOwnedFramebufferReference() {
        this.ownedFramebufferId = -1;
        this.ownedColorTextureId = -1;
        this.ownedDepthTextureId = -1;
        this.ownedColorTexture = null;
        this.ownedDepthTexture = null;
    }

    private void logFramebufferFailure(String reason, Throwable error) {
        if (!ForgeVersion.MC_26_2.d() || this.framebufferFailureLogged) {
            return;
        }
        this.framebufferFailureLogged = true;
        Vape.debugLog("RenderBatchManager 26.2: " + reason);
        if (error != null) {
            Vape.logThrowable(error);
        }
    }

    public RenderBatchManager() {
        try {
            InvWalkKeyLayout.initializeShaders();
            this.shaderProgram = InvWalkKeyLayout.universalShader;
        }
        catch (Throwable throwable) {
            throw RenderBatchManager.initializationFailure("shader load", throwable);
        }
        try {
            VertexAttributeType[] vertexAttributes = new VertexAttributeType[]{VertexAttributeType.Float, VertexAttributeType.Vec3, VertexAttributeType.Vec2, VertexAttributeType.Vec4, VertexAttributeType.Float, VertexAttributeType.Float, VertexAttributeType.Vec2, VertexAttributeType.Float, VertexAttributeType.Float, VertexAttributeType.Vec2, VertexAttributeType.Vec2, VertexAttributeType.Vec4, VertexAttributeType.Vec3, VertexAttributeType.Float, VertexAttributeType.Vec4};
            this.batchBuffer = new RenderBatchBuffer(this.shaderProgram, 5000, vertexAttributes);
        }
        catch (Throwable throwable) {
            throw RenderBatchManager.initializationFailure("mesh creation", throwable);
        }
        try {
            TextureAtlasRegistry textureAtlasRegistry = TextureAtlasRegistry.getInstance();
            textureAtlasRegistry.setActiveAtlas(textureAtlasRegistry.getOrCreate("vape_texture"));
        }
        catch (Throwable throwable) {
            throw RenderBatchManager.initializationFailure("texture atlas creation", throwable);
        }
    }

    private void saveAndPrepareGlState() {
        boolean cullFaceEnabled = GL11.glIsEnabled((int)2884);
        boolean blendEnabled = GL11.glIsEnabled((int)3042);
        boolean depthTestEnabled = GL11.glIsEnabled((int)2929);
        boolean depthWriteEnabled = GL11.glGetBoolean((int)2930);
        this.savedGlStates.push(new RenderBatchStateFlags(cullFaceEnabled, blendEnabled, depthTestEnabled, depthWriteEnabled));
        if (cullFaceEnabled) {
            OpenGlBackendHolder.backend.disableCapability(2884);
        }
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        if (depthTestEnabled) {
            GlStateManager.disableDepth();
        }
        GlStateManager.Y(770, 771);
    }

    public void beginBatchRendering() {
        this.captureGlBindings();
        int targetFramebufferId = this.getTargetFramebufferId();
        if (targetFramebufferId != -1) {
            GL30.glBindFramebuffer((int)36160, (int)targetFramebufferId);
        }
        this.saveAndPrepareGlState();
        this.batchBuffer.bindResources();
        this.batchResourcesBound = true;
    }

    private void restoreCapturedGlBindings() {
        GL30.glBindVertexArray((int)this.savedVertexArrayId);
        GL20.glUseProgram((int)this.savedProgramId);
        GL11.glBindTexture((int)3553, (int)this.savedTextureId);
        GL30.glBindFramebuffer((int)36009, (int)this.savedFramebufferId);
        GL30.glBindFramebuffer((int)36008, (int)this.savedReadFramebufferId);
        GL15.glBindBuffer((int)34962, (int)this.savedArrayBufferId);
        GL15.glBindBuffer((int)34963, (int)this.savedElementArrayBufferId);
    }

    private void ensureBatchResourcesBound() {
        if (!this.batchResourcesBound) {
            this.bindBatchResources();
        }
    }
}
