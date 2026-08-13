package gg.vape.mapping.runtime;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;

public class MemberNameRemapTableV35V36
extends MemberNameRemapTable {
    protected void QX() {
        this.B(MappedClasses.F1, "doneLoadingTerrain", "started");
        this.B(MappedClasses.F1, "netManager", "connection");
        this.t(MappedClasses.F1, "sendPacket", "send");
        this.t(MappedClasses.F1, "getPlayerInfoMap", "getOnlinePlayers");
    }

    protected void G() {
        this.B(MappedClasses.ZX, "buffer", "builder");
        this.t(MappedClasses.ZX, "draw", "end");
    }

    protected void QC() {
        this.t(MappedClasses.zs, "loadRenderers", "allChanged");
        this.t(MappedClasses.zs, "updateCameraAndRender", "renderLevel");
        this.t(MappedClasses.zs, "markBlockRangeForRenderUpdate", "setBlocksDirty");
        this.t(MappedClasses.zs, "updateChunks", "compileChunksUntil");
    }

    protected void QP() {
        this.B(MappedClasses.ITEM_FOOD, "value", "nutrition");
        this.B(MappedClasses.ITEM_FOOD, "saturation", "saturationModifier");
    }

    protected void x() {
        this.t(MappedClasses.Yh, "isSameTeam", "isAlliedTo");
    }

    protected void Qf() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.m, "func_238470_a_", "blit");
        }
    }

    protected void O() {
        this.t(MappedClasses.uV, "getEffectsFromStack", "getMobEffects");
    }

    protected void F() {
        this.B(MappedClasses.U, "capability", "state");
        this.B(MappedClasses.U, "currentState", "enabled");
    }

    protected void Q9() {
        this.t(MappedClasses.zt, "getLocationSkin", "getSkinTextureLocation");
    }

    protected void QB() {
        this.B(MappedClasses.qM, "fuse", "life");
    }

    protected void ug() {
    }

    protected void Qa() {
        this.t(MappedClasses.MOB_SPAWNER_LOGIC, "getCachedEntity", "getOrCreateDisplayEntity");
    }

    protected void QK() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.Vv, "func_196954_c", "getShape");
        }
    }

    protected void Qv() {
        this.B(MappedClasses.Zk, "defaultState", "defaultBlockState");
        this.B(MappedClasses.Zk, "translationKey", "descriptionId");
        this.t(MappedClasses.Zk, "getStateById", "stateById");
        this.t(MappedClasses.Zk, "getItem", "getCloneItemStack");
        this.t(MappedClasses.Zk, "getStateId", "getId");
    }

    protected void uV() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.u6, "func_237500_a_", "formatNameForTeam");
        }
    }

    protected void ud() {
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
        this.B(MappedClasses.zm, "itemInUseCount", "field_184628_bn");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.zm, "field_191988_bg", "zza");
        }
        this.t(MappedClasses.zm, "getHeldItemMainhand", "getMainHandItem");
        this.t(MappedClasses.zm, "swingArm", "swing");
        this.t(MappedClasses.zm, "removePotionEffect", "removeEffect");
        this.t(MappedClasses.zm, "isPotionActive", "hasEffect");
        this.t(MappedClasses.zm, "getActivePotionEffect", "getEffect");
        this.t(MappedClasses.zm, "getAttributeManager", "getAttributes");
        this.t(MappedClasses.zm, "livingTick", "aiStep");
        this.t(MappedClasses.zm, "canEntityBeSeen", "canSee");
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
        this.t(MappedClasses.zm, "addPotionEffect", "addEffect");
        this.t(MappedClasses.zm, "isElytraFlying", "isFallFlying");
    }

    protected void S() {
        this.t(MappedClasses.SET_VISIBILITY, "setAllVisible", "setAll");
    }

    protected void QW() {
        this.B(MappedClasses.qD, "moving", "hasPos");
    }

    protected void q() {
        this.B(MappedClasses.YX, "entityID", "id");
        this.B(MappedClasses.YX, "motionX", "xa");
        this.B(MappedClasses.YX, "motionY", "ya");
        this.B(MappedClasses.YX, "motionZ", "za");
    }

    protected void us() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.F6, "getSortedScores", "getPlayerScores");
        } else {
            this.t(MappedClasses.F6, "getSortedScores", "func_96534_i");
        }
    }

    protected void p() {
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

    protected void QV() {
        this.t(MappedClasses.uk, "calcSideHit", "getDirection");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.uk, "func_72317_d", "move");
            this.t(MappedClasses.uk, "func_216365_b", "clip");
            this.t(MappedClasses.uk, "func_72314_b", "inflate");
            this.t(MappedClasses.uk, "func_186662_g", "inflate");
            this.t(MappedClasses.uk, "func_72318_a", "contains");
            this.t(MappedClasses.uk, "func_216361_a", "expandTowards");
        }
    }

    protected void Ql() {
        this.t(MappedClasses.YS, "drawEntityOnScreen", "renderEntityInInventory");
    }

    protected void Qi() {
        this.B(MappedClasses.ll, "depthBuffer", "depthBufferId");
        this.t(MappedClasses.ll, "bindFramebuffer", "bindWrite");
        this.t(MappedClasses.ll, "unbindFramebuffer", "unbindWrite");
        this.t(MappedClasses.ll, "bindFramebufferTexture", "bindRead");
        this.t(MappedClasses.ll, "unbindFramebufferTexture", "unbindRead");
        this.t(MappedClasses.ll, "deleteFramebuffer", "destroyBuffers");
    }

    protected void m() {
        this.t(MappedClasses.Q, "getInputByCode", "getKey");
    }

    protected void Qy() {
        this.t(MappedClasses.DQ, "getLast", "last");
        this.t(MappedClasses.DQ, "push", "pushPose");
        this.t(MappedClasses.DQ, "pop", "popPose");
        this.t(MappedClasses.DQ, "rotate", "mulPose");
    }

    protected void I() {
        this.B(MappedClasses.lM, "caughtEntity", "hookedIn");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.lM, "field_234597_c_", "biting");
        }
    }

    protected void u() {
        this.t(MappedClasses.YL, "getCollisionShapes", "getBlockCollisions");
    }

    protected void M() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.FV, "func_219895_b", "popPush");
        }
    }

    protected void uL() {
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

    protected void QT() {
        this.t(MappedClasses.Dt, "bindTexture", "_bind");
    }

    protected void Qh() {
        this.t(MappedClasses.Yr, "getUnformattedComponentText", "getContents");
    }

    protected void ur() {
        this.B(MappedClasses.FO, "outboundPacketsQueue", "queue");
        this.t(MappedClasses.FO, "getNetHandler", "getPacketListener");
        this.t(MappedClasses.FO, "flushOutboundQueue", "flushQueue");
        this.t(MappedClasses.FO, "dispatchPacket", "sendPacket");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.FO, "sendPacket", "send");
        }
    }

    protected void uq() {
        this.t(MappedClasses.VK, "getEnchantmentTagList", "getEnchantmentTags");
        this.t(MappedClasses.VK, "getTranslationKey", "getDescriptionId");
        this.t(MappedClasses.VK, "getDisplayName", "getHoverName");
        this.t(MappedClasses.VK, "getDamage", "getDamageValue");
        this.t(MappedClasses.VK, "setDamage", "setDamageValue");
        this.t(MappedClasses.VK, "addEnchantment", "enchant");
        this.t(MappedClasses.VK, "isItemEqual", "sameItem");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.VK, "func_82840_a", "getTooltipLines");
        }
    }

    protected void l() {
        this.t(MappedClasses.D3, "get", "byId");
        this.t(MappedClasses.D3, "removeAttributesModifiersFromEntity", "removeAttributeModifiers");
    }

    protected void Qe() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.lF, "field_175252_a", "PLAYER_ORDERING");
        }
    }

    protected void C$src$V$18vebku() {
        this.t(MappedClasses.FS, "getEnchantmentLevel", "getItemEnchantmentLevel");
        this.t(MappedClasses.FS, "getEnchantmentModifierDamage", "getDamageProtection");
        this.t(MappedClasses.FS, "getDepthStriderModifier", "getDepthStrider");
        this.t(MappedClasses.FS, "getModifierForCreature", "getDamageBonus");
        this.t(MappedClasses.FS, "applyEnchantmentModifierArray", "runIterationOnInventory");
    }

    protected void Qx() {
        this.B(MappedClasses.u3, "potion", "effect");
    }

    protected void QI() {
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
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.z5, "field_191988_bg", "zza");
        }
        this.t(MappedClasses.z5, "sendChatMessage", "chat");
        this.t(MappedClasses.z5, "onUpdateWalkingPlayer", "sendPosition");
    }

    protected void u9() {
        this.B(MappedClasses.Vk, "currentSectionName", "path");
    }

    protected void Qd() {
        this.B(MappedClasses.YU, "loadedTileEntityList", "blockEntityList");
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

    protected void Qo() {
        this.B(MappedClasses.zX, "SEARCH", "TAB_SEARCH");
    }

    protected void uF() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.e, "field_149615_a", "slot");
        }
    }

    protected void X() {
        this.t(MappedClasses.z_, "getID", "getId");
    }

    protected void uk() {
        this.B(MappedClasses.za, "canCollide", "hasCollision");
        this.B(MappedClasses.za, "slipperiness", "friction");
        this.t(MappedClasses.za, "onBlockActivated", "use");
    }

    protected void Q() {
        this.t(MappedClasses.lp, "finish", "endBatch");
    }

    protected void V() {
        this.B(MappedClasses.lT, "keyBindAttack", "keyAttack");
        this.B(MappedClasses.lT, "keyBindUseItem", "keyUse");
        this.B(MappedClasses.lT, "keyBindForward", "keyUp");
        this.B(MappedClasses.lT, "keyBindLeft", "keyLeft");
        this.B(MappedClasses.lT, "keyBindBack", "keyDown");
        this.B(MappedClasses.lT, "keyBindRight", "keyRight");
        this.B(MappedClasses.lT, "keyBindJump", "keyJump");
        this.B(MappedClasses.lT, "keyBindSprint", "keySprint");
        this.B(MappedClasses.lT, "keyBindSneak", "keyShift");
        this.B(MappedClasses.lT, "keyBindFullscreen", "keyFullscreen");
        this.B(MappedClasses.lT, "keyBindInventory", "keyInventory");
        this.B(MappedClasses.lT, "keyBindDrop", "keyDrop");
        this.B(MappedClasses.lT, "hideGUI", "hideGui");
        this.B(MappedClasses.lT, "renderDistanceChunks", "renderDistance");
        this.B(MappedClasses.lT, "pointOfView", "cameraType");
        this.B(MappedClasses.lT, "mouseSensitivity", "sensitivity");
        this.B(MappedClasses.lT, "viewBobbing", "bobView");
        this.t(MappedClasses.lT, "setKeyBindingCode", "setKey");
    }

    protected void QH() {
        this.t(MappedClasses.Y, "func_237498_g_", "createFormattedDisplayName");
    }

    protected void a() {
        this.t(MappedClasses.lR, "getDisplayName", "getFullname");
    }

    protected void Qg() {
        this.B(MappedClasses.Ye, "efficiency", "speed");
    }

    protected void Q2() {
        this.B(MappedClasses.YQ, "slotNumber", "index");
        this.t(MappedClasses.YQ, "getStack", "getItem");
    }

    protected void u4() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.qF, "func_216352_a", "miss");
        }
    }

    protected void N() {
        this.B(MappedClasses.qI, "x", "i");
        this.B(MappedClasses.qI, "y", "j");
        this.B(MappedClasses.qI, "z", "k");
        this.B(MappedClasses.qI, "w", "r");
    }

    protected void b() {
        this.B(MappedClasses.zZ, "lowerChestInventory", "container");
    }

    protected void J() {
        this.B(MappedClasses.lt, "pitch", "xRot");
        this.B(MappedClasses.lt, "yaw", "yRot");
        this.B(MappedClasses.lt, "pos", "position");
        this.B(MappedClasses.lt, "renderViewEntity", "entity");
        this.B(MappedClasses.lt, "world", "level");
        this.t(MappedClasses.lt, "update", "setup");
    }

    protected void uH() {
        this.t(MappedClasses.uE, "getChunkProvider", "getChunkSource");
    }

    protected void uX() {
        this.t(MappedClasses.Zc, "getGameProfile", "getProfile");
        this.t(MappedClasses.Zc, "getResponseTime", "getLatency");
    }

    protected void Qk() {
        this.t(MappedClasses.K, "bindTexture", "_bindTexture");
        this.t(MappedClasses.K, "enableLighting", "_enableLighting");
        this.t(MappedClasses.K, "disableLighting", "_disableLighting");
        this.t(MappedClasses.K, "depthMask", "_depthMask");
        this.t(MappedClasses.K, "enableBlend", "_enableBlend");
        this.t(MappedClasses.K, "disableBlend", "_disableBlend");
        this.t(MappedClasses.K, "enableCull", "_enableCull");
        this.t(MappedClasses.K, "disableCull", "_disableCull");
        this.t(MappedClasses.K, "alphaFunc", "_alphaFunc");
        this.t(MappedClasses.K, "loadIdentity", "_loadIdentity");
        this.t(MappedClasses.K, "color4f", "_color4f");
        this.t(MappedClasses.K, "enableAlphaTest", "_enableAlphaTest");
        this.t(MappedClasses.K, "enableDepthTest", "_enableDepthTest");
        this.t(MappedClasses.K, "disableDepthTest", "_disableDepthTest");
        this.t(MappedClasses.K, "disableAlphaTest", "_disableAlphaTest");
        this.t(MappedClasses.K, "enableTexture", "_enableTexture");
        this.t(MappedClasses.K, "disableTexture", "_disableTexture");
        this.t(MappedClasses.K, "rotatef", "_rotatef");
        this.t(MappedClasses.K, "activeTexture", "_activeTexture");
        this.t(MappedClasses.K, "blendFuncSeparate", "_blendFuncSeparate");
        this.t(MappedClasses.K, "texImage2D", "_texImage2D");
        this.t(MappedClasses.K, "genTexture", "_genTexture");
    }

    protected void c() {
        this.B(MappedClasses.G, "matrix", "pose");
    }

    protected void Y() {
        this.t(MappedClasses.ql, "disableStandardItemLighting", "turnOff");
        this.t(MappedClasses.ql, "enableStandardItemLighting", "turnBackOn");
    }

    protected void y() {
        this.B(MappedClasses.ld, "isHittingBlock", "isDestroying");
        this.B(MappedClasses.ld, "curBlockDamageMP", "destroyProgress");
        this.t(MappedClasses.ld, "processRightClick", "useItem");
        this.t(MappedClasses.ld, "getBlockReachDistance", "getPickRange");
        this.t(MappedClasses.ld, "extendedReach", "hasFarPickRange");
        this.t(MappedClasses.ld, "attackEntity", "attack");
        this.t(MappedClasses.ld, "syncCurrentPlayItem", "ensureHasSentCarriedItem");
        this.t(MappedClasses.ld, "onStoppedUsingItem", "releaseUsingItem");
        this.t(MappedClasses.ld, "windowClick", "handleInventoryMouseClick");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.ld, "func_217292_a", "useItemOn");
        }
    }

    protected void r() {
    }

    protected void Qu() {
        this.B(MappedClasses.Dk, "xCoord", "x");
        this.B(MappedClasses.Dk, "yCoord", "y");
        this.B(MappedClasses.Dk, "zCoord", "z");
    }

    protected void uB() {
        this.t(MappedClasses.VIS_GRAPH, "computeVisibility", "resolve");
        this.t(MappedClasses.VIS_GRAPH, "setOpaqueCube", "setOpaque");
    }

    protected void uy() {
        this.B(MappedClasses.ud, "bipedBodyWear", "jacket");
        this.B(MappedClasses.ud, "bipedRightArmwear", "rightSleeve");
        this.B(MappedClasses.ud, "bipedLeftArmwear", "leftSleeve");
        this.B(MappedClasses.ud, "bipedRightLegwear", "rightPants");
        this.B(MappedClasses.ud, "bipedLeftLegwear", "leftPants");
    }

    protected void Qb() {
        this.B(MappedClasses.uR, "serverIP", "ip");
    }

    protected void uv() {
        this.B(MappedClasses.uB, "damageType", "msgId");
        this.t(MappedClasses.uB, "causePlayerDamage", "playerAttack");
        this.t(MappedClasses.uB, "getImmediateSource", "getDirectEntity");
    }

    protected void u5() {
        this.t(MappedClasses.Fm, "processPacket", "handle");
    }

    protected void Qt() {
        this.B(MappedClasses.qS, "username", "name");
        this.B(MappedClasses.qS, "playerID", "uuid");
    }

    protected void QZ() {
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

    protected void Q1() {
        this.t(MappedClasses.Dn, "pos", "vertex");
    }

    protected void R() {
    }

    protected void QA() {
        this.B(MappedClasses.FX, "properties", "values");
    }

    protected void QF() {
        this.B(MappedClasses.qz, "posX", "xa");
        this.B(MappedClasses.qz, "posY", "ya");
        this.B(MappedClasses.qz, "posZ", "za");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.qz, "func_149066_f", "getyRot");
            this.t(MappedClasses.qz, "func_149063_g", "getxRot");
            this.t(MappedClasses.qz, "func_149060_h", "hasRotation");
        }
    }

    protected void QY() {
        this.B(MappedClasses.lU, "logicOpcode", "eventId");
    }

    protected void Q_() {
        this.t(MappedClasses.ZK, "getImpl", "immediate");
    }

    protected void QL() {
        this.t(MappedClasses.f, "intersectsWith", "intersects");
    }

    protected void ux() {
        this.B(MappedClasses.zp, "keyCode", "value");
    }

    protected void k() {
        this.B(MappedClasses.q0, "index", "data3d");
        this.B(MappedClasses.q0, "opposite", "oppositeIndex");
        this.t(MappedClasses.q0, "getDirectionVec", "getNormal");
        this.t(MappedClasses.q0, "getName2", "getName");
        this.t(MappedClasses.q0, "getHorizontalIndex", "get2DDataValue");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.q0, "func_210769_a", "getNearest");
        }
    }

    protected void QO() {
        this.t(MappedClasses.F7, "isBlockLoaded", "hasChunkAt");
    }

    protected void u_() {
        this.B(MappedClasses.Zu, "chatComponent", "message");
        this.t(MappedClasses.Zu, "getChatComponent", "getMessage");
    }

    protected void u3() {
        this.B(MappedClasses.Yk, "blend", "mode");
    }

    protected void d() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.Vi, "field_239496_d_", "defaultRightToLeft");
        }
    }

    protected void Q5() {
        this.t(MappedClasses.Fi, "toggleFullscreen", "updateFullscreen");
    }


    protected void QD() {
        this.B(MappedClasses.X, "windowId", "containerId");
        this.B(MappedClasses.X, "inventorySlots", "slots");
    }

    protected void uP() {
        this.B(MappedClasses.qO, "jump", "jumping");
        this.B(MappedClasses.qO, "moveStrafe", "leftImpulse");
        this.B(MappedClasses.qO, "sneaking", "shiftKeyDown");
        this.B(MappedClasses.qO, "forwardKeyDown", "up");
        this.B(MappedClasses.qO, "backKeyDown", "down");
        this.B(MappedClasses.qO, "leftKeyDown", "left");
        this.B(MappedClasses.qO, "rightKeyDown", "right");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.qO, "field_192832_b", "forwardImpulse");
        }
    }

    protected void v$src$V$19nftu9() {
        this.B(MappedClasses.Yd, "rotateAngleX", "xRot");
        this.B(MappedClasses.Yd, "rotateAngleY", "yRot");
        this.B(MappedClasses.Yd, "rotateAngleZ", "zRot");
        this.B(MappedClasses.Yd, "rotationPointX", "x");
        this.B(MappedClasses.Yd, "rotationPointY", "y");
        this.B(MappedClasses.Yd, "rotationPointZ", "z");
        this.B(MappedClasses.Yd, "showModel", "visible");
        this.B(MappedClasses.Yd, "textureOffsetX", "xTexOffs");
        this.B(MappedClasses.Yd, "textureOffsetY", "yTexOffs");
    }

    protected void o() {
        this.B(MappedClasses.Dc, "info", "camera");
        this.t(MappedClasses.Dc, "renderEntityStatic", "render");
    }

    protected void B() {
        this.B(MappedClasses.Y6, "name", "location");
    }

    protected void D() {
        this.B(MappedClasses.DR, "pressed", "isDown");
        this.B(MappedClasses.DR, "pressTime", "clickCount");
        this.B(MappedClasses.DR, "keyCode", "key");
        this.t(MappedClasses.DR, "isPressed", "consumeClick");
        this.t(MappedClasses.DR, "unPressAllKeys", "releaseAll");
        this.t(MappedClasses.DR, "setPressed", "setDown");
        this.t(MappedClasses.DR, "unpressKey", "release");
        this.t(MappedClasses.DR, "setKeyBindState", "set");
        this.t(MappedClasses.DR, "onTick", "click");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.DR, "func_151470_d", "isDown");
        }
    }

    protected void e() {
        this.t(MappedClasses.VJ, "getEntitiesWithinAABBForEntity", Vape.INSTANCE.isVanillaMinecraftPresent() ? "getEntities" : "func_177414_a");
    }

    protected void QQ() {
        this.B(MappedClasses.FY, "slotIndex", "filterFlag");
    }

    protected void Q7() {
        this.B(MappedClasses.ux, "children", "decomposedParts");
    }

    protected void Qr() {
        this.t(MappedClasses.Z, "addEntityImpl", "addEntity");
        this.t(MappedClasses.Z, "removeEntity", Vape.INSTANCE.isVanillaMinecraftPresent() ? "onEntityRemovedVanilla" : "onEntityRemoved");
        this.t(MappedClasses.Z, "getAllEntities", "entitiesForRendering");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.Z, "func_239134_a_", "setGameTime");
        }
        this.t(MappedClasses.Z, "playSound", "playLocalSound");
    }

    protected void E() {
        this.t(MappedClasses.lg, "chunkExists", "hasChunk");
    }

    protected void u6() {
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
        this.t(MappedClasses.zc, "getDistance", "distanceTo");
        this.t(MappedClasses.zc, "rotateTowards", "turn");
        this.t(MappedClasses.zc, "getPosY", "getY");
        this.t(MappedClasses.zc, "copyDataFromOld", "restoreFrom");
        this.t(MappedClasses.zc, "isBurning", "isOnFire");
        this.t(MappedClasses.zc, "isSneaking", "isCrouching");
        this.t(MappedClasses.zc, "getCollisionBorderSize", "getPickRadius");
        this.t(MappedClasses.zc, "isOffsetPositionInLiquid", Vape.INSTANCE.isVanillaMinecraftPresent() ? "isFree" : "func_70038_c");
        this.t(MappedClasses.zc, "setVelocity", "lerpMotion");
        this.t(MappedClasses.zc, "isWet", "isInWaterOrRain");
        this.t(MappedClasses.zc, "setFire", "setSecondsOnFire");
        this.t(MappedClasses.zc, "setFlag", "setSharedFlag");
        this.t(MappedClasses.zc, "setPositionAndRotation", Vape.INSTANCE.isVanillaMinecraftPresent() ? "absMoveTo" : "func_70080_a");
        this.t(MappedClasses.zc, "setPosition", "setPos");
        this.t(MappedClasses.zc, "setSneaking", "setShiftKeyDown");
        this.t(MappedClasses.zc, "getLook", "getViewVector");
        this.t(MappedClasses.zc, "doBlockCollisions", "checkInsideBlocks");
        this.t(MappedClasses.zc, "getLowestRidingEntity", "getRootVehicle");
        this.t(MappedClasses.zc, "canTriggerWalking", "isMovementNoisy");
        this.B(MappedClasses.zc, "preventEntitySpawning", "blocksBuilding");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.zc, "func_184231_a", "checkFallDamage");
        }
        this.t(MappedClasses.zc, "hasNoGravity", "isNoGravity");
        this.t(MappedClasses.zc, "canPassengerSteer", "isControlledByLocalInstance");
    }

    protected void Qn() {
        this.t(MappedClasses.FJ, "applyModifier", "addModifier");
    }

    protected void L() {
        this.B(MappedClasses.FR, "baseName", "name");
        this.t(MappedClasses.FR, "getPotionTypeForName", "byName");
    }

    protected void uw() {
        this.B(MappedClasses.uK, "category", "biomeCategory");
    }

    protected void t() {
        this.B(MappedClasses.L, "textureLocation", "location");
    }

    protected void Qj() {
        this.t(MappedClasses.VQ, "getEntityTexture", "getTextureLocation");
    }

    protected void h() {
        this.B(MappedClasses.Fo, "listShaders", "passes");
        this.t(MappedClasses.Fo, "createBindFramebuffers", "resize");
    }

    protected void g() {
        this.B(MappedClasses.Ys, "xVelocity", "accumulatedDX");
        this.B(MappedClasses.Ys, "yVelocity", "accumulatedDY");
        this.B(MappedClasses.Ys, "mouseX", "xpos");
        this.B(MappedClasses.Ys, "mouseY", "ypos");
        this.t(MappedClasses.Ys, "ungrabMouse", "releaseMouse");
    }

    protected void W() {
        this.t(MappedClasses.DX, "getPlayerName", "getOwner");
    }

    protected void K() {
        this.B(MappedClasses.DD, "blockPosition", "pos");
    }

    protected void n() {
        this.t(MappedClasses.zH, "updateLightmap", "updateLightTexture");
        this.t(MappedClasses.zH, "disableLightmap", "turnOffLightLayer");
        this.t(MappedClasses.zH, "enableLightmap", "turnOnLightLayer");
    }

    protected void f() {
        this.t(MappedClasses.MOB_SPAWNER_TILE_ENTITY, "getSpawnerBaseLogic", "getSpawner");
    }

    protected void Qz() {
        this.B(MappedClasses.ZQ, "entityId", "id");
        this.B(MappedClasses.ZQ, "type", "action");
    }

    protected void u0() {
        this.B(MappedClasses.lX, "isDrawing", "building");
        this.B(MappedClasses.lX, "byteBuffer", "buffer");
    }

    protected void uz() {
        this.B(MappedClasses.u0, "lidAngle", "openness");
        this.B(MappedClasses.u0, "numPlayersUsing", "openCount");
    }

    protected void Qc() {
        this.t(MappedClasses.T, "getShaderManager", "getEffect");
    }

    protected void Qw() {
        this.B(MappedClasses.ZI, "pos", "worldPosition");
    }

    protected void QJ() {
        this.B(MappedClasses.Db, "atlasTexture", "atlas");
    }

    protected void Q6() {
        this.t(MappedClasses.j, "getParameters", "writeToString");
    }

    protected void uU() {
        this.B(MappedClasses.uu, "attachedEntity", "entity");
        this.B(MappedClasses.uu, "particleTypes", "particleType");
    }

    protected void A() {
        this.B(MappedClasses.Zn, "textureName", "binding");
    }

    protected void u2() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.qP, "func_72441_c", "add");
            this.t(MappedClasses.qP, "func_72436_e", "distanceToSqr");
        }
    }

    protected void P() {
        this.B(MappedClasses.DZ, "lidAngle", "openness");
        this.B(MappedClasses.DZ, "numPlayersUsing", "openCount");
    }

    protected void QN() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.qr, "func_226595_a_", "multiply");
        }
        this.t(MappedClasses.qr, "write", "store");
    }

    protected void QG() {
        this.t(MappedClasses.la, "getBoundingBox", "bounds");
    }

    protected void U() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.Vm, "field_239501_a_", "language");
        }
        this.t(MappedClasses.Vm, "format", "get");
    }

    protected void QE() {
        this.B(MappedClasses.ZN, "TALL_PLANTS", "REPLACEABLE_PLANT");
        this.t(MappedClasses.ZN, "blocksMovement", "blocksMotion");
    }

    protected void Qs() {
        this.B(MappedClasses.zw, "yaw", "yRot");
        this.B(MappedClasses.zw, "pitch", "xRot");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.zw, "func_179834_f", "getRelativeArguments");
        }
    }

    protected void Q8() {
        this.t(MappedClasses.lh, "getAttackDamage", "getAttackDamageBonus");
    }

    protected void u7() {
        this.B(MappedClasses.Yv, "entityIDs", "entityIds");
    }

    protected void un() {
        this.B(MappedClasses.Zj, "displayedTitle", "title");
        this.t(MappedClasses.Zj, "renderIngameGui", "render");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.Zj, "func_238447_a_", "displayScoreboardSidebar");
        }
    }

    protected void Q3() {
        this.B(MappedClasses.Yg, "tagMap", "tags");
    }

    protected void uI() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.zJ, "func_217299_a", "clip");
        }
    }

    protected void ue() {
        this.t(MappedClasses.lf, "down", "below");
        this.t(MappedClasses.lf, "up", "above");
        if (Vape.INSTANCE.isVanillaMinecraftPresent() || !Vape.INSTANCE.isForgeAbsent()) {
            this.t(MappedClasses.lf, "offset", "relative");
        } else {
            this.t(MappedClasses.lf, "offset", "a");
        }
    }

    protected void QM() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.qe, "field_149152_f", "knockbackX");
            this.B(MappedClasses.qe, "field_149153_g", "knockbackY");
            this.B(MappedClasses.qe, "field_149159_h", "knockbackZ");
        }
    }

    protected void Qq() {
        this.B(MappedClasses.Yu, "currentItem", "selected");
        this.B(MappedClasses.Yu, "field_70462_a", "items");
        this.B(MappedClasses.Yu, "armorInventory", "armor");
        this.t(MappedClasses.Yu, "getItemStack", "getCarried");
    }

    protected void uA() {
        this.B(MappedClasses.Ft, "INVENTORY_BACKGROUND", "INVENTORY_LOCATION");
        this.B(MappedClasses.Ft, "guiLeft", "leftPos");
        this.B(MappedClasses.Ft, "guiTop", "topPos");
        this.B(MappedClasses.Ft, "xSize", "imageWidth");
        this.B(MappedClasses.Ft, "ySize", "imageHeight");
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.Ft, "field_147002_h", "menu");
        }
        this.B(MappedClasses.Ft, "container", "menu");
        this.t(MappedClasses.Ft, "handleMouseClick", "slotClicked");
        this.t(MappedClasses.Ft, "getSelectedSlot", "findSlot");
    }

    protected void QS() {
        this.b(MappedClasses.u4, "getSprite", "func_215288_a", Vape.INSTANCE.isForgeAbsent());
    }

    protected void s() {
        this.B(MappedClasses.qU, "damageReduceAmount", "defense");
    }

    protected void Z() {
        this.B(MappedClasses.VG, "slot", "target");
    }

    protected void QU() {
        this.B(MappedClasses.F5, "attackDamage", "damage");
    }

    protected void Q4() {
        this.B(MappedClasses.zV, "bipedHead", "head");
        this.B(MappedClasses.zV, "bipedHeadwear", "hat");
        this.B(MappedClasses.zV, "bipedRightArm", "rightArm");
        this.B(MappedClasses.zV, "bipedLeftArm", "leftArm");
        this.B(MappedClasses.zV, "bipedRightLeg", "rightLeg");
        this.B(MappedClasses.zV, "bipedLeftLeg", "leftLeg");
    }

    protected void Qm() {
        this.B(MappedClasses.DT, "hitResult", "location");
    }

    protected void w() {
        this.B(MappedClasses.qF, "pos", "blockPos");
        this.B(MappedClasses.qF, "face", "direction");
    }

    protected void j() {
        this.B(MappedClasses.lb, "BLOCK_TO_ITEM", "BY_BLOCK");
        this.t(MappedClasses.lb, "getIdFromItem", "getId");
        this.t(MappedClasses.lb, "getItemById", "byId");
        this.t(MappedClasses.lb, "fillItemGroup", "fillItemCategory");
        this.t(MappedClasses.lb, "getName", "getDescription");
        this.t(MappedClasses.lb, "getDisplayName", "getName");
    }

    protected void z() {
        this.B(MappedClasses.z8, "zLevel", "blitOffset");
        this.t(MappedClasses.z8, "renderItemIntoGUI", "renderGuiItem");
        this.t(MappedClasses.z8, "renderItemOverlays", "renderGuiItemDecorations");
    }

    protected void u1() {
        this.t(MappedClasses.l0, "getSizeInventory", "getContainerSize");
        this.t(MappedClasses.l0, "getStackInSlot", "getItem");
    }

    protected void H() {
        this.B(MappedClasses.Fq, "entityModel", "model");
    }

    protected void Q0() {
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

    protected void ut() {
        this.t(MappedClasses.qg, "forEntity", "of");
    }

    protected void Qp() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.B(MappedClasses.YN, "field_194148_c", "tickDelta");
        }
        this.B(MappedClasses.YN, "renderPartialTicks", "partialTick");
    }

    protected void QR() {
        this.t(MappedClasses.YE, "getBedDirection", "getBedOrientation");
    }

    protected void i() {
        if (Vape.INSTANCE.isVanillaMinecraftPresent()) {
            this.t(MappedClasses.uQ, "func_243246_a", "drawShadow");
            this.t(MappedClasses.uQ, "func_243248_b", "draw");
            this.t(MappedClasses.uQ, "func_238405_a_", "drawShadow");
            this.t(MappedClasses.uQ, "func_238421_b_", "draw");
            this.t(MappedClasses.uQ, "func_243247_a", "drawInBatch");
            this.t(MappedClasses.uQ, "func_78256_a", "width");
        }
    }

    protected void forge116SrgCorrections() {
        this.B(MappedClasses.zc, "wasTouchingWater", "field_70171_ac");
        this.B(MappedClasses.Yl, "wasUnderwater", "field_204230_bP");
        this.B(MappedClasses.z5, "sprintTime", "field_71157_e");
        this.B(MappedClasses.z5, "sprintTriggerTime", "field_71156_d");
        this.B(MappedClasses.z5, "yBob", "field_71154_f");
        this.B(MappedClasses.z5, "xBob", "field_71155_g");
        this.B(MappedClasses.z5, "yBobO", "field_71163_h");
        this.B(MappedClasses.z5, "xBobO", "field_71164_i");
        this.B(MappedClasses.zm, "jumping", "field_70703_bu");
        this.B(MappedClasses.Z, "clientLevelData", "field_239130_d_");
        this.B(MappedClasses.YQ, "container", "field_75224_c");
        this.B(MappedClasses.YQ, "index", "field_75225_a");
        this.B(MappedClasses.Fj, "hardness", "field_235705_i_");
        this.B(MappedClasses.qD, "yRot", "field_149476_e");
        this.B(MappedClasses.qD, "xRot", "field_149473_f");
        this.B(MappedClasses.YN, "msPerTick", "field_194149_e");
        this.B(MappedClasses.Fa, "location", "field_179713_c");
        this.B(MappedClasses.s, "id", "field_149458_a");
        this.B(MappedClasses.s, "x", "field_149456_b");
        this.B(MappedClasses.s, "y", "field_149457_c");
        this.B(MappedClasses.s, "z", "field_149454_d");
        this.B(MappedClasses.s, "yRot", "field_149455_e");
        this.B(MappedClasses.s, "xRot", "field_149453_f");
        this.B(MappedClasses.uW, "yRot", "field_148951_f");
        this.B(MappedClasses.uW, "xRot", "field_148952_g");
        this.B(MappedClasses.YB, "blockHit", "field_218795_a");
        this.B(MappedClasses.D7, "yHeadRot", "field_149383_b");
        this.B(MappedClasses.Zc, "tabListDisplayName", "field_178872_h");
        this.B(MappedClasses.Db, "u0", "field_110979_l");
        this.B(MappedClasses.Db, "u1", "field_110980_m");
        this.B(MappedClasses.Db, "v0", "field_110977_n");
        this.B(MappedClasses.Db, "v1", "field_110978_o");

        this.t(MappedClasses.FW, "renderLevel", "func_228378_a_");
        this.t(MappedClasses.z5, "aiStep", "func_70636_d");
        this.t(MappedClasses.lF, "getNameForDisplay", "func_200262_a");
        this.t(MappedClasses.zY, "use", "func_77659_a");
        this.t(MappedClasses.lb, "getPlayerPOVHitResult", "func_219968_a");
        this.t(MappedClasses.Z, "getEntity", "func_73045_a");
        this.t(MappedClasses.zc, "isPickable", "func_70067_L");
    }
}

