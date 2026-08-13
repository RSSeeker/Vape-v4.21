package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.runtime.MemberNameRemapTable;
import java.util.List;
import java.util.UUID;

public class MemberNameRemapTableV51
extends MemberNameRemapTable {
    protected void AG() {
        this.B(MappedClasses.DD, "blockPosition", "pos");
    }

    protected void Ao() {
        this.B(MappedClasses.G, "matrix", "pose");
    }

    protected void Ad() {
        this.t(MappedClasses.zH, "updateLightmap", "updateLightTexture");
        this.t(MappedClasses.zH, "disableLightmap", "turnOffLightLayer");
        this.t(MappedClasses.zH, "enableLightmap", "turnOnLightLayer");
    }

    protected void A5() {
        this.t(MappedClasses.Dt, "bindTexture", "_bind");
    }

    protected void h() {
        this.B(MappedClasses.ze, "field_70232_b", "xPower");
        this.B(MappedClasses.ze, "field_70233_c", "yPower");
        this.B(MappedClasses.ze, "field_70230_d", "zPower");
    }

    protected void UJ() {
        this.t(MappedClasses.lp, "finish", "endBatch");
    }

    protected void e() {
        this.B(MappedClasses.YX, "entityID", "id");
        this.B(MappedClasses.YX, "motionX", "xa");
        this.B(MappedClasses.YX, "motionY", "ya");
        this.B(MappedClasses.YX, "motionZ", "za");
        this.B(MappedClasses.YX, "field_149417_a", "id");
        this.B(MappedClasses.YX, "field_149415_b", "xa");
        this.B(MappedClasses.YX, "field_149416_c", "ya");
        this.B(MappedClasses.YX, "field_149414_d", "za");
    }

    protected void AN() {
        this.G(MappedClasses.ZM, "LEATHER", "LEATHER", MappedClasses.Vo);
        this.G(MappedClasses.ZM, "CHAIN", "CHAIN", MappedClasses.Vo);
        this.G(MappedClasses.ZM, "IRON", "IRON", MappedClasses.Vo);
        this.G(MappedClasses.ZM, "GOLD", "GOLD", MappedClasses.Vo);
        this.G(MappedClasses.ZM, "DIAMOND", "DIAMOND", MappedClasses.Vo);
    }

    protected void AY() {
        this.t(MappedClasses.lh, "getAttackDamage", "getAttackDamageBonus");
    }

    protected void AO() {
        this.t(MappedClasses.VIS_GRAPH, "computeVisibility", "resolve");
        this.t(MappedClasses.VIS_GRAPH, "setOpaqueCube", "setOpaque");
    }

    protected void AE() {
        this.B(MappedClasses.qU, "damageReduceAmount", "defense");
    }

    protected void AQ() {
        this.B(MappedClasses.DT, "hitResult", "location");
    }

    protected void U9() {
        this.B(MappedClasses.q0, "index", "data3d");
        this.B(MappedClasses.q0, "opposite", "oppositeIndex");
        this.t(MappedClasses.q0, "getDirectionVec", "getNormal");
        this.t(MappedClasses.q0, "getName2", "getName");
        this.t(MappedClasses.q0, "func_210769_a", "getNearest");
        this.t(MappedClasses.q0, "getHorizontalIndex", "get2DDataValue");
    }

    protected void UU() {
        this.B(MappedClasses.Ye, "efficiency", "speed");
    }

    protected void Au() {
        this.B(MappedClasses.lt, "pitch", "xRot");
        this.B(MappedClasses.lt, "yaw", "yRot");
        this.B(MappedClasses.lt, "pos", "position");
        this.B(MappedClasses.lt, "renderViewEntity", "entity");
        this.B(MappedClasses.lt, "world", "level");
        this.t(MappedClasses.lt, "update", "setup");
    }

    protected void Ak() {
        this.B(MappedClasses.zX, "SEARCH", "TAB_SEARCH");
    }

    protected void AX() {
        this.B(MappedClasses.YQ, "slotNumber", "index");
        this.t(MappedClasses.YQ, "getStack", "getItem");
    }

    protected void A4() {
        this.B(MappedClasses.zc, "entityId", "id");
        this.B(MappedClasses.zc, "rotationYaw", "yRot");
        this.B(MappedClasses.zc, "rotationPitch", "xRot");
        this.B(MappedClasses.zc, "ticksExisted", "tickCount");
        this.B(MappedClasses.zc, "lastTickPosX", "xOld");
        this.B(MappedClasses.zc, "lastTickPosY", "yOld");
        this.B(MappedClasses.zc, "lastTickPosZ", "zOld");
        this.B(MappedClasses.zc, "prevRotationYaw", "yRotO");
        this.B(MappedClasses.zc, "prevRotationPitch", "xRotO");
        this.B(MappedClasses.zc, "prevPosX", "xo");
        this.B(MappedClasses.zc, "prevPosY", "yo");
        this.B(MappedClasses.zc, "prevPosZ", "zo");
        this.B(MappedClasses.zc, "boundingBox", "bb");
        this.B(MappedClasses.zc, "ridingEntity", "vehicle");
        this.G(MappedClasses.zc, "rand", "random", MappedClasses.VO);
        this.B(MappedClasses.zc, "noClip", "noPhysics");
        this.B(MappedClasses.zc, "hurtResistantTime", "invulnerableTime");
        this.B(MappedClasses.zc, "entityUniqueID", "uuid");
        this.B(MappedClasses.zc, "positionVec", "position");
        this.B(MappedClasses.zc, "motion", "deltaMovement");
        this.B(MappedClasses.zc, "size", "dimensions");
        this.B(MappedClasses.zc, "collidedHorizontally", "horizontalCollision");
        this.B(MappedClasses.zc, "collidedVertically", "verticalCollision");
        this.B(MappedClasses.zc, "world", "level");
        this.G(MappedClasses.zc, "field_242272_av", "packetPositionCodec", MappedClasses.Fg);
        this.B(MappedClasses.zc, "stepHeight", "maxUpStep");
        this.B(MappedClasses.zc, "prevDistanceWalkedModified", "walkDistO");
        this.B(MappedClasses.zc, "distanceWalkedModified", "walkDist");
        this.B(MappedClasses.zc, "distanceWalkedOnStepModified", "moveDist");
        this.B(MappedClasses.zc, "preventEntitySpawning", "blocksBuilding");
        this.t(MappedClasses.zc, "getDistance", "distanceTo");
        this.t(MappedClasses.zc, "rotateTowards", "turn");
        this.t(MappedClasses.zc, "getPosY", "getY");
        this.t(MappedClasses.zc, "copyDataFromOld", "restoreFrom");
        this.t(MappedClasses.zc, "isBurning", "isOnFire");
        this.t(MappedClasses.zc, "isSneaking", "isCrouching");
        this.t(MappedClasses.zc, "getCollisionBorderSize", "getPickRadius");
        this.t(MappedClasses.zc, "isOffsetPositionInLiquid", "isFree");
        this.t(MappedClasses.zc, "setVelocity", "lerpMotion");
        this.t(MappedClasses.zc, "isWet", "isInWaterOrRain");
        this.t(MappedClasses.zc, "setFire", "igniteForSeconds");
        this.t(MappedClasses.zc, "setFlag", "setSharedFlag");
        this.t(MappedClasses.zc, "setPositionAndRotation", "absMoveTo");
        this.t(MappedClasses.zc, "setSneaking", "setShiftKeyDown");
        this.t(MappedClasses.zc, "getLook", "getViewVector");
        this.t(MappedClasses.zc, "doBlockCollisions", "checkInsideBlocks");
        this.t(MappedClasses.zc, "func_145775_I", "checkInsideBlocks");
        this.t(MappedClasses.zc, "remove", "unsetRemoved");
        this.t(MappedClasses.zc, "getLowestRidingEntity", "getRootVehicle");
        this.t(MappedClasses.zc, "func_184231_a", "checkFallDamage");
        this.t(MappedClasses.zc, "canBeCollidedWith", "isPickable");
        this.t(MappedClasses.zc, "m_6021_", "teleportTo");
    }

    protected void AS() {
        this.f(MappedClasses.YL, "getCollisionShapes", "getBlockCollisions", Iterable.class, new Class[0]);
    }

    protected void AH() {
        this.B(MappedClasses.YU, "loadedTileEntityList", "blockEntityTickers");
        this.B(MappedClasses.YU, "isRemote", "isClientSide");
        this.B(MappedClasses.YU, "rainingStrength", "rainLevel");
        this.B(MappedClasses.YU, "prevRainingStrength", "oRainLevel");
        this.B(MappedClasses.YU, "thunderingStrength", "thunderLevel");
        this.B(MappedClasses.YU, "prevThunderingStrength", "oThunderLevel");
        this.B(MappedClasses.YU, "worldInfo", "levelData");
        this.t(MappedClasses.YU, "getEntityByID", "getEntity");
        this.t(MappedClasses.YU, "isValid", "isInSpawnableBounds");
        this.t(MappedClasses.YU, "getEntitiesInAABBexcluding", "getEntities");
    }

    protected void U3() {
        this.B(MappedClasses.Yl, "openContainer", "containerMenu");
        this.B(MappedClasses.Yl, "field_71094_bP", "xCloak");
        this.B(MappedClasses.Yl, "field_71095_bQ", "yCloak");
        this.B(MappedClasses.Yl, "field_71085_bR", "zCloak");
        this.B(MappedClasses.Yl, "field_71091_bM", "xCloakO");
        this.B(MappedClasses.Yl, "field_71096_bN", "yCloakO");
        this.B(MappedClasses.Yl, "field_71097_bO", "zCloakO");
        this.B(MappedClasses.Yl, "container", "inventoryMenu");
        this.B(MappedClasses.Yl, "fishingBobber", "fishing");
        this.t(MappedClasses.Yl, "resetCooldown", "resetAttackStrengthTicker");
        this.t(MappedClasses.Yl, "getCooledAttackStrength", "getAttackStrengthScale");
        this.t(MappedClasses.Yl, "closeScreen", "closeContainer");
        this.t(MappedClasses.Yl, "attackTargetEntityWithCurrentItem", "attack");
        this.t(MappedClasses.Yl, "getFoodStats", "getFoodData");
        this.t(MappedClasses.Yl, "onCriticalHit", "crit");
        this.t(MappedClasses.Yl, "onEnchantmentCritical", "magicCrit");
    }

    protected void o() {
        this.B(MappedClasses.F5, "attackDamage", "damage");
    }

    protected void Ul() {
        this.B(MappedClasses.z8, "zLevel", "blitOffset");
        this.t(MappedClasses.z8, "renderItemIntoGUI", "renderAndDecorateFakeItem");
        this.t(MappedClasses.z8, "renderItemOverlays", "renderGuiItemDecorations");
    }

    protected void US() {
        this.B(MappedClasses.Yu, "currentItem", "selected");
        this.B(MappedClasses.Yu, "field_70462_a", "items");
        this.B(MappedClasses.Yu, "armorInventory", "armor");
    }

    protected void Aj() {
        this.B(MappedClasses.FW, "SHADERS_TEXTURES", "EFFECTS");
        this.B(MappedClasses.FW, "shaderGroup", "postEffect");
        this.B(MappedClasses.FW, "lightmapTexture", "lightTexture");
        this.B(MappedClasses.FW, "activeRender", "mainCamera");
        this.B(MappedClasses.FW, "rendererUpdateCount", "confusionAnimationTick");
        this.B(MappedClasses.FW, "fovModifierHand", "fov");
        this.B(MappedClasses.FW, "fovModifierHandPrev", "oldFov");
        this.B(MappedClasses.FW, "farPlaneDistance", "renderDistance");
        this.B(MappedClasses.FW, "useShader", "effectActive");
        this.t(MappedClasses.FW, "renderWorld", "renderLevel");
        this.t(MappedClasses.FW, "hurtCameraEffect", "bobHurt");
        this.t(MappedClasses.FW, "applyBobbing", "bobView");
        this.t(MappedClasses.FW, "updateShaderGroupSize", "resize");
        this.t(MappedClasses.FW, "getMouseOver", "pick");
        this.f(MappedClasses.FW, "updateCameraAndRender", "render", Void.TYPE, MappedClasses.uy, Boolean.TYPE);
        this.f(MappedClasses.FW, "renderItemActivationAnimation", "renderItemActivationAnimation", Void.TYPE, MappedClasses.m, Float.TYPE);
    }

    protected void As() {
        this.t(MappedClasses.qP, "func_72436_e", "distanceTo");
    }

    protected void AF() {
        this.t(MappedClasses.z_, "getID", "id");
        this.t(MappedClasses.z_, "getAmount", "amount");
    }

    protected void j() {
        this.t(MappedClasses.T, "getShaderManager", "getEffect");
    }

    protected void A0() {
        this.t(MappedClasses.DQ, "getLast", "last");
        this.t(MappedClasses.DQ, "push", "pushPose");
        this.t(MappedClasses.DQ, "pop", "popPose");
        this.t(MappedClasses.DQ, "rotate", "mulPose");
        this.t(MappedClasses.DQ, "mulPoseMatrix", "mulPose");
    }

    protected void Af() {
        this.B(MappedClasses.lb, "BLOCK_TO_ITEM", "BY_BLOCK");
        this.t(MappedClasses.lb, "getIdFromItem", "getId");
        this.t(MappedClasses.lb, "getItemById", "byId");
        this.t(MappedClasses.lb, "fillItemGroup", "fillItemCategory");
        this.t(MappedClasses.lb, "getName", "getDescription");
        this.t(MappedClasses.lb, "getDisplayName", "getName");
    }

    protected void Ur() {
        this.B(MappedClasses.Zj, "displayedTitle", "title");
        this.t(MappedClasses.Zj, "func_238447_a_", "displayScoreboardSidebar");
    }

    protected void F() {
        this.f(MappedClasses.Z, "addEntityImpl", "addEntity", Void.TYPE, MappedClasses.zc);
        this.t(MappedClasses.Z, "func_184148_a", "playSound");
        this.t(MappedClasses.Z, "getAllEntities", "entitiesForRendering");
        this.t(MappedClasses.Z, "func_239134_a_", "setGameTime");
        this.t(MappedClasses.Z, "playSound", "playLocalSound");
    }

    protected void Uf() {
        this.B(MappedClasses.qe, "field_149152_f", "knockbackX");
        this.B(MappedClasses.qe, "field_149153_g", "knockbackY");
        this.B(MappedClasses.qe, "field_149159_h", "knockbackZ");
    }

    protected void U7() {
        this.t(MappedClasses.zJ, "func_217299_a", "clip");
        this.t(MappedClasses.zJ, "getBlockState", "getBlockState");
    }

    protected void U4() {
        this.t(MappedClasses.LEVEL_ENTITY_GETTER, "m_142232_", "get");
    }

    protected void i() {
        this.t(MappedClasses.Y, "func_237498_g_", "createFormattedDisplayName");
    }

    protected void R() {
        this.t(MappedClasses.D3, "get", "byId");
        this.f(MappedClasses.D3, "removeAttributesModifiersFromEntity", "removeAttributeModifiers", Void.TYPE, MappedClasses.Ya);
        this.t(MappedClasses.D3, "getDisplayName", "getDisplayName");
        this.t(MappedClasses.D3, "isBeneficial", "isBeneficial");
    }

    protected void s() {
        this.B(MappedClasses.Dc, "info", "camera");
        this.t(MappedClasses.Dc, "renderEntityStatic", "render");
    }

    protected void S() {
        this.B(MappedClasses.q9, "isCreativeMode", "instabuild");
        this.B(MappedClasses.q9, "disableDamage", "invulnerable");
        this.B(MappedClasses.q9, "allowFlying", "mayfly");
        this.B(MappedClasses.q9, "allowEdit", "mayBuild");
        this.B(MappedClasses.q9, "isFlying", "flying");
        this.t(MappedClasses.q9, "getWalkSpeed", "getWalkingSpeed");
        this.t(MappedClasses.q9, "getFlySpeed", "getFlyingSpeed");
        this.t(MappedClasses.q9, "setFlySpeed", "setFlyingSpeed");
        this.t(MappedClasses.q9, "setWalkSpeed", "setWalkingSpeed");
    }

    protected void C$src$V$15qx32j() {
        this.B(MappedClasses.Yg, "tagMap", "tags");
    }

    protected void Aa() {
        if (this.isVanillaMinecraftAbsent()) {
            this.f(MappedClasses.Zn, "binding", "a", false);
        }
    }

    protected void p() {
        this.B(MappedClasses.ls, "f_157152_", "shaderTextures");
        this.G(MappedClasses.ls, "f_157141_", "modelViewStack", MappedClasses.VH);
        this.B(MappedClasses.ls, "f_157140_", "modelViewMatrix");
        this.t(MappedClasses.ls, "m_69482_", "enableDepthTest");
        this.t(MappedClasses.ls, "m_157429_", "setShaderColor");
        this.t(MappedClasses.ls, "m_69405_", "blendFunc");
        this.t(MappedClasses.ls, "m_69478_", "enableBlend");
        this.t(MappedClasses.ls, "m_157179_", "setShaderTexture");
    }

    protected void N() {
        this.t(MappedClasses.lg, "chunkExists", "hasChunk");
    }

    protected void J() {
        this.t(MappedClasses.j, "getParameters", "writeToString");
    }

    protected void c() {
        this.t(MappedClasses.qr, "func_226595_a_", "m_27644_");
    }

    protected void AD() {
        this.B(MappedClasses.ll, "depthBuffer", "depthBufferId");
        this.t(MappedClasses.ll, "bindFramebuffer", "bindWrite");
        this.t(MappedClasses.ll, "unbindFramebuffer", "unbindWrite");
        this.t(MappedClasses.ll, "bindFramebufferTexture", "bindRead");
        this.t(MappedClasses.ll, "unbindFramebufferTexture", "unbindRead");
        this.t(MappedClasses.ll, "deleteFramebuffer", "destroyBuffers");
    }

    protected void A1() {
        this.B(MappedClasses.K, "f_84076_", "activeTexture");
        this.B(MappedClasses.K, "f_84066_", "BLEND");
        this.B(MappedClasses.K, "f_84077_", "TEXTURES");
        this.t(MappedClasses.K, "m_84544_", "_bindTexture");
        this.t(MappedClasses.K, "m_84298_", "_depthMask");
        this.t(MappedClasses.K, "m_84525_", "_enableBlend");
        this.t(MappedClasses.K, "m_84519_", "_disableBlend");
        this.t(MappedClasses.K, "m_84091_", "_enableCull");
        this.t(MappedClasses.K, "m_84094_", "_disableCull");
        this.t(MappedClasses.K, "m_84300_", "_colorMask");
        this.t(MappedClasses.K, "m_84513_", "_enableDepthTest");
        this.t(MappedClasses.K, "m_84507_", "_disableDepthTest");
        this.t(MappedClasses.K, "m_84109_", "_enableTexture");
        this.t(MappedClasses.K, "m_84110_", "_disableTexture");
        this.t(MappedClasses.K, "m_84538_", "_activeTexture");
        this.t(MappedClasses.K, "m_84335_", "_blendFuncSeparate");
        this.t(MappedClasses.K, "m_84209_", "_texImage2D");
        this.t(MappedClasses.K, "m_84111_", "_genTexture");
        this.t(MappedClasses.K, "m_157116_", "glShaderSource");
    }

    protected void Aw() {
        this.B(MappedClasses.ITEM_FOOD, "value", "nutrition");
        this.B(MappedClasses.ITEM_FOOD, "saturation", "saturation");
    }

    protected void Ah() {
        this.t(MappedClasses.FJ, "applyModifier", "addModifier");
        this.f(MappedClasses.FJ, "getModifier", "getModifier", MappedClasses.z_, MappedClasses.zC);
        this.b(MappedClasses.FJ, "func_225505_c_", "getModifiers", true);
        this.b(MappedClasses.FJ, "func_142049_d", "removeModifiers", true);
    }

    protected void Ui() {
        this.B(MappedClasses.DR, "pressed", "isDown");
        this.B(MappedClasses.DR, "pressTime", "clickCount");
        this.B(MappedClasses.DR, "keyCode", "key");
        this.t(MappedClasses.DR, "isPressed", "consumeClick");
        this.t(MappedClasses.DR, "unPressAllKeys", "releaseAll");
        this.t(MappedClasses.DR, "setPressed", "setDown");
        this.t(MappedClasses.DR, "unpressKey", "release");
        this.t(MappedClasses.DR, "setKeyBindState", "set");
        this.t(MappedClasses.DR, "onTick", "click");
        this.t(MappedClasses.DR, "func_151470_d", "isDown");
    }

    protected void A9() {
        this.G(MappedClasses.FX, "properties", "values", MappedClasses.lS);
    }

    protected void UX() {
        this.B(MappedClasses.ld, "isHittingBlock", "isDestroying");
        this.B(MappedClasses.ld, "curBlockDamageMP", "destroyProgress");
        this.f(MappedClasses.ld, "processRightClick", "useItem", MappedClasses.zr, MappedClasses.Yl, MappedClasses.Yf);
        this.t(MappedClasses.ld, "getBlockReachDistance", "getPickRange");
        this.t(MappedClasses.ld, "extendedReach", "hasFarPickRange");
        this.t(MappedClasses.ld, "attackEntity", "attack");
        this.t(MappedClasses.ld, "syncCurrentPlayItem", "ensureHasSentCarriedItem");
        this.t(MappedClasses.ld, "onStoppedUsingItem", "releaseUsingItem");
        this.t(MappedClasses.ld, "extendedReach", "isCreative");
    }

    protected void A6() {
        this.B(MappedClasses.zw, "yaw", "yRot");
        this.B(MappedClasses.zw, "pitch", "xRot");
        this.B(MappedClasses.zw, "field_148936_d", "yRot");
        this.B(MappedClasses.zw, "field_148937_e", "xRot");
        this.t(MappedClasses.zw, "func_179834_f", "getRelativeArguments");
    }

    protected void d() {
    }

    protected void An() {
        this.B(MappedClasses.Ft, "INVENTORY_BACKGROUND", "INVENTORY_LOCATION");
        this.B(MappedClasses.Ft, "guiLeft", "leftPos");
        this.B(MappedClasses.Ft, "guiTop", "topPos");
        this.B(MappedClasses.Ft, "xSize", "imageWidth");
        this.B(MappedClasses.Ft, "ySize", "imageHeight");
        this.B(MappedClasses.Ft, "field_147002_h", "menu");
        this.B(MappedClasses.Ft, "container", "menu");
        this.t(MappedClasses.Ft, "handleMouseClick", "slotClicked");
        this.t(MappedClasses.Ft, "getSelectedSlot", "findSlot");
    }

    protected void V() {
        this.t(MappedClasses.m, "func_238470_a_", "m_93200_");
    }

    protected void Z() {
        this.B(MappedClasses.Yk, "blend", "mode");
        this.B(MappedClasses.Yk, "field_179213_a", "mode");
    }

    protected void x() {
        this.B(MappedClasses.uK, "category", "biomeCategory");
    }

    protected void k() {
        this.B(MappedClasses.Zk, "defaultState", "defaultBlockState");
        this.t(MappedClasses.Zk, "getStateById", "stateById");
        this.f(MappedClasses.Zk, "getItem", "getCloneItemStack", MappedClasses.VK, MappedClasses.F7, MappedClasses.lf, MappedClasses.Zl);
        this.t(MappedClasses.Zk, "getStateId", "getId");
        this.t(MappedClasses.Zk, "onBlockActivated", "useWithoutItem");
        this.B(MappedClasses.Zk, "translationKey", "descriptionId");
    }

    protected void AW() {
        this.B(MappedClasses.qS, "username", "name");
        this.G(MappedClasses.qS, "playerID", "uuid", UUID.class);
    }

    protected void Ay() {
        this.t(MappedClasses.u6, "func_237500_a_", "formatNameForTeam");
    }

    protected void AC() {
        this.B(MappedClasses.FR, "baseName", "name");
        this.t(MappedClasses.FR, "getPotionTypeForName", "byName");
    }

    protected void Aq() {
        this.B(MappedClasses.Y6, "name", "location");
    }

    protected void Us() {
        this.f(MappedClasses.Dn, "m_5483_", "m_340435_", MappedClasses.Dn, Float.TYPE, Float.TYPE, Float.TYPE);
        this.t(MappedClasses.Dn, "color", "setColor");
    }

    protected void A7() {
        this.t(MappedClasses.uV, "getEffectsFromStack", "getMobEffects");
    }

    protected void K() {
        this.B(MappedClasses.F1, "doneLoadingTerrain", "started");
        this.t(MappedClasses.F1, "getPlayerInfoMap", "getOnlinePlayers");
    }

    protected void AV() {
        this.t(MappedClasses.Zc, "getGameProfile", "getProfile");
        this.t(MappedClasses.Zc, "getResponseTime", "getLatency");
    }

    protected void AK() {
        this.B(MappedClasses.Fq, "field_177097_h", "layers");
        this.B(MappedClasses.Fq, "entityModel", "model");
    }

    protected void A3() {
        this.B(MappedClasses.Ys, "xVelocity", "accumulatedDX");
        this.B(MappedClasses.Ys, "yVelocity", "accumulatedDY");
        this.B(MappedClasses.Ys, "mouseX", "xpos");
        this.B(MappedClasses.Ys, "mouseY", "ypos");
        this.t(MappedClasses.Ys, "ungrabMouse", "releaseMouse");
    }

    protected void l() {
        this.B(MappedClasses.ZQ, "entityId", "id");
        this.B(MappedClasses.ZQ, "type", "action");
    }

    protected void A() {
        this.t(MappedClasses.VQ, "getEntityTexture", "getTextureLocation");
    }

    protected void U1() {
        this.B(MappedClasses.X, "windowId", "containerId");
    }

    protected void m() {
        this.B(MappedClasses.Zu, "chatComponent", "unsignedContent");
        this.t(MappedClasses.Zu, "getChatComponent", "unsignedContent");
    }

    protected void v$src$V$16iylby() {
        this.B(MappedClasses.zV, "bipedHead", "head");
        this.B(MappedClasses.zV, "bipedHeadwear", "hat");
        this.B(MappedClasses.zV, "bipedRightArm", "rightArm");
        this.B(MappedClasses.zV, "bipedLeftArm", "leftArm");
        this.B(MappedClasses.zV, "bipedRightLeg", "rightLeg");
        this.B(MappedClasses.zV, "bipedLeftLeg", "leftLeg");
    }

    protected void B() {
        this.B(MappedClasses.lF, "field_175252_a", "f_94518_");
    }

    protected void A8() {
        this.B(MappedClasses.DN, "position", "pos");
        this.B(MappedClasses.DN, "facing", "direction");
    }

    protected void AZ() {
        this.b(MappedClasses.lf, "func_177984_a", "above", true);
        this.b(MappedClasses.lf, "func_177978_c", "north", true);
        this.b(MappedClasses.lf, "func_177974_f", "east", true);
        this.b(MappedClasses.lf, "func_177968_d", "south", true);
        this.b(MappedClasses.lf, "func_177976_e", "west", true);
        this.b(MappedClasses.lf, "m_7495_", "below", true);
        this.b(MappedClasses.lf, "m_5484_", "relative", true);
        this.b(MappedClasses.lf, "up", "above", true);
        this.b(MappedClasses.lf, "down", "below", true);
    }

    protected void Ax() {
    }

    protected void U() {
        this.B(MappedClasses.Vk, "currentSectionName", "path");
    }

    protected void Q() {
        this.B(MappedClasses.lM, "caughtEntity", "hookedIn");
        this.B(MappedClasses.lM, "field_234597_c_", "biting");
    }

    protected void Um() {
        this.B(MappedClasses.YN, "field_194148_c", "deltaTicks");
        this.B(MappedClasses.YN, "renderPartialTicks", "deltaTickResidual");
    }

    protected void AI() {
        this.B(MappedClasses.qz, "posX", "xa");
        this.B(MappedClasses.qz, "posY", "ya");
        this.B(MappedClasses.qz, "posZ", "za");
        this.B(MappedClasses.qz, "field_149069_g", "hasRot");
        this.t(MappedClasses.qz, "func_149065_a", "getEntity");
        this.t(MappedClasses.qz, "func_149066_f", "getyRot");
        this.t(MappedClasses.qz, "func_149063_g", "getxRot");
        this.t(MappedClasses.qz, "func_149060_h", "hasRotation");
    }

    protected void Ac() {
        this.B(MappedClasses.lT, "keyBindAttack", "keyAttack");
        this.B(MappedClasses.lT, "keyBindUseItem", "keyUse");
        this.B(MappedClasses.lT, "keyBindForward", "keyUp");
        this.B(MappedClasses.lT, "keyBindLeft", "keyLeft");
        this.B(MappedClasses.lT, "keyBindBack", "keyDown");
        this.B(MappedClasses.lT, "keyBindRight", "keyRight");
        this.B(MappedClasses.lT, "keyBindJump", "keyJump");
        this.B(MappedClasses.lT, "keyBindSprint", "keySprint");
        this.B(MappedClasses.lT, "keyBindSneak", "keyShift");
        this.B(MappedClasses.lT, "keyBindInventory", "keyInventory");
        this.B(MappedClasses.lT, "keyBindDrop", "keyDrop");
        this.B(MappedClasses.lT, "keyBindFullscreen", "keyFullscreen");
        this.B(MappedClasses.lT, "hideGUI", "hideGui");
        this.G(MappedClasses.lT, "renderDistanceChunks", "renderDistance", MappedClasses.l4);
        this.B(MappedClasses.lT, "pointOfView", "cameraType");
        this.G(MappedClasses.lT, "mouseSensitivity", "sensitivity", MappedClasses.l4);
        this.G(MappedClasses.lT, "viewBobbing", "bobView", MappedClasses.l4);
        this.G(MappedClasses.lT, "guiScale", "guiScale", MappedClasses.l4);
        this.B(MappedClasses.lT, "pauseOnLostFocus", "pauseOnLostFocus");
        this.G(MappedClasses.lT, "gamma", "gamma", MappedClasses.l4);
        this.G(MappedClasses.lT, "fov", "fov", MappedClasses.l4);
        this.G(MappedClasses.lT, "screenEffectScale", "screenEffectScale", MappedClasses.l4);
        this.t(MappedClasses.lT, "setKeyBindingCode", "setKey");
    }

    protected void X() {
        if (this.isVanillaMinecraftAbsent()) {
            this.f(MappedClasses.U, "state", "a", false);
            this.f(MappedClasses.U, "enabled", "b", false);
        }
    }

    protected void AT() {
        this.B(MappedClasses.Vi, "field_239496_d_", "defaultRightToLeft");
    }

    protected void P() {
    }

    protected void y() {
        this.t(MappedClasses.uE, "getChunkProvider", "getChunkSource");
    }

    protected void I() {
        this.f(MappedClasses.u4, "getSprite", "get", MappedClasses.Db, MappedClasses.Vo);
    }

    protected void t() {
        this.t(MappedClasses.Fm, "processPacket", "handle");
    }

    protected void Ag() {
        this.t(MappedClasses.Yh, "isSameTeam", "isAlliedTo");
    }

    protected void UK() {
        this.t(MappedClasses.YE, "getBedDirection", "getBedOrientation");
    }

    protected void Ai() {
        this.B(MappedClasses.uR, "serverIP", "ip");
    }

    protected void Uh() {
        this.B(MappedClasses.ux, "children", "decomposedParts");
    }

    protected void Az() {
    }

    protected void UI() {
        this.B(MappedClasses.zm, "prevRenderYawOffset", "yBodyRotO");
        this.B(MappedClasses.zm, "prevRotationYawHead", "yHeadRotO");
        this.B(MappedClasses.zm, "moveStrafing", "xxa");
        this.B(MappedClasses.zm, "moveForward", "zza");
        this.B(MappedClasses.zm, "SPRINTING_SPEED_BOOST", "SPEED_MODIFIER_SPRINTING");
        this.B(MappedClasses.zm, "rotationYawHead", "yHeadRot");
        this.B(MappedClasses.zm, "isSwingInProgress", "swinging");
        this.B(MappedClasses.zm, "limbSwing", "animationPosition");
        this.B(MappedClasses.zm, "limbSwingAmount", "animationSpeed");
        this.B(MappedClasses.zm, "prevLimbSwingAmount", "animationSpeedOld");
        this.B(MappedClasses.zm, "renderYawOffset", "yBodyRot");
        this.B(MappedClasses.zm, "jumpTicks", "noJumpDelay");
        this.B(MappedClasses.zm, "maxHurtResistantTime", "invulnerableDuration");
        this.B(MappedClasses.zm, "swingProgressInt", "swingTime");
        this.B(MappedClasses.zm, "activePotionsMap", "activeEffects");
        this.B(MappedClasses.zm, "potionsNeedUpdate", "effectsDirty");
        this.B(MappedClasses.zm, "field_191988_bg", "zza");
        this.B(MappedClasses.zm, "itemInUseCount", "useItemRemaining");
        this.B(MappedClasses.zm, "itemInUse", "useItem");
        this.t(MappedClasses.zm, "getHeldItemMainhand", "getMainHandItem");
        this.t(MappedClasses.zm, "swingArm", "swing");
        this.f(MappedClasses.zm, "removePotionEffect", "removeEffect", Boolean.TYPE, MappedClasses.Vo);
        this.f(MappedClasses.zm, "isPotionActive", "hasEffect", Boolean.TYPE, MappedClasses.Vo);
        this.f(MappedClasses.zm, "getActivePotionEffect", "getEffect", MappedClasses.u3, MappedClasses.Vo);
        this.t(MappedClasses.zm, "getAttributeManager", "getAttributes");
        this.t(MappedClasses.zm, "livingTick", "aiStep");
        this.t(MappedClasses.zm, "canEntityBeSeen", "hasLineOfSight");
        this.t(MappedClasses.zm, "getActivePotionEffects", "getActiveEffects");
        this.t(MappedClasses.zm, "updatePotionEffects", "tickEffects");
        this.t(MappedClasses.zm, "isOnLadder", "onClimbable");
        this.t(MappedClasses.zm, "isActiveItemStackBlocking", "isBlocking");
        this.t(MappedClasses.zm, "getItemInUseCount", "getUseItemRemainingTicks");
        this.t(MappedClasses.zm, "getItemInUseMaxCount", "getTicksUsingItem");
        this.t(MappedClasses.zm, "getCreatureAttribute", "getMobType");
        this.t(MappedClasses.zm, "getItemStackFromSlot", "getItemBySlot");
        this.t(MappedClasses.zm, "getArmorInventoryList", "getArmorSlots");
        this.t(MappedClasses.zm, "getHeldItem", "getItemInHand");
        this.t(MappedClasses.zm, "getSwingProgress", "getAttackAnim");
        this.t(MappedClasses.zm, "setAIMoveSpeed", "setSpeed");
        this.t(MappedClasses.zm, "m_7292_", "addEffect");
        this.f(MappedClasses.zm, "getAttribute", "getAttribute", MappedClasses.FJ, new Class[0]);
    }

    protected void Uk() {
        this.f(MappedClasses.FS, "getItemEnchantmentLevel", "getItemEnchantmentLevel", Integer.TYPE, MappedClasses.Vo, MappedClasses.VK);
        this.f(MappedClasses.FS, "getEnchantmentLevel", "getItemEnchantmentLevel", Integer.TYPE, MappedClasses.Vo, MappedClasses.VK);
        this.f(MappedClasses.FS, "getEnchantmentModifierDamage", "getDamageProtection", Float.TYPE, MappedClasses.ZB, MappedClasses.P, MappedClasses.uB);
        this.t(MappedClasses.FS, "getDepthStriderModifier", "getDepthStrider");
        this.f(MappedClasses.FS, "getModifierForCreature", "modifyDamage", Float.TYPE, MappedClasses.ZB, MappedClasses.VK, MappedClasses.zc, MappedClasses.uB, Float.TYPE);
    }

    protected void G() {
        this.t(MappedClasses.uQ, "func_238421_b_", "m_92883_");
        this.t(MappedClasses.uQ, "func_243247_a", "m_92841_");
        this.t(MappedClasses.uQ, "func_243246_a", Vape.INSTANCE.isVanillaMinecraftPresent() ? "drawShadow" : "m_92763_");
        this.t(MappedClasses.uQ, "func_243248_b", Vape.INSTANCE.isVanillaMinecraftPresent() ? "draw" : "m_92889_");
    }

    protected void Ae() {
        this.B(MappedClasses.VG, "slot", "target");
    }

    protected void UN() {
        this.B(MappedClasses.ud, "bipedBodyWear", "jacket");
        this.B(MappedClasses.ud, "bipedRightArmwear", "rightSleeve");
        this.B(MappedClasses.ud, "bipedLeftArmwear", "leftSleeve");
        this.B(MappedClasses.ud, "bipedRightLegwear", "rightPants");
        this.B(MappedClasses.ud, "bipedLeftLegwear", "leftPants");
        this.B(MappedClasses.ud, "field_178730_v", "jacket");
        this.B(MappedClasses.ud, "field_178732_b", "rightSleeve");
        this.B(MappedClasses.ud, "field_178734_a", "leftSleeve");
        this.B(MappedClasses.ud, "field_178731_d", "rightPants");
        this.B(MappedClasses.ud, "field_178733_c", "leftPants");
    }

    protected void UA() {
        this.t(MappedClasses.zs, "loadRenderers", "allChanged");
        this.f(MappedClasses.zs, "renderLevel", "renderLevel", Void.TYPE, MappedClasses.uy, Boolean.TYPE, MappedClasses.lt, MappedClasses.FW, MappedClasses.zH, MappedClasses.qr, MappedClasses.qr);
        this.t(MappedClasses.zs, "markBlockRangeForRenderUpdate", "setBlocksDirty");
    }

    protected void Ud() {
        this.t(MappedClasses.Yr, "getUnformattedComponentText", "getContents");
    }

    protected void z() {
        this.t(MappedClasses.SET_VISIBILITY, "setAllVisible", "setAll");
    }

    protected void A2() {
        this.B(MappedClasses.e, "field_149615_a", "slot");
    }

    protected void Y() {
        this.B(MappedClasses.uW, "yaw", "yRot");
        this.B(MappedClasses.uW, "pitch", "xRot");
        this.B(MappedClasses.uW, "entityId", "id");
    }

    protected void AM() {
        this.t(MappedClasses.lR, "getDisplayName", "getFullname");
    }

    protected void A_() {
        this.B(MappedClasses.L, "textureLocation", "location");
    }

    protected void UM() {
        this.B(MappedClasses.lU, "logicOpcode", "eventId");
    }

    protected void D() {
        this.B(MappedClasses.Fo, "listShaders", "passes");
        this.t(MappedClasses.Fo, "createBindFramebuffers", "resize");
    }

    protected void w() {
        this.B(MappedClasses.Dk, "xCoord", "x");
        this.B(MappedClasses.Dk, "yCoord", "y");
        this.B(MappedClasses.Dk, "zCoord", "z");
    }

    protected void Am() {
        this.B(MappedClasses.FO, "outboundPacketsQueue", "pendingActions");
        this.t(MappedClasses.FO, "getNetHandler", "getPacketListener");
        this.t(MappedClasses.FO, "flushOutboundQueue", "flushQueue");
        this.f(MappedClasses.FO, "dispatchPacket", "sendPacket", Void.TYPE, MappedClasses.Fm, MappedClasses.H, Boolean.TYPE);
        this.t(MappedClasses.FO, "func_179290_a", "send");
    }

    protected void H() {
    }

    protected void a() {
        this.t(MappedClasses.la, "getBoundingBox", "bounds");
    }

    protected void E() {
        this.B(MappedClasses.z5, "movementInput", "input");
        this.B(MappedClasses.z5, "clientSneakState", "wasShiftKeyDown");
        this.B(MappedClasses.z5, "lastReportedPosX", "xLast");
        this.B(MappedClasses.z5, "lastReportedPosY", "yLast1");
        this.B(MappedClasses.z5, "lastReportedPosZ", "zLast");
        this.B(MappedClasses.z5, "lastReportedYaw", "yRotLast");
        this.B(MappedClasses.z5, "lastReportedPitch", "xRotLast");
        this.B(MappedClasses.z5, "serverSprintState", "wasSprinting");
        this.B(MappedClasses.z5, "positionUpdateTicks", "positionReminder");
        this.B(MappedClasses.z5, "timeInPortal", "spinningEffectIntensity");
        this.B(MappedClasses.z5, "prevTimeInPortal", "oSpinningEffectIntensity");
        this.B(MappedClasses.z5, "field_191988_bg", "zza");
        this.t(MappedClasses.z5, "onUpdateWalkingPlayer", "sendPosition");
        this.t(MappedClasses.z5, "livingTick", "tick");
    }

    protected void AA() {
        this.B(MappedClasses.zp, "keyCode", "value");
    }

    protected void AR() {
        this.t(MappedClasses.ZK, "getImpl", "immediate");
    }

    protected void UY() {
        this.f(MappedClasses.F7, "getBiome", "getBiome", MappedClasses.Vo, MappedClasses.lf);
    }

    protected void r() {
        this.t(MappedClasses.qg, "forEntity", "of");
    }

    protected void AP() {
        this.t(MappedClasses.Q, "getInputByCode", "getKey");
    }

    protected void AB() {
        this.B(MappedClasses.ut, "glTextureId", "id");
    }

    protected void u() {
        this.B(MappedClasses.uB, "damageType", "msgId");
        this.t(MappedClasses.uB, "causePlayerDamage", "playerAttack");
        this.t(MappedClasses.uB, "getImmediateSource", "getDirectEntity");
    }

    protected void UF() {
        this.t(MappedClasses.MOB_SPAWNER_TILE_ENTITY, "getSpawnerBaseLogic", "getSpawner");
        this.t(MappedClasses.MOB_SPAWNER_TILE_ENTITY, "func_145881_a", "getSpawner");
    }

    protected void M() {
        this.t(MappedClasses.l0, "getSizeInventory", "getContainerSize");
        this.t(MappedClasses.l0, "getStackInSlot", "getItem");
    }

    protected void f() {
        this.G(MappedClasses.VK, "tag", "components", MappedClasses.DP);
        this.t(MappedClasses.VK, "getEnchantmentTagList", "getEnchantmentTags");
        this.t(MappedClasses.VK, "getTranslationKey", "getDescriptionId");
        this.t(MappedClasses.VK, "getDisplayName", "getHoverName");
        this.t(MappedClasses.VK, "getDamage", "getDamageValue");
        this.t(MappedClasses.VK, "setDamage", "setDamageValue");
        this.f(MappedClasses.VK, "addEnchantment", "enchant", Void.TYPE, MappedClasses.Vo, Integer.TYPE);
        this.f(MappedClasses.VK, "func_82840_a", "getTooltipLines", List.class, MappedClasses.D, MappedClasses.Yl, MappedClasses.Zx);
        this.f(MappedClasses.VK, "canDestroy", "canPlaceOnBlockInAdventureMode", Boolean.TYPE, MappedClasses.Vg);
        this.t(MappedClasses.VK, "m_150930_", "is");
    }

    protected void Ar() {
        this.B(MappedClasses.ZX, "INSTANCE", "instance");
        this.t(MappedClasses.ZX, "draw", "end");
    }

    protected void UW() {
        this.B(MappedClasses.uu, "attachedEntity", "entity");
        this.B(MappedClasses.uu, "particleTypes", "particleType");
    }

    protected void Ap() {
        this.B(MappedClasses.qD, "moving", "hasPos");
        this.B(MappedClasses.qD, "yaw", "yRot");
        this.B(MappedClasses.qD, "pitch", "xRot");
    }

    protected void Uc() {
        this.t(MappedClasses.qF, "func_216352_a", "miss");
    }

    protected void AL() {
        this.B(MappedClasses.qF, "pos", "blockPos");
        this.B(MappedClasses.qF, "face", "direction");
    }

    protected void g() {
        this.B(MappedClasses.za, "canCollide", "hasCollision");
        this.B(MappedClasses.za, "slipperiness", "friction");
        this.t(MappedClasses.za, "onBlockActivated", "useWithoutItem");
    }

    protected void n() {
        this.B(MappedClasses.lX, "isDrawing", "building");
        this.G(MappedClasses.lX, "byteBuffer", "buffer", MappedClasses.Fx);
    }

    protected void AJ() {
        this.B(MappedClasses.Yd, "rotateAngleX", "xRot");
        this.B(MappedClasses.Yd, "rotateAngleY", "yRot");
        this.B(MappedClasses.Yd, "rotateAngleZ", "zRot");
        this.B(MappedClasses.Yd, "rotationPointX", "x");
        this.B(MappedClasses.Yd, "rotationPointY", "y");
        this.B(MappedClasses.Yd, "rotationPointZ", "z");
        this.B(MappedClasses.Yd, "showModel", "visible");
    }

    protected void Ab() {
        this.B(MappedClasses.uP, "fontRenderer", "font");
        this.B(MappedClasses.uP, "renderManager", "entityRenderDispatcher");
        this.B(MappedClasses.uP, "leftClickCounter", "missTime");
        this.B(MappedClasses.uP, "world", "level");
        this.B(MappedClasses.uP, "isWindowFocused", "windowActive");
        this.B(MappedClasses.uP, "worldRenderer", "levelRenderer");
        this.B(MappedClasses.uP, "mainWindow", "window");
        this.B(MappedClasses.uP, "networkManager", "pendingConnection");
        this.B(MappedClasses.uP, "integratedServer", "singleplayerServer");
        this.B(MappedClasses.uP, "debugFPS", "fps");
        this.B(MappedClasses.uP, "currentScreen", "screen");
        this.B(MappedClasses.uP, "gameSettings", "options");
        this.B(MappedClasses.uP, "renderViewEntity", "cameraEntity");
        this.B(MappedClasses.uP, "playerController", "gameMode");
        this.B(MappedClasses.uP, "objectMouseOver", "hitResult");
        this.B(MappedClasses.uP, "pointedEntity", "crosshairPickEntity");
        this.B(MappedClasses.uP, "rightClickDelayTimer", "rightClickDelay");
        this.B(MappedClasses.uP, "mouseHelper", "mouseHandler");
        this.B(MappedClasses.uP, "session", "user");
        this.B(MappedClasses.uP, "ingameGUI", "gui");
        this.B(MappedClasses.uP, "integratedServerIsRunning", "isLocalServer");
        this.B(MappedClasses.uP, "pointedEntity", "crosshairPickEntity");
        this.B(MappedClasses.uP, "potionSprites", "mobEffectTextures");
        this.f(MappedClasses.uP, "clickMouse", "startAttack", Boolean.TYPE, new Class[0]);
        this.t(MappedClasses.uP, "rightClickMouse", "startUseItem");
        this.t(MappedClasses.uP, "displayGuiScreen", "setScreen");
        this.t(MappedClasses.uP, "getFramebuffer", "getMainRenderTarget");
        this.t(MappedClasses.uP, "getIntegratedServer", "getSingleplayerServer");
        this.t(MappedClasses.uP, "sendClickBlockToController", "continueAttack");
        this.t(MappedClasses.uP, "runTick", "tick");
        this.t(MappedClasses.uP, "runGameLoop", "runTick");
    }


    protected void O() {
        this.t(MappedClasses.f, "intersectsWith", "intersects");
        this.t(MappedClasses.f, "m_71049_", "intersects");
    }

    protected void b() {
        this.t(MappedClasses.Fi, "toggleFullscreen", "updateFullscreen");
    }

    protected void L() {
        this.f(MappedClasses.zt, "getLocationSkin", "getSkin", MappedClasses.uZ, new Class[0]);
    }

    protected void W() {
        this.t(MappedClasses.uk, "calcSideHit", "getDirection");
        this.t(MappedClasses.uk, "func_72317_d", "move");
        this.t(MappedClasses.uk, "func_216365_b", "clip");
        this.t(MappedClasses.uk, "func_72314_b", "inflate");
        this.t(MappedClasses.uk, "func_186662_g", "inflate");
        this.t(MappedClasses.uk, "func_72318_a", "contains");
        this.t(MappedClasses.uk, "func_216361_a", "expandTowards");
    }

    protected void At() {
        this.B(MappedClasses.Vm, "field_239501_a_", "language");
        this.t(MappedClasses.Vm, "format", "get");
    }

    protected void q() {
        this.B(MappedClasses.ZI, "pos", "worldPosition");
    }

    protected void AU() {
        this.G(MappedClasses.u3, "potion", "effect", MappedClasses.Vo);
    }

    protected void Al() {
        this.B(MappedClasses.FY, "slotIndex", "id");
    }

    protected void Av() {
        this.B(MappedClasses.zZ, "lowerChestInventory", "container");
    }

    protected void UZ() {
        this.f(MappedClasses.YS, "drawEntityOnScreen", "renderEntityInInventory", Void.TYPE, MappedClasses.m, Float.TYPE, Float.TYPE, Integer.TYPE, MappedClasses.qb, MappedClasses.qI, MappedClasses.qI, MappedClasses.P);
    }
}

