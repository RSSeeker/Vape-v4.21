package gg.vape.module.render.proj;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPreTick;
import gg.vape.event.impl.EventRender3D;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.runtime.NativeBridge;
import gg.vape.utils.render.BufferedRenderPrimitives;
import gg.vape.utils.render.GuiRenderPrimitives;
import gg.vape.utils.render.OpenGlBackendHolder;
import gg.vape.utils.render.RenderUtil;
import gg.vape.value.BooleanValue;
import gg.vape.wrapper.impl.AxisAlignedBB;
import gg.vape.wrapper.impl.Entity;
import gg.vape.wrapper.impl.EntityEnderPearl;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.RayTraceResult;
import gg.vape.wrapper.impl.RayTraceResult_type;
import gg.vape.wrapper.impl.RenderManager;
import gg.vape.wrapper.impl.Vec3;
import gg.vape.wrapper.impl.WorldClient;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

public class Projectiles
extends Mod {
    private final List<Float> vertices;
    private final BooleanValue showPearls;
    private final ArrowProjectile arrowProjectile = new ArrowProjectile();
    private IntBuffer vertexCountBuffer;
    private final Projectile eggProjectile;
    private final BooleanValue showArrows;
    private final List<Integer> firstVertices;
    private IntBuffer firstVertexBuffer;
    private final Projectile snowballProjectile;
    private final BooleanValue showPotions;
    private final PotionProjectile potionProjectile = new PotionProjectile();
    private FloatBuffer vertexBuffer;
    private final BooleanValue showSnowballs;
    private final Projectile pearlProjectile = new Projectile(Collections.singleton(MappedClasses.Zg), new Color(173, 12, 255));
    private final List<Integer> vertexCounts;
    private final BooleanValue showEggs;

    private IProjectile resolveProjectile(EntityEnderPearl entity) {
        if (entity.b$src$Z$fqlxe4()) {
            return null;
        }
        if (entity.z() == entity.M() && entity.h() == entity.m$src$D$fwnne5()) {
            return null;
        }
        for (IProjectile projectile : this.getEnabledProjectiles()) {
            if (!projectile.matches(entity)) continue;
            return projectile;
        }
        return null;
    }

    private void addVertex(double x, double y, double z, Color color) {
        double cameraX = RenderManager.getInterpolatedRenderPosX();
        double cameraY = RenderManager.getInterpolatedRenderPosY();
        double cameraZ = RenderManager.getInterpolatedRenderPosZ();
        this.vertices.add(Float.valueOf((float)(x - cameraX)));
        this.vertices.add(Float.valueOf((float)(y - cameraY)));
        this.vertices.add(Float.valueOf((float)(z - cameraZ)));
        this.vertices.add(Float.valueOf((float)color.getRed() / 255.0f));
        this.vertices.add(Float.valueOf((float)color.getGreen() / 255.0f));
        this.vertices.add(Float.valueOf((float)color.getBlue() / 255.0f));
        this.vertices.add(Float.valueOf((float)color.getAlpha() / 255.0f));
    }

    private void drawLegacyVertices() {
        if (this.vertexBuffer == null || this.vertexBuffer.capacity() < this.vertices.size()) {
            this.vertexBuffer = BufferUtils.createFloatBuffer((int)this.vertices.size());
        } else {
            this.vertexBuffer.clear();
        }
        for (Float value : this.vertices) {
            this.vertexBuffer.put(value.floatValue());
        }
        this.vertexBuffer.flip();
        GL11.glEnableClientState((int)32884);
        GL11.glEnableClientState((int)32886);
        int stride = 28;
        FloatBuffer positionBuffer = this.vertexBuffer.duplicate();
        positionBuffer.position(0);
        NativeBridge.vertexPointer(3, stride, positionBuffer);
        FloatBuffer colorBuffer = this.vertexBuffer.duplicate();
        colorBuffer.position(3);
        NativeBridge.colorPointer(4, stride, colorBuffer);
        int trajectoryCount = this.firstVertices.size();
        if (this.firstVertexBuffer == null || this.firstVertexBuffer.capacity() < trajectoryCount) {
            this.firstVertexBuffer = BufferUtils.createIntBuffer((int)trajectoryCount);
        } else {
            this.firstVertexBuffer.clear();
        }
        if (this.vertexCountBuffer == null || this.vertexCountBuffer.capacity() < trajectoryCount) {
            this.vertexCountBuffer = BufferUtils.createIntBuffer((int)trajectoryCount);
        } else {
            this.vertexCountBuffer.clear();
        }
        for (int i = 0; i < trajectoryCount; ++i) {
            this.firstVertexBuffer.put(this.firstVertices.get(i));
            this.vertexCountBuffer.put(this.vertexCounts.get(i));
        }
        this.firstVertexBuffer.flip();
        this.vertexCountBuffer.flip();
        GL14.glMultiDrawArrays((int)3, (IntBuffer)this.firstVertexBuffer, (IntBuffer)this.vertexCountBuffer);
        GL11.glDisableClientState((int)32884);
        GL11.glDisableClientState((int)32886);
    }

    @EventHandler
    public void onTick(EventPreTick event) {
        this.vertices.clear();
        this.firstVertices.clear();
        this.vertexCounts.clear();
        WorldClient world = Minecraft.theWorld();
        if (world.isNull()) {
            return;
        }
        for (Object entityHandle : world.S()) {
            Entity entity = new Entity(entityHandle);
            if (!entity.isInstance(MappedClasses.lv)) continue;
            EnderPearlProjectileBridge projectileEntity = new EnderPearlProjectileBridge(entity.getObject());
            EntityEnderPearl simulatedEntity = new EntityEnderPearl(entity.getObject());
            IProjectile projectile = this.resolveProjectile(projectileEntity);
            if (projectile == null) continue;
            this.simulateTrajectory(simulatedEntity, projectile);
        }
    }

    @EventHandler
    public void onRender3D(EventRender3D event) {
        if (this.vertices.isEmpty()) {
            return;
        }
        OpenGlBackendHolder.backend.setLineWidth(1.5f);
        OpenGlBackendHolder.backend.enableCapability(2848);
        boolean blendEnabled = OpenGlBackendHolder.backend.isCapabilityEnabled(3042);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.enableCapability(3042);
        }
        OpenGlBackendHolder.backend.disableCapability(3553);
        OpenGlBackendHolder.backend.disableCapability(2929);
        RenderUtil.d();
        if (GuiRenderPrimitives.d()) {
            this.drawBufferedSegments();
        } else {
            this.drawLegacyVertices();
        }
        RenderUtil.Y();
        OpenGlBackendHolder.backend.disableCapability(2848);
        if (!blendEnabled) {
            OpenGlBackendHolder.backend.disableCapability(3042);
        }
        OpenGlBackendHolder.backend.enableCapability(3553);
        OpenGlBackendHolder.backend.enableCapability(2929);
    }

    public Projectiles() {
        super("Projectiles", -16535661, Category.RENDER, "Shows projectile trajectories while in air");
        this.eggProjectile = new Projectile(Collections.singleton(MappedClasses.l2), new Color(255, 238, 154));
        this.snowballProjectile = new Projectile(Collections.singleton(MappedClasses.YZ), new Color(255, 255, 255));
        this.showArrows = BooleanValue.create(this, "Show Arrows", true);
        this.showPearls = BooleanValue.create(this, "Show Pearls", true);
        this.showPotions = BooleanValue.create(this, "Show Potions", false);
        this.showEggs = BooleanValue.create(this, "Show Eggs", false);
        this.showSnowballs = BooleanValue.create(this, "Show Snowballs", false);
        this.vertices = new ArrayList<Float>();
        this.firstVertices = new ArrayList<Integer>();
        this.vertexCounts = new ArrayList<Integer>();
        this.addValue(this.showArrows, this.showPearls, this.showPotions, this.showEggs, this.showSnowballs);
    }


    private void simulateTrajectory(EntityEnderPearl entity, IProjectile projectile) {
        if (!entity.isInstance(MappedClasses.lv)) {
            return;
        }
        int firstVertex = this.vertices.size() / 7;
        Color color = projectile.getColor();
        if (color == null) {
            color = new Color(255, 255, 255);
        }
        this.addVertex(entity.z(), entity.N(), entity.h(), color);
        double x = entity.z();
        double y = entity.N();
        double z = entity.h();
        double motionX = entity.t();
        double motionY = entity.q();
        double motionZ = entity.T();
        WorldClient world = Minecraft.theWorld();
        EntityPlayerSP player = Minecraft.thePlayer();
        while (true) {
            float collisionRadius = projectile.getCollisionRadius();
            float collisionHeight = projectile.getCollisionHeight();
            AxisAlignedBB projectileBounds = AxisAlignedBB.create(x - (double)collisionRadius, y, z - (double)collisionRadius, x + (double)collisionRadius, y + (double)collisionHeight, z + (double)collisionRadius);
            Vec3 start = Vec3.create(x, y, z);
            Vec3 end = Vec3.create(x + motionX, y + motionY, z + motionZ);
            RayTraceResult hitResult = world.K(start, end, false, entity.isInstance(MappedClasses.F), false, entity);
            if (ForgeVersion.MC_1_16_5.d()) {
                if (!hitResult.getTypeOfHit().equals(RayTraceResult_type.miss())) {
                    end = Vec3.create(hitResult.getHitVec().getX(), hitResult.getHitVec().getY(), hitResult.getHitVec().getZ());
                }
            } else if (hitResult.isNotNull()) {
                end = Vec3.create(hitResult.getHitVec().getX(), hitResult.getHitVec().getY(), hitResult.getHitVec().getZ());
            }
            List<Object> candidates = world.F(player, projectileBounds.addCoord(motionX, motionY, motionZ).expand(1.0, 1.0, 1.0));
            double closestDistance = 0.0;
            for (Object entityHandle : candidates) {
                Entity candidate = new Entity(entityHandle);
                if (!candidate.isInstance(MappedClasses.zm) || candidate.isInstance(MappedClasses.uz) || !candidate.n$src$Z$fx7gig() || candidate.equals(player)) continue;
                AxisAlignedBB candidateBounds = candidate.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(0.3, 0.3, 0.3);
                RayTraceResult entityHit = candidateBounds.calculateIntercept(start, end);
                if (!entityHit.isNotNull()) continue;
                double hitDistance = start.distanceTo(entityHit.getHitVec());
                if (closestDistance != 0.0 && !(hitDistance < closestDistance)) continue;
                closestDistance = hitDistance;
                entityHit.setEntity(candidate);
                hitResult = entityHit;
            }
            x += motionX;
            z += motionZ;
            if ((!ForgeVersion.MC_1_16_5.d() ? hitResult.isNotNull() : !hitResult.getTypeOfHit().equals(RayTraceResult_type.miss())) || (y += motionY) < -128.0) break;
            double drag = entity.h$src$Z$ftwoya() ? 0.8 : 0.99;
            motionX *= drag;
            motionY *= drag;
            motionZ *= drag;
            motionY -= 0.05;
            this.addVertex(x + motionX, y + motionY, z + motionZ, color);
        }
        int vertexCount = this.vertices.size() / 7 - firstVertex;
        this.firstVertices.add(firstVertex);
        this.vertexCounts.add(vertexCount);
    }

    private void drawBufferedSegments() {
        if (this.vertices.size() < 14) {
            return;
        }
        int vertexStride = 7;
        for (int trajectoryIndex = 0; trajectoryIndex < this.firstVertices.size(); ++trajectoryIndex) {
            int firstOffset = this.firstVertices.get(trajectoryIndex) * vertexStride;
            int endOffset = this.vertexCounts.get(trajectoryIndex) * vertexStride + firstOffset;
            for (int offset = firstOffset; offset < endOffset && offset + vertexStride + 1 <= endOffset; offset += vertexStride) {
                Color color = new Color(this.vertices.get(offset + 3).floatValue(), this.vertices.get(offset + 4).floatValue(), this.vertices.get(offset + 5).floatValue(), this.vertices.get(offset + 6).floatValue());
                float startX = this.vertices.get(offset).floatValue();
                float startY = this.vertices.get(offset + 1).floatValue();
                float startZ = this.vertices.get(offset + 2).floatValue();
                float endX = this.vertices.get(offset + 7).floatValue();
                float endY = this.vertices.get(offset + 8).floatValue();
                float endZ = this.vertices.get(offset + 9).floatValue();
                BufferedRenderPrimitives.drawLine3D(startX, startY, startZ, endX, endY, endZ, 1.5f, color);
            }
        }
    }

    private List<IProjectile> getEnabledProjectiles() {
        ArrayList<IProjectile> projectiles = new ArrayList<IProjectile>();
        if (this.showArrows.getEffectiveValue().booleanValue()) {
            projectiles.add(this.arrowProjectile);
        }
        if (this.showPotions.getEffectiveValue().booleanValue()) {
            projectiles.add(this.potionProjectile);
        }
        if (this.showPearls.getEffectiveValue().booleanValue()) {
            projectiles.add(this.pearlProjectile);
        }
        if (this.showEggs.getEffectiveValue().booleanValue()) {
            projectiles.add(this.eggProjectile);
        }
        if (this.showSnowballs.getEffectiveValue().booleanValue()) {
            projectiles.add(this.snowballProjectile);
        }
        return projectiles;
    }
}
