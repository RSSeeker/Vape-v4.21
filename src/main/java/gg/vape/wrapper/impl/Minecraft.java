package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.account.MinecraftSessionWrapper;
import gg.vape.mapping.mappings.MMinecraft;
import gg.vape.wrapper.Wrapper;

import java.util.Map;

public class Minecraft
extends Wrapper {
    private static Object L;
    private static boolean s;

    public static void J(boolean bl) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().D(bl);
            return;
        }
        MMinecraft.t(Minecraft.vapeInstance.getMappings().U, Minecraft.i(), bl);
    }

    public static NetworkManager x$src$Lgg_vape_wrapper_impl_NetworkManager_$1sglv7v() {
        return new NetworkManager(MMinecraft.w(Vape.INSTANCE.getMappings().U, Minecraft.i()));
    }

    public static boolean V() {
        return MMinecraft.I(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static int w() {
        return MMinecraft.y(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static void b() {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().Q();
            return;
        }
        MMinecraft.R(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    static {
        if (!Minecraft.g()) {
            Minecraft.e(true);
        }
    }

    public static void r(int n) {
        MMinecraft.x(Minecraft.vapeInstance.getMappings().U, Minecraft.i(), n);
    }

    public static Map P() {
        return Minecraft.vapeInstance.getMappings().U.h$src$Ljava_util_Map_$8i5rvc(Minecraft.i());
    }

    public static RenderManager D() {
        return ForgeVersion.MC_1_7_10.L() ? RenderManager.getInstance() : new RenderManager(Minecraft.vapeInstance.getMappings().U.w(Minecraft.i()));
    }

    public static GuiSpriteManager T() {
        return new GuiSpriteManager(MMinecraft.p(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static boolean l$src$Z$b9uwii() {
        boolean bl = Minecraft.g();
        return false;
    }

    public static int h() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().R();
        }
        return Minecraft.vapeInstance.getMappings().U.R(Minecraft.i());
    }

    public static void O(boolean bl) {
        MMinecraft.u(Minecraft.vapeInstance.getMappings().U, Minecraft.i(), bl);
    }

    public static TextureManagerHandle getMainRenderTarget() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        return new TextureManagerHandle(Minecraft.vapeInstance.getMappings().U.V(Minecraft.i()));
    }

    public static boolean a() {
        return Minecraft.vapeInstance.getMappings().U.f(Minecraft.i());
    }

    public static EffectRenderer z() {
        return new EffectRenderer(MMinecraft.v(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static EntityLivingBase F() {
        return new EntityLivingBase(Minecraft.vapeInstance.getMappings().U.h(Minecraft.i()));
    }

    public static ModelManager x() {
        return new ModelManager(MMinecraft.n(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static boolean F$src$Z$aoypys() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().x();
        }
        return MMinecraft.f(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static VoxelShape H$src$Lgg_vape_wrapper_impl_VoxelShape_$1dlcquv() {
        return new VoxelShape(MMinecraft.x(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static FontRenderer getFontRenderer() {
        return new FontRenderer(Minecraft.vapeInstance.getMappings().U.J(Minecraft.i()));
    }

    public static ScaledResolution G() {
        return new ScaledResolution();
    }

    public static PlayerControllerMP playerController() {
        return new PlayerControllerMP(Minecraft.vapeInstance.getMappings().U.r(Minecraft.i()));
    }

    public static int Q() {
        return MMinecraft.S(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static Framebuffer getFrameBuffer() {
        return new Framebuffer(Minecraft.vapeInstance.getMappings().U.u(Minecraft.i()));
    }

    public static TextureManagerBridge M() {
        return new TextureManagerBridge(Minecraft.vapeInstance.getMappings().U.P(Minecraft.i()));
    }

    public static Entity Y() {
        return new Entity(Minecraft.vapeInstance.getMappings().U.F(Minecraft.i()));
    }

    public static void e(boolean bl) {
        s = bl;
    }

    public static FontManager q() {
        if (!ForgeVersion.MC_1_21_10.d()) {
            return null;
        }
        Object object = Minecraft.vapeInstance.getMappings().U.j(Minecraft.i());
        return object != null ? new FontManager(object) : null;
    }

    public static int J() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return Minecraft.p().I();
        }
        return Minecraft.vapeInstance.getMappings().U.Y(Minecraft.i());
    }

    public static boolean m$src$Z$baep3v() {
        return Minecraft.vapeInstance.getMappings().U.X$src$Z$1ecebix(Minecraft.i());
    }

    public static Object u() {
        return Minecraft.vapeInstance.getMappings().U.R$src$Ljava_lang_Object_$11ec019(Minecraft.i());
    }

    public static RenderItem v() {
        if (ForgeVersion.MC_1_7_10.L()) {
            return RenderItem.d();
        }
        return new RenderItem(MMinecraft.k(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static ResourceManager getResourcePackRepository() {
        return new ResourceManager(Minecraft.vapeInstance.getMappings().U.X(Minecraft.i()));
    }

    public static Timer getTimer() {
        return new Timer(MMinecraft.W(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static KeyboardHandler r() {
        return new KeyboardHandler(MMinecraft.o(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static GameSettings gameSettings() {
        return new GameSettings(Minecraft.vapeInstance.getMappings().U.b(Minecraft.i()));
    }

    public static MouseHandler s() {
        return new MouseHandler(MMinecraft.X(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static NetHandlerPlayClientImpl N() {
        return new NetHandlerPlayClientImpl(Minecraft.vapeInstance.getMappings().U.a(Minecraft.i()));
    }

    public static EntityPlayerSP thePlayer() {
        return new EntityPlayerSP(Vape.INSTANCE.getMappings().U.T(Minecraft.i()));
    }

    public static int l() {
        return Minecraft.vapeInstance.getMappings().U.L();
    }

    public static boolean g() {
        return s;
    }

    public static void B(GuiScreen guiScreen) {
        Minecraft.vapeInstance.getMappings().U.r(Minecraft.i(), guiScreen.getObject());
    }

    public static void E(int n) {
        MMinecraft.v(Minecraft.vapeInstance.getMappings().U, Minecraft.i(), n);
    }

    public static void W(Entity entity) {
        Minecraft.vapeInstance.getMappings().U.a(Minecraft.i(), entity.getObject());
    }

    public static TitledScreen k() {
        return new TitledScreen(Minecraft.vapeInstance.getMappings().U.A(Minecraft.i()));
    }

    public static RenderTypeBuffer getFramerateLimitTracker() {
        return new RenderTypeBuffer(Minecraft.vapeInstance.getMappings().U.S(Minecraft.i()));
    }

    public Minecraft() {
        super(Vape.INSTANCE.getMappings().U.J());
    }

    public static void w(MinecraftSessionWrapper minecraftSessionWrapper) {
        MMinecraft.p(Minecraft.vapeInstance.getMappings().U, Minecraft.i(), minecraftSessionWrapper.getObject());
    }

    public static MinecraftSessionWrapper Q$src$Lgg_vape_account_MinecraftSessionWrapper_$1ftnn3u() {
        return new MinecraftSessionWrapper(MMinecraft.G(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static Object i() {
        if (L == null) {
            L = Minecraft.vapeInstance.getMappings().U.J();
        }
        return L;
    }

    public static void S() {
        MMinecraft.Y(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static TextureManager getTextureManager() {
        return new TextureManager(Minecraft.vapeInstance.getMappings().U.c(Minecraft.i()));
    }

    public static void R() {
        MMinecraft.b(Minecraft.vapeInstance.getMappings().U, Minecraft.i());
    }

    public static void r(EntityLivingBase entityLivingBase) {
        Minecraft.vapeInstance.getMappings().U.G(Minecraft.i(), entityLivingBase == null ? null : entityLivingBase.getObject());
    }


    public static void currentScreen(Object object) {
        Minecraft.vapeInstance.getMappings().U.Y(Minecraft.i(), object);
    }

    public static RayTraceResult p$src$Lgg_vape_wrapper_impl_RayTraceResult_$5rw6n0() {
        return new RayTraceResult(Minecraft.vapeInstance.getMappings().U.q(Minecraft.i()));
    }

    public static MouseHelper p() {
        return new MouseHelper(MMinecraft.y$src$Ljava_lang_Object_$1igycos(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static EntityRenderer m$src$Lgg_vape_wrapper_impl_EntityRenderer_$13begmf() {
        return new EntityRenderer(Minecraft.vapeInstance.getMappings().U.m(Minecraft.i()));
    }

    public static void v(Runnable runnable) {
        MMinecraft.m(Minecraft.vapeInstance.getMappings().U).invokeVoid(Minecraft.i(), runnable);
    }

    public static ServerData H() {
        return new ServerData(Minecraft.vapeInstance.getMappings().U.l(Minecraft.i()));
    }

    public static WorldClient theWorld() {
        return new WorldClient(Minecraft.vapeInstance.getMappings().U.E(Minecraft.i()));
    }

    public static RenderGlobal O() {
        return new RenderGlobal(MMinecraft.N(Minecraft.vapeInstance.getMappings().U, Minecraft.i()));
    }

    public static void F$src$V$aoypvc() {
        Minecraft.vapeInstance.getMappings().U.V$src$V$9672l7(Minecraft.i());
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.s().u();
        }
    }

    public static void X(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().a(n);
            return;
        }
        Minecraft.vapeInstance.getMappings().U.B(Minecraft.i(), n);
    }

    public static void O(RayTraceResult rayTraceResult) {
        Minecraft.vapeInstance.getMappings().U.P(Minecraft.i(), rayTraceResult.getObject());
    }

    public static void U(int n) {
        if (ForgeVersion.MC_1_16_5.d()) {
            Minecraft.p().H(n);
            return;
        }
        Minecraft.vapeInstance.getMappings().U.R(Minecraft.i(), n);
    }

    public static GuiScreen currentScreen() {
        return new GuiScreen(Minecraft.vapeInstance.getMappings().U.U(Minecraft.i()));
    }

    public static Timer a_jo_2_T() {
        return Minecraft.getTimer();
    }

    public static EntityPlayerSP a_xH_J() {
        return Minecraft.thePlayer();
    }

    public static GuiScreen a_pt_1_w() {
        return Minecraft.currentScreen();
    }

    public static GameSettings a_w3_0_S() {
        return Minecraft.gameSettings();
    }
}

