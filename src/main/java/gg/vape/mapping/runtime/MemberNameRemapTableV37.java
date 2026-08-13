package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.runtime.MemberNameRemapTable;

public class MemberNameRemapTableV37
extends MemberNameRemapTable {
    protected void R4() {
        this.t(MappedClasses.lf, "down", "below");
        this.t(MappedClasses.lf, "up", "above");
        this.t(MappedClasses.lf, "func_177984_a", "m_7494_");
        this.t(MappedClasses.lf, "func_177978_c", "m_142127_");
        this.t(MappedClasses.lf, "func_177974_f", "m_142126_");
        this.t(MappedClasses.lf, "func_177968_d", "m_142128_");
        this.t(MappedClasses.lf, "func_177976_e", "m_142125_");
        this.t(MappedClasses.lf, "offset", "relative");
    }

    protected void R7() {
        this.t(MappedClasses.VIS_GRAPH, "computeVisibility", "resolve");
        this.t(MappedClasses.VIS_GRAPH, "setOpaqueCube", "setOpaque");
    }

    protected void R() {
        this.B(MappedClasses.Dk, "xCoord", "x");
        this.B(MappedClasses.Dk, "yCoord", "y");
        this.B(MappedClasses.Dk, "zCoord", "z");
    }

    protected void Rw() {
        this.B(MappedClasses.L, "textureLocation", "location");
    }

    protected void H() {
        this.t(MappedClasses.VK, "getEnchantmentTagList", "getEnchantmentTags");
        this.t(MappedClasses.VK, "getTranslationKey", "getDescriptionId");
        this.t(MappedClasses.VK, "getDisplayName", "getHoverName");
        this.t(MappedClasses.VK, "getDamage", "getDamageValue");
        this.t(MappedClasses.VK, "setDamage", "setDamageValue");
        this.t(MappedClasses.VK, "addEnchantment", "enchant");
        this.t(MappedClasses.VK, "func_82840_a", Vape.INSTANCE.isVanillaMinecraftPresent() ? "getTooltipLines" : "m_41651_");
        this.t(MappedClasses.VK, "canDestroy", "hasAdventureModePlaceTagForBlock");
    }

    protected void Rq() {
        this.B(MappedClasses.DN, "position", "pos");
        this.B(MappedClasses.DN, "facing", "direction");
    }

    protected void R5() {
        this.t(MappedClasses.zH, "updateLightmap", "updateLightTexture");
        this.t(MappedClasses.zH, "disableLightmap", "turnOffLightLayer");
        this.t(MappedClasses.zH, "enableLightmap", "turnOnLightLayer");
    }

    protected void RF() {
        this.B(MappedClasses.Yk, "blend", "mode");
        this.B(MappedClasses.Yk, "field_179213_a", "f_84577_");
    }

    protected void x() {
        this.B(MappedClasses.uK, "category", "biomeCategory");
    }

    protected void r() {
        this.B(MappedClasses.ud, "bipedBodyWear", "jacket");
        this.B(MappedClasses.ud, "bipedRightArmwear", "rightSleeve");
        this.B(MappedClasses.ud, "bipedLeftArmwear", "leftSleeve");
        this.B(MappedClasses.ud, "bipedRightLegwear", "rightPants");
        this.B(MappedClasses.ud, "bipedLeftLegwear", "leftPants");
        this.B(MappedClasses.ud, "field_178730_v", "f_103378_");
        this.B(MappedClasses.ud, "field_178732_b", "f_103375_");
        this.B(MappedClasses.ud, "field_178734_a", "f_103374_");
        this.B(MappedClasses.ud, "field_178731_d", "f_103377_");
        this.B(MappedClasses.ud, "field_178733_c", "f_103376_");
    }

    protected void RI() {
        this.B(MappedClasses.G, "matrix", "pose");
    }

    protected void Rs() {
        this.B(MappedClasses.q0, "index", "data3d");
        this.B(MappedClasses.q0, "opposite", "oppositeIndex");
        this.t(MappedClasses.q0, "getDirectionVec", "getNormal");
        this.t(MappedClasses.q0, "getName2", "getName");
        this.t(MappedClasses.q0, "func_210769_a", "m_122366_");
        this.t(MappedClasses.q0, "getHorizontalIndex", "get2DDataValue");
    }

    protected void o() {
        this.B(MappedClasses.FY, "slotIndex", "filterFlag");
    }

    protected void Tk() {
        this.t(MappedClasses.uV, "getEffectsFromStack", "getMobEffects");
    }

    protected void I() {
        this.B(MappedClasses.Vk, "currentSectionName", "path");
    }

    protected void TT() {
        this.B(MappedClasses.lU, "logicOpcode", "eventId");
    }

    protected void Ts() {
        this.B(MappedClasses.Ft, "INVENTORY_BACKGROUND", "INVENTORY_LOCATION");
        this.B(MappedClasses.Ft, "guiLeft", "leftPos");
        this.B(MappedClasses.Ft, "guiTop", "topPos");
        this.B(MappedClasses.Ft, "xSize", "imageWidth");
        this.B(MappedClasses.Ft, "ySize", "imageHeight");
        this.B(MappedClasses.Ft, "field_147002_h", "f_97732_");
        this.B(MappedClasses.Ft, "container", "menu");
        this.t(MappedClasses.Ft, "handleMouseClick", "slotClicked");
        this.t(MappedClasses.Ft, "getSelectedSlot", "findSlot");
    }

    protected void z() {
        this.B(MappedClasses.qD, "moving", "hasPos");
        this.B(MappedClasses.qD, "yaw", "yRot");
        this.B(MappedClasses.qD, "pitch", "xRot");
    }

    protected void T1() {
        this.t(MappedClasses.Y, "func_237498_g_", "createFormattedDisplayName");
    }

    protected void u() {
        this.B(MappedClasses.lt, "pitch", "xRot");
        this.B(MappedClasses.lt, "yaw", "yRot");
        this.B(MappedClasses.lt, "pos", "position");
        this.B(MappedClasses.lt, "renderViewEntity", "entity");
        this.B(MappedClasses.lt, "world", "level");
        this.t(MappedClasses.lt, "update", "setup");
    }

    protected void Rl() {
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
        this.B(MappedClasses.uP, "currentServerData", "currentServer");
        this.B(MappedClasses.uP, "pointedEntity", "crosshairPickEntity");
        this.B(MappedClasses.uP, "potionSprites", "mobEffectTextures");
        this.t(MappedClasses.uP, "clickMouse", "startAttack");
        this.t(MappedClasses.uP, "rightClickMouse", "startUseItem");
        this.t(MappedClasses.uP, "displayGuiScreen", "setScreen");
        this.t(MappedClasses.uP, "getFramebuffer", "getMainRenderTarget");
        this.t(MappedClasses.uP, "getIntegratedServer", "getSingleplayerServer");
        this.t(MappedClasses.uP, "sendClickBlockToController", "continueAttack");
        this.t(MappedClasses.uP, "runTick", "tick");
        this.t(MappedClasses.uP, "runGameLoop", "runTick");
    }

    protected void R0() {
        this.B(MappedClasses.u3, "potion", "effect");
    }

    protected void Tt() {
        this.t(MappedClasses.Dn, "pos", "vertex");
    }

    protected void U() {
        this.B(MappedClasses.ld, "isHittingBlock", "isDestroying");
        this.B(MappedClasses.ld, "curBlockDamageMP", "destroyProgress");
        this.t(MappedClasses.ld, "processRightClick", "useItem");
        this.t(MappedClasses.ld, "getBlockReachDistance", "getPickRange");
        this.t(MappedClasses.ld, "extendedReach", "hasFarPickRange");
        this.t(MappedClasses.ld, "attackEntity", "attack");
        this.t(MappedClasses.ld, "syncCurrentPlayItem", "ensureHasSentCarriedItem");
        this.t(MappedClasses.ld, "onStoppedUsingItem", "releaseUsingItem");
    }

    protected void G() {
    }

    protected void RE() {
        this.B(MappedClasses.qe, "field_149152_f", "f_132110_");
        this.B(MappedClasses.qe, "field_149153_g", "f_132111_");
        this.B(MappedClasses.qe, "field_149159_h", "f_132112_");
    }

    protected void TN() {
        this.B(MappedClasses.Yd, "rotateAngleX", "xRot");
        this.B(MappedClasses.Yd, "rotateAngleY", "yRot");
        this.B(MappedClasses.Yd, "rotateAngleZ", "zRot");
        this.B(MappedClasses.Yd, "rotationPointX", "x");
        this.B(MappedClasses.Yd, "rotationPointY", "y");
        this.B(MappedClasses.Yd, "rotationPointZ", "z");
        this.B(MappedClasses.Yd, "showModel", "visible");
    }

    protected void K() {
        this.t(MappedClasses.Zc, "getGameProfile", "getProfile");
        this.t(MappedClasses.Zc, "getResponseTime", "getLatency");
    }

    protected void w() {
        this.B(MappedClasses.za, "canCollide", "hasCollision");
        this.B(MappedClasses.za, "slipperiness", "friction");
        this.t(MappedClasses.za, "onBlockActivated", "use");
    }

    protected void Rz() {
        this.B(MappedClasses.FW, "SHADERS_TEXTURES", "EFFECTS");
        this.B(MappedClasses.FW, "shaderGroup", "postEffect");
        this.B(MappedClasses.FW, "lightmapTexture", "lightTexture");
        this.B(MappedClasses.FW, "activeRender", "mainCamera");
        this.B(MappedClasses.FW, "rendererUpdateCount", "tick");
        this.B(MappedClasses.FW, "fovModifierHand", "fov");
        this.B(MappedClasses.FW, "fovModifierHandPrev", "oldFov");
        this.B(MappedClasses.FW, "farPlaneDistance", "renderDistance");
        this.B(MappedClasses.FW, "useShader", "effectActive");
        this.t(MappedClasses.FW, "renderWorld", "renderLevel");
        this.t(MappedClasses.FW, "hurtCameraEffect", "bobHurt");
        this.t(MappedClasses.FW, "applyBobbing", "bobView");
        this.t(MappedClasses.FW, "updateShaderGroupSize", "resize");
        this.t(MappedClasses.FW, "getMouseOver", "pick");
        this.t(MappedClasses.FW, "updateCameraAndRender", "render");
    }

    protected void Tz() {
        this.B(MappedClasses.qz, "posX", "xa");
        this.B(MappedClasses.qz, "posY", "ya");
        this.B(MappedClasses.qz, "posZ", "za");
        this.B(MappedClasses.qz, "field_149069_g", "f_132506_");
        this.t(MappedClasses.qz, "func_149065_a", "m_132519_");
        this.t(MappedClasses.qz, "func_149066_f", "m_132531_");
        this.t(MappedClasses.qz, "func_149063_g", "m_132532_");
        this.t(MappedClasses.qz, "func_149060_h", "m_132533_");
    }

    protected void TO() {
        this.B(MappedClasses.ut, "glTextureId", "id");
    }

    protected void TL() {
        this.t(MappedClasses.qr, "func_226595_a_", "m_27644_");
    }

    protected void a() {
        this.t(MappedClasses.zJ, "func_217299_a", "m_45547_");
    }

    protected void RK() {
    }

    protected void TC() {
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

    protected void RG() {
        this.B(MappedClasses.lb, "BLOCK_TO_ITEM", "BY_BLOCK");
        this.t(MappedClasses.lb, "getIdFromItem", "getId");
        this.t(MappedClasses.lb, "getItemById", "byId");
        this.t(MappedClasses.lb, "fillItemGroup", "fillItemCategory");
        this.t(MappedClasses.lb, "getName", "getDescription");
        this.t(MappedClasses.lb, "getDisplayName", "getName");
    }

    protected void Rt() {
        this.B(MappedClasses.z8, "zLevel", "blitOffset");
        this.t(MappedClasses.z8, "renderItemIntoGUI", "renderAndDecorateFakeItem");
        this.t(MappedClasses.z8, "renderItemOverlays", "renderGuiItemDecorations");
    }

    protected void y() {
        this.t(MappedClasses.SET_VISIBILITY, "setAllVisible", "setAll");
    }

    protected void Ry() {
        this.t(MappedClasses.ZK, "getImpl", "immediate");
    }

    protected void F() {
        this.t(MappedClasses.lg, "chunkExists", "hasChunk");
    }

    protected void RA() {
        this.t(MappedClasses.u6, "func_237500_a_", "m_83348_");
    }

    protected void TD() {
        this.B(MappedClasses.qS, "username", "name");
        this.B(MappedClasses.qS, "playerID", "uuid");
    }

    protected void Rg() {
        this.B(MappedClasses.Zu, "chatComponent", "message");
        this.t(MappedClasses.Zu, "getChatComponent", "getMessage");
    }

    protected void Rk() {
        this.t(MappedClasses.T, "getShaderManager", "getEffect");
    }

    protected void R9() {
        this.t(MappedClasses.DQ, "getLast", "last");
        this.t(MappedClasses.DQ, "push", "pushPose");
        this.t(MappedClasses.DQ, "pop", "popPose");
        this.t(MappedClasses.DQ, "rotate", "mulPose");
    }

    protected void RV() {
        this.B(MappedClasses.zp, "keyCode", "value");
    }

    protected void S() {
        this.t(MappedClasses.YS, "drawEntityOnScreen", "renderEntityInInventory");
    }

    protected void RH() {
        this.B(MappedClasses.zw, "yaw", "yRot");
        this.B(MappedClasses.zw, "pitch", "xRot");
        this.B(MappedClasses.zw, "field_148936_d", "f_132799_");
        this.B(MappedClasses.zw, "field_148937_e", "f_132800_");
        this.t(MappedClasses.zw, "func_179834_f", "m_132826_");
    }

    protected void R6() {
        this.B(MappedClasses.VG, "slot", "target");
    }


    protected void R_() {
        this.B(MappedClasses.e, "field_149615_a", "f_134488_");
    }

    protected void v$src$V$1wzakm() {
        this.B(MappedClasses.YQ, "slotNumber", "index");
        this.t(MappedClasses.YQ, "getStack", "getItem");
    }

    protected void TQ() {
        this.t(MappedClasses.la, "getBoundingBox", "bounds");
    }

    protected void RU() {
        this.t(MappedClasses.zs, "loadRenderers", "allChanged");
        this.t(MappedClasses.zs, "updateCameraAndRender", "renderLevel");
        this.t(MappedClasses.zs, "markBlockRangeForRenderUpdate", "setBlocksDirty");
        this.t(MappedClasses.zs, "updateChunks", "compileChunksUntil");
    }

    protected void RB() {
        this.B(MappedClasses.Yg, "tagMap", "tags");
    }

    protected void R3() {
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
        this.B(MappedClasses.zc, "rand", "random");
        this.B(MappedClasses.zc, "noClip", "noPhysics");
        this.B(MappedClasses.zc, "hurtResistantTime", "invulnerableTime");
        this.B(MappedClasses.zc, "entityUniqueID", "uuid");
        this.B(MappedClasses.zc, "positionVec", "position");
        this.B(MappedClasses.zc, "motion", "deltaMovement");
        this.B(MappedClasses.zc, "size", "dimensions");
        this.B(MappedClasses.zc, "collidedHorizontally", "horizontalCollision");
        this.B(MappedClasses.zc, "collidedVertically", "verticalCollision");
        this.B(MappedClasses.zc, "world", "level");
        this.B(MappedClasses.zc, "field_242272_av", "packetCoordinates");
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
        this.t(MappedClasses.zc, "setFire", "setSecondsOnFire");
        this.t(MappedClasses.zc, "setFlag", "setSharedFlag");
        this.t(MappedClasses.zc, "setPositionAndRotation", Vape.INSTANCE.isVanillaMinecraftPresent() ? "absMoveTo" : "m_19890_");
        this.t(MappedClasses.zc, "setSneaking", "setShiftKeyDown");
        this.t(MappedClasses.zc, "getLook", "getViewVector");
        this.t(MappedClasses.zc, "doBlockCollisions", "checkInsideBlocks");
        this.t(MappedClasses.zc, "func_145775_I", "m_20101_");
        this.t(MappedClasses.zc, "remove", "unsetRemoved");
        this.t(MappedClasses.zc, "getLowestRidingEntity", "getRootVehicle");
        this.t(MappedClasses.zc, "func_184231_a", "m_7840_");
        this.t(MappedClasses.zc, "canBeCollidedWith", "isPickable");
    }

    protected void RW() {
        this.B(MappedClasses.lF, "field_175252_a", "f_94518_");
    }

    protected void Ti() {
        this.t(MappedClasses.Yh, "isSameTeam", "isAlliedTo");
    }

    protected void Tq() {
        this.B(MappedClasses.Db, "atlasTexture", "atlas");
    }

    protected void RC() {
        this.B(MappedClasses.FX, "properties", "values");
    }

    protected void T8() {
        this.t(MappedClasses.FV, "func_219895_b", "m_6182_");
    }

    protected void h() {
        this.B(MappedClasses.ZN, "TALL_PLANTS", "REPLACEABLE_PLANT");
        this.t(MappedClasses.ZN, "blocksMovement", "blocksMotion");
    }

    protected void n() {
        this.t(MappedClasses.DX, "getPlayerName", "getOwner");
    }

    protected void R1() {
        this.t(MappedClasses.YE, "getBedDirection", "getBedOrientation");
    }

    protected void T4() {
        this.B(MappedClasses.Y6, "name", "location");
    }

    protected void c() {
        this.t(MappedClasses.FJ, "applyModifier", "addModifier");
    }

    protected void RS() {
        this.B(MappedClasses.DR, "pressed", "isDown");
        this.B(MappedClasses.DR, "pressTime", "clickCount");
        this.B(MappedClasses.DR, "keyCode", "key");
        this.t(MappedClasses.DR, "isPressed", "consumeClick");
        this.t(MappedClasses.DR, "unPressAllKeys", "releaseAll");
        this.t(MappedClasses.DR, "setPressed", "setDown");
        this.t(MappedClasses.DR, "unpressKey", "release");
        this.t(MappedClasses.DR, "setKeyBindState", "set");
        this.t(MappedClasses.DR, "onTick", "click");
        this.t(MappedClasses.DR, "func_151470_d", "m_90857_");
    }

    protected void E() {
        this.t(MappedClasses.ls, "fogDensity", Vape.INSTANCE.isVanillaMinecraftPresent() ? "setShaderFogStart" : "m_157445_");
    }

    protected void Rn() {
        this.t(MappedClasses.qP, "func_72436_e", "m_82554_");
    }

    protected void Rf() {
        this.B(MappedClasses.F5, "attackDamage", "damage");
    }

    protected void RN() {
        this.t(MappedClasses.lh, "getAttackDamage", "getAttackDamageBonus");
    }

    protected void X() {
        this.B(MappedClasses.uR, "serverIP", "ip");
    }

    protected void Ru() {
        this.t(MappedClasses.uk, "calcSideHit", "getDirection");
        this.t(MappedClasses.uk, "func_72317_d", "m_82386_");
        this.t(MappedClasses.uk, "func_216365_b", "m_82371_");
        this.t(MappedClasses.uk, "func_72314_b", "m_82377_");
        this.t(MappedClasses.uk, "func_186662_g", "m_82400_");
        this.t(MappedClasses.uk, "func_72318_a", "m_82390_");
        this.t(MappedClasses.uk, "func_216361_a", "m_82369_");
    }

    protected void RM() {
        this.t(MappedClasses.YL, "getCollisionShapes", "getBlockCollisions");
    }

    protected void TX() {
        this.t(MappedClasses.D3, "get", "byId");
        this.t(MappedClasses.D3, "removeAttributesModifiersFromEntity", "removeAttributeModifiers");
    }

    protected void Ri() {
        this.t(MappedClasses.m, "func_238470_a_", "m_93200_");
    }

    protected void Ra() {
        this.t(MappedClasses.Dt, "bindTexture", "_bind");
    }

    protected void RP() {
        this.t(MappedClasses.u4, "getSprite", "get");
    }

    protected void P() {
        this.B(MappedClasses.Ys, "xVelocity", "accumulatedDX");
        this.B(MappedClasses.Ys, "yVelocity", "accumulatedDY");
        this.B(MappedClasses.Ys, "mouseX", "xpos");
        this.B(MappedClasses.Ys, "mouseY", "ypos");
        this.t(MappedClasses.Ys, "ungrabMouse", "releaseMouse");
    }

    protected void T3() {
        this.B(MappedClasses.qU, "damageReduceAmount", "defense");
    }

    protected void p() {
        this.B(MappedClasses.zZ, "lowerChestInventory", "container");
    }

    protected void j() {
        this.B(MappedClasses.uW, "yaw", "yRot");
        this.B(MappedClasses.uW, "pitch", "xRot");
    }

    protected void Rj() {
        this.B(MappedClasses.ITEM_FOOD, "value", "nutrition");
        this.B(MappedClasses.ITEM_FOOD, "saturation", "saturationModifier");
    }

    protected void m() {
        this.B(MappedClasses.Vm, "field_239501_a_", "f_118934_");
        this.t(MappedClasses.Vm, "format", "get");
    }

    protected void l() {
        this.t(MappedClasses.Yr, "getUnformattedComponentText", "getContents");
    }

    protected void Tl() {
        this.t(MappedClasses.l0, "getSizeInventory", "getContainerSize");
        this.t(MappedClasses.l0, "getStackInSlot", "getItem");
    }

    protected void RL() {
        this.B(MappedClasses.uu, "attachedEntity", "entity");
        this.B(MappedClasses.uu, "particleTypes", "particleType");
    }

    protected void RO() {
        this.B(MappedClasses.z5, "movementInput", "input");
        this.B(MappedClasses.z5, "clientSneakState", "wasShiftKeyDown");
        this.B(MappedClasses.z5, "lastReportedPosX", "xLast");
        this.B(MappedClasses.z5, "lastReportedPosY", "yLast1");
        this.B(MappedClasses.z5, "lastReportedPosZ", "zLast");
        this.B(MappedClasses.z5, "lastReportedYaw", "yRotLast");
        this.B(MappedClasses.z5, "lastReportedPitch", "xRotLast");
        this.B(MappedClasses.z5, "serverSprintState", "wasSprinting");
        this.B(MappedClasses.z5, "positionUpdateTicks", "positionReminder");
        this.B(MappedClasses.z5, "timeInPortal", "portalTime");
        this.B(MappedClasses.z5, "prevTimeInPortal", "oPortalTime");
        this.B(MappedClasses.z5, "field_191988_bg", "f_20902_");
        this.t(MappedClasses.z5, "sendChatMessage", "chat");
        this.t(MappedClasses.z5, "onUpdateWalkingPlayer", "sendPosition");
        this.t(MappedClasses.z5, "livingTick", "tick");
    }

    protected void e() {
        this.t(MappedClasses.Fj, "func_196954_c", Vape.INSTANCE.isVanillaMinecraftPresent() ? "getShape" : "m_60808_");
    }

    protected void RD() {
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
        this.B(MappedClasses.lT, "renderDistanceChunks", "renderDistance");
        this.B(MappedClasses.lT, "pointOfView", "cameraType");
        this.B(MappedClasses.lT, "mouseSensitivity", "sensitivity");
        this.B(MappedClasses.lT, "viewBobbing", "bobView");
        this.t(MappedClasses.lT, "setKeyBindingCode", "setKey");
    }

    protected void i() {
        this.t(MappedClasses.qF, "func_216352_a", "m_82426_");
    }

    protected void RY() {
        this.t(MappedClasses.uQ, "func_238421_b_", "m_92883_");
        this.t(MappedClasses.uQ, "func_243247_a", "m_92841_");
        this.t(MappedClasses.uQ, "func_243246_a", Vape.INSTANCE.isVanillaMinecraftPresent() ? "drawShadow" : "m_92763_");
        this.t(MappedClasses.uQ, "func_243248_b", Vape.INSTANCE.isVanillaMinecraftPresent() ? "draw" : "m_92889_");
    }

    protected void Z() {
        this.B(MappedClasses.FO, "outboundPacketsQueue", "queue");
        this.t(MappedClasses.FO, "getNetHandler", "getPacketListener");
        this.t(MappedClasses.FO, "flushOutboundQueue", "flushQueue");
        this.t(MappedClasses.FO, "dispatchPacket", "sendPacket");
        this.t(MappedClasses.FO, "func_179290_a", "m_129512_");
    }

    protected void Rr() {
        this.B(MappedClasses.ux, "children", "decomposedParts");
    }

    protected void f() {
        this.t(MappedClasses.lR, "getDisplayName", "getFullname");
    }

    protected void RX() {
        this.B(MappedClasses.zV, "bipedHead", "head");
        this.B(MappedClasses.zV, "bipedHeadwear", "hat");
        this.B(MappedClasses.zV, "bipedRightArm", "rightArm");
        this.B(MappedClasses.zV, "bipedLeftArm", "leftArm");
        this.B(MappedClasses.zV, "bipedRightLeg", "rightLeg");
        this.B(MappedClasses.zV, "bipedLeftLeg", "leftLeg");
    }

    protected void TI() {
        this.B(MappedClasses.DD, "blockPosition", "pos");
    }

    protected void O() {
        this.B(MappedClasses.ZX, "buffer", "builder");
        this.t(MappedClasses.ZX, "draw", "end");
    }

    protected void TE() {
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

    protected void R8() {
        this.B(MappedClasses.zm, "prevRenderYawOffset", "yBodyRotO");
        this.B(MappedClasses.zm, "prevRotationYawHead", "yHeadRotO");
        this.B(MappedClasses.zm, "moveStrafing", "xxa");
        this.B(MappedClasses.zm, "moveForward", "zza");
        this.B(MappedClasses.zm, "SPRINTING_SPEED_BOOST", "SPEED_MODIFIER_SPRINTING");
        this.B(MappedClasses.zm, "rotationYawHead", "yHeadRot");
        this.B(MappedClasses.zm, "isSwingInProgress", "swinging");
        this.B(MappedClasses.zm, "jumpMovementFactor", "flyingSpeed");
        this.B(MappedClasses.zm, "limbSwing", "animationPosition");
        this.B(MappedClasses.zm, "limbSwingAmount", "animationSpeed");
        this.B(MappedClasses.zm, "prevLimbSwingAmount", "animationSpeedOld");
        this.B(MappedClasses.zm, "renderYawOffset", "yBodyRot");
        this.B(MappedClasses.zm, "jumpTicks", "noJumpDelay");
        this.B(MappedClasses.zm, "maxHurtResistantTime", "invulnerableDuration");
        this.B(MappedClasses.zm, "swingProgressInt", "swingTime");
        this.B(MappedClasses.zm, "activePotionsMap", "activeEffects");
        this.B(MappedClasses.zm, "potionsNeedUpdate", "effectsDirty");
        this.B(MappedClasses.zm, "field_191988_bg", "f_20902_");
        this.B(MappedClasses.zm, "itemInUseCount", "useItemRemaining");
        this.B(MappedClasses.zm, "itemInUse", "useItem");
        this.t(MappedClasses.zm, "getHeldItemMainhand", "getMainHandItem");
        this.t(MappedClasses.zm, "swingArm", "swing");
        this.t(MappedClasses.zm, "removePotionEffect", "removeEffect");
        this.t(MappedClasses.zm, "isPotionActive", "hasEffect");
        this.t(MappedClasses.zm, "getActivePotionEffect", "getEffect");
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
    }

    protected void TU() {
        this.t(MappedClasses.Fm, "processPacket", "handle");
    }

    protected void B() {
        this.t(MappedClasses.Z, "addEntityImpl", "addEntity");
        this.t(MappedClasses.Z, "func_184148_a", "m_6263_");
        this.t(MappedClasses.Z, "getAllEntities", "entitiesForRendering");
        this.t(MappedClasses.Z, "func_239134_a_", "m_104637_");
        this.t(MappedClasses.Z, "playSound", "playLocalSound");
    }

    protected void Rb() {
        this.t(MappedClasses.Q, "getInputByCode", "getKey");
    }

    protected void q() {
        this.B(MappedClasses.Dc, "info", "camera");
        this.t(MappedClasses.Dc, "renderEntityStatic", "render");
    }

    protected void t() {
        this.B(MappedClasses.ze, "field_70232_b", "f_36813_");
        this.B(MappedClasses.ze, "field_70233_c", "f_36814_");
        this.B(MappedClasses.ze, "field_70230_d", "f_36815_");
    }

    protected void Rd() {
        this.B(MappedClasses.ZQ, "entityId", "id");
        this.B(MappedClasses.ZQ, "type", "action");
    }

    protected void T5() {
        this.t(MappedClasses.z_, "getID", "getId");
    }

    protected void R2() {
        this.B(MappedClasses.Fq, "field_177097_h", "f_115291_");
        this.B(MappedClasses.Fq, "entityModel", "model");
    }

    protected void Rm() {
        this.B(MappedClasses.F1, "doneLoadingTerrain", "started");
        this.B(MappedClasses.F1, "netManager", "connection");
        this.t(MappedClasses.F1, "sendPacket", "send");
        this.t(MappedClasses.F1, "getPlayerInfoMap", "getOnlinePlayers");
    }

    protected void C$src$V$14xsb7() {
        this.B(MappedClasses.qI, "x", "i");
        this.B(MappedClasses.qI, "y", "j");
        this.B(MappedClasses.qI, "z", "k");
        this.B(MappedClasses.qI, "w", "r");
    }

    protected void RJ() {
        this.t(MappedClasses.FS, "getEnchantmentLevel", "getItemEnchantmentLevel");
        this.t(MappedClasses.FS, "getEnchantmentModifierDamage", "getDamageProtection");
        this.t(MappedClasses.FS, "getDepthStriderModifier", "getDepthStrider");
        this.t(MappedClasses.FS, "getModifierForCreature", "getDamageBonus");
        this.t(MappedClasses.FS, "applyEnchantmentModifierArray", "runIterationOnInventory");
    }

    protected void Rv() {
        this.B(MappedClasses.Zk, "defaultState", "defaultBlockState");
        this.B(MappedClasses.Zk, "translationKey", "descriptionId");
        this.t(MappedClasses.Zk, "getStateById", "stateById");
        this.t(MappedClasses.Zk, "getItem", "getCloneItemStack");
        this.t(MappedClasses.Zk, "getStateId", "getId");
        this.t(MappedClasses.Zk, "onBlockActivated", "use");
    }

    protected void TA() {
        this.B(MappedClasses.ZI, "pos", "worldPosition");
    }

    protected void Rx() {
        this.t(MappedClasses.Fi, "toggleFullscreen", "updateFullscreen");
    }

    protected void g() {
        this.B(MappedClasses.qO, "jump", "jumping");
        this.B(MappedClasses.qO, "moveStrafe", "leftImpulse");
        this.B(MappedClasses.qO, "sneaking", "shiftKeyDown");
        this.B(MappedClasses.qO, "forwardKeyDown", "up");
        this.B(MappedClasses.qO, "backKeyDown", "down");
        this.B(MappedClasses.qO, "leftKeyDown", "left");
        this.B(MappedClasses.qO, "rightKeyDown", "right");
    }

    protected void To() {
        this.t(MappedClasses.zt, "getLocationSkin", "getSkinTextureLocation");
    }

    protected void RZ() {
        this.B(MappedClasses.qF, "pos", "blockPos");
        this.B(MappedClasses.qF, "face", "direction");
    }

    protected void J() {
        this.B(MappedClasses.Yu, "currentItem", "selected");
        this.B(MappedClasses.Yu, "field_70462_a", "items");
        this.B(MappedClasses.Yu, "armorInventory", "armor");
    }

    protected void M() {
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

    protected void RR() {
        this.B(MappedClasses.Fo, "listShaders", "passes");
        this.t(MappedClasses.Fo, "createBindFramebuffers", "resize");
    }

    protected void L() {
        this.B(MappedClasses.lX, "isDrawing", "building");
        this.B(MappedClasses.lX, "byteBuffer", "buffer");
    }

    protected void RQ() {
        this.B(MappedClasses.uB, "damageType", "msgId");
        this.t(MappedClasses.uB, "causePlayerDamage", "playerAttack");
        this.t(MappedClasses.uB, "getImmediateSource", "getDirectEntity");
    }

    protected void TR() {
        this.B(MappedClasses.Ye, "efficiency", "speed");
    }

    protected void W() {
        this.B(MappedClasses.Vi, "field_239496_d_", "f_118911_");
    }

    protected void Rp() {
        this.B(MappedClasses.Zj, "displayedTitle", "title");
        this.t(MappedClasses.Zj, "renderIngameGui", "render");
        this.t(MappedClasses.Zj, "func_238447_a_", "m_93036_");
    }

    protected void k() {
        this.B(MappedClasses.FR, "baseName", "name");
        this.t(MappedClasses.FR, "getPotionTypeForName", "byName");
    }

    protected void d() {
        this.t(MappedClasses.MOB_SPAWNER_TILE_ENTITY, "getSpawnerBaseLogic", "getSpawner");
        this.t(MappedClasses.MOB_SPAWNER_TILE_ENTITY, "func_145881_a", "m_59801_");
    }

    protected void Ro() {
        this.t(MappedClasses.uE, "getChunkProvider", "getChunkSource");
    }

    protected void Rc() {
        this.B(MappedClasses.YN, "field_194148_c", "f_92519_");
        this.B(MappedClasses.YN, "renderPartialTicks", "partialTick");
    }

    protected void N() {
        this.B(MappedClasses.YX, "entityID", "id");
        this.B(MappedClasses.YX, "motionX", "xa");
        this.B(MappedClasses.YX, "motionY", "ya");
        this.B(MappedClasses.YX, "motionZ", "za");
        this.B(MappedClasses.YX, "field_149417_a", "f_133176_");
        this.B(MappedClasses.YX, "field_149415_b", "f_133177_");
        this.B(MappedClasses.YX, "field_149416_c", "f_133178_");
        this.B(MappedClasses.YX, "field_149414_d", "f_133179_");
    }

    protected void V() {
        this.t(MappedClasses.VQ, "getEntityTexture", "getTextureLocation");
    }

    protected void Re() {
        this.B(MappedClasses.zX, "SEARCH", "TAB_SEARCH");
    }

    protected void RT() {
        this.t(MappedClasses.f, "intersectsWith", "intersects");
    }

    protected void Q() {
        this.t(MappedClasses.qg, "forEntity", "of");
    }

    protected void b() {
        this.t(MappedClasses.j, "getParameters", "writeToString");
    }

    protected void D() {
        this.B(MappedClasses.X, "windowId", "containerId");
    }

    protected void Y() {
        this.B(MappedClasses.ll, "depthBuffer", "depthBufferId");
        this.t(MappedClasses.ll, "bindFramebuffer", "bindWrite");
        this.t(MappedClasses.ll, "unbindFramebuffer", "unbindWrite");
        this.t(MappedClasses.ll, "bindFramebufferTexture", "bindRead");
        this.t(MappedClasses.ll, "unbindFramebufferTexture", "unbindRead");
        this.t(MappedClasses.ll, "deleteFramebuffer", "destroyBuffers");
    }

    protected void Rh() {
        this.t(MappedClasses.lp, "finish", "endBatch");
    }

    protected void s() {
        this.B(MappedClasses.DT, "hitResult", "location");
    }

    protected void A() {
        this.B(MappedClasses.lM, "caughtEntity", "hookedIn");
        this.B(MappedClasses.lM, "field_234597_c_", "f_37099_");
    }
}

