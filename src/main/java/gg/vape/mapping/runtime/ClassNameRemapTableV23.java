package gg.vape.mapping.runtime;

import gg.vape.mapping.runtime.ClassNameRemapTable;

public class ClassNameRemapTableV23
extends ClassNameRemapTable {
    private static String controlFlowMarker;

    static {
        ClassNameRemapTableV23.setControlFlowMarker("LdxEsc");
    }

    public static String getControlFlowMarker() {
        return controlFlowMarker;
    }

    public ClassNameRemapTableV23() {
        this.Q("net/minecraft/util/Vec3i", "net/minecraft/util/math/Vec3i");
        this.Q("net/minecraft/client/particle/EntityParticleEmitter", "net/minecraft/client/particle/ParticleEmitter");
        this.Q("net/minecraft/util/Vec3", "net/minecraft/util/math/Vec3d");
        this.Q("net/minecraft/util/AxisAlignedBB", "net/minecraft/util/math/AxisAlignedBB");
        this.Q("net/minecraft/util/MovingObjectPosition", "net/minecraft/util/math/RayTraceResult");
        this.Q("net/minecraft/util/MovingObjectPosition$MovingObjectType", "net/minecraft/util/math/RayTraceResult$Type");
        this.Q("net/minecraft/network/play/client/C00PacketKeepAlive", "net/minecraft/network/play/client/CPacketKeepAlive");
        this.Q("net/minecraft/network/play/client/C0FPacketConfirmTransaction", "net/minecraft/network/play/client/CPacketConfirmTransaction");
        this.Q("net/minecraft/network/play/client/C0APacketAnimation", "net/minecraft/network/play/client/CPacketAnimation");
        this.Q("net/minecraft/network/play/client/C0BPacketEntityAction", "net/minecraft/network/play/client/CPacketEntityAction");
        this.Q("net/minecraft/network/play/client/C01PacketChatMessage", "net/minecraft/network/play/client/CPacketChatMessage");
        this.Q("net/minecraft/network/play/client/C02PacketUseEntity", "net/minecraft/network/play/client/CPacketUseEntity");
        this.Q("net/minecraft/network/play/client/C03PacketPlayer", "net/minecraft/network/play/client/CPacketPlayer");
        this.Q("net/minecraft/network/play/client/C07PacketPlayerDigging", "net/minecraft/network/play/client/CPacketPlayerDigging");
        this.Q("net/minecraft/network/play/client/C09PacketHeldItemChange", "net/minecraft/network/play/client/CPacketHeldItemChange");
        this.Q("net/minecraft/network/play/client/C13PacketPlayerAbilities", "net/minecraft/network/play/client/CPacketPlayerAbilities");
        this.Q("net/minecraft/network/play/client/C0DPacketCloseWindow", "net/minecraft/network/play/client/CPacketCloseWindow");
        this.Q("net/minecraft/client/renderer/entity/RenderItem", "net/minecraft/client/renderer/RenderItem");
        this.Q("net/minecraft/network/play/client/C02PacketUseEntity$Action", "net/minecraft/network/play/client/CPacketUseEntity$Action");
        this.Q("net/minecraft/entity/ai/attributes/BaseAttributeMap", "net/minecraft/entity/ai/attributes/AbstractAttributeMap");
        this.Q("net/minecraft/util/IChatComponent", "net/minecraft/util/text/ITextComponent");
        this.Q("net/minecraft/util/ChatComponentText", "net/minecraft/util/text/TextComponentString");
        this.Q("net/minecraft/util/ChatComponentStyle", "net/minecraft/util/text/Style");
        this.Q("net/minecraft/util/ChatComponentTranslation", "net/minecraft/util/text/TextComponentTranslation");
        this.Q("net/minecraft/client/renderer/WorldRenderer", "net/minecraft/client/renderer/BufferBuilder");
        this.Q("net/minecraft/network/play/server/S2APacketParticles", "net/minecraft/network/play/server/SPacketParticles");
        this.Q("net/minecraft/network/play/server/S27PacketExplosion", "net/minecraft/network/play/server/SPacketExplosion");
        this.Q("net/minecraft/network/play/server/S12PacketEntityVelocity", "net/minecraft/network/play/server/SPacketEntityVelocity");
        this.Q("net/minecraft/network/play/server/S14PacketEntity", "net/minecraft/network/play/server/SPacketEntity");
        this.Q("net/minecraft/network/play/server/S14PacketEntity$S16PacketEntityLook", "net/minecraft/network/play/server/SPacketEntity$S16PacketEntityLook");
        this.Q("net/minecraft/network/play/server/S18PacketEntityTeleport", "net/minecraft/network/play/server/SPacketEntityTeleport");
        this.Q("net/minecraft/network/play/server/S23PacketBlockChange", "net/minecraft/network/play/server/SPacketBlockChange");
        this.Q("net/minecraft/network/play/server/S08PacketPlayerPosLook", "net/minecraft/network/play/server/SPacketPlayerPosLook");
        this.Q("net/minecraft/network/play/client/C08PacketPlayerBlockPlacement", "net/minecraft/network/play/client/CPacketPlayerTryUseItemOnBlock");
        this.Q("net/minecraft/network/play/server/S19PacketEntityStatus", "net/minecraft/network/play/server/SPacketEntityStatus");
        this.Q("net/minecraft/network/play/server/S0BPacketAnimation", "net/minecraft/network/play/server/SPacketAnimation");
        this.Q("net/minecraft/network/play/server/S00PacketKeepAlive", "net/minecraft/network/play/server/SPacketKeepAlive");
        this.Q("net/minecraft/network/play/server/S32PacketConfirmTransaction", "net/minecraft/network/play/server/SPacketConfirmTransaction");
        this.Q("net/minecraft/network/play/server/S0CPacketSpawnPlayer", "net/minecraft/network/play/server/SPacketSpawnPlayer");
        this.Q("net/minecraft/network/play/server/S13PacketDestroyEntities", "net/minecraft/network/play/server/SPacketDestroyEntities");
        this.Q("net/minecraft/network/play/server/S01PacketJoinGame", "net/minecraft/network/play/server/SPacketJoinGame");
        this.Q("net/minecraft/network/play/server/S06PacketUpdateHealth", "net/minecraft/network/play/server/SPacketUpdateHealth");
        this.Q("net/minecraft/network/play/server/S0EPacketSpawnObject", "net/minecraft/network/play/server/SPacketSpawnObject");
        this.Q("net/minecraft/network/play/server/S3BPacketScoreboardObjective", "net/minecraft/network/play/server/SPacketScoreboardObjective");
        this.Q("net/minecraft/network/play/server/S3DPacketDisplayScoreboard", "net/minecraft/network/play/server/SPacketDisplayObjective");
        this.Q("net/minecraft/network/play/server/S3CPacketUpdateScore", "net/minecraft/network/play/server/SPacketUpdateScore");
        this.Q("net/minecraft/network/play/server/S3EPacketTeams", "net/minecraft/network/play/server/SPacketTeams");
        this.Q("net/minecraft/network/play/server/S02PacketChat", "net/minecraft/network/play/server/SPacketChat");
        this.Q("net/minecraft/network/play/server/S38PacketPlayerListItem", "net/minecraft/network/play/server/SPacketPlayerListItem");
        this.Q("net/minecraft/network/play/server/S03PacketTimeUpdate", "net/minecraft/network/play/server/SPacketTimeUpdate");
        this.Q("net/minecraft/network/play/client/C0EPacketClickWindow", "net/minecraft/network/play/client/CPacketClickWindow");
        this.Q("net/minecraft/network/play/client/C14PacketTabComplete", "net/minecraft/network/play/client/CPacketTabComplete");
        this.Q("net/minecraft/client/renderer/entity/RendererLivingEntity", "net/minecraft/client/renderer/entity/RenderLivingBase");
        this.Q("net/minecraft/util/RegistryNamespaced", "net/minecraft/util/registry/RegistryNamespaced");
        this.Q("net/minecraft/util/RegistrySimple", "net/minecraft/util/registry/RegistrySimple");
        this.Q("net/minecraft/network/play/client/C03PacketPlayer$C05PacketPlayerLook", "net/minecraft/network/play/client/CPacketPlayer$Rotation");
        this.Q("net/minecraft/network/play/client/C03PacketPlayer$C04PacketPlayerPosition", "net/minecraft/network/play/client/CPacketPlayer$Position");
        this.Q("net/minecraft/network/play/client/C03PacketPlayer$C06PacketPlayerPosLook", "net/minecraft/network/play/client/CPacketPlayer$PositionRotation");
        this.Q("net/minecraft/world/biome/BiomeGenBase", "net/minecraft/world/biome/Biome");
        this.Q("net/minecraft/world/biome/WorldChunkManager", "net/minecraft/world/biome/BiomeProvider");
        this.Q("net/minecraft/util/BlockPos", "net/minecraft/util/math/BlockPos");
        this.Q("net/minecraft/block/state/IBlockState", "net/minecraft/block/state/IBlockState");
        this.Q("net/minecraft/network/play/client/C0BPacketEntityAction$Action", "net/minecraft/network/play/client/CPacketEntityAction$Action");
        this.Q("net/minecraft/network/play/server/S08PacketPlayerPosLook$EnumFlags", "net/minecraft/network/play/server/SPacketPlayerPosLook$EnumFlags");
        this.Q("net/minecraft/client/resources/model/IBakedModel", "net/minecraft/client/renderer/block/model/IBakedModel");
        this.Q("net/minecraft/util/EnumWorldBlockLayer", "net/minecraft/util/BlockRenderLayer");
        this.Q("net/minecraft/network/play/server/S19PacketEntityHeadLook", "net/minecraft/network/play/server/SPacketEntityHeadLook");
    }

    public static void setControlFlowMarker(String marker) {
        controlFlowMarker = marker;
    }
}
