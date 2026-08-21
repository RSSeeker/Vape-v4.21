package gg.vape.runtime;

import gg.vape.Vape;
import gg.vape.reflect.Badlion189Mappings;
import gg.vape.reflect.Fabric12111Mappings;
import gg.vape.reflect.Fabric262Mappings;
import gg.vape.reflect.NeoForge1201Mappings;
import gg.vape.reflect.NeoForge1211Mappings;
import gg.vape.reflect.Type;
import gg.vape.reflect.Vanilla1122Mappings;
import gg.vape.reflect.Vanilla1211Mappings;
import gg.vape.reflect.Vanilla1201Mappings;
import gg.vape.reflect.Vanilla1206Mappings;
import gg.vape.reflect.Vanilla12111Mappings;
import gg.vape.reflect.Vanilla1710Mappings;
import gg.vape.reflect.Vanilla189Mappings;
import gg.vape.reflect.Vanilla262Mappings;
import gg.vape.ui.click.GuiScreenNativeCallbackBridge;
import gg.vape.utils.Base64Util;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class NativeBridge {
    private static final String DEFAULT_CONFIG_JSON = "{"
            + "\"friends\":[],"
            + "\"profiles\":{},"
            + "\"otherdata\":[{\"frames\":["
            + "{\"title\":\"Combat\",\"x\":32,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"Render\",\"x\":144,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"Utility\",\"x\":256,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"World\",\"x\":368,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"Inventory\",\"x\":480,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"Favorites\",\"x\":592,\"y\":32,\"visible\":true,\"pinned\":false},"
            + "{\"title\":\"Settings\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false},"
            + "{\"title\":\"ModuleSearch\",\"x\":32,\"y\":32,\"visible\":false,\"pinned\":false}"
            + "]}]}";
    private static boolean forgeAbsent = true;
    private static volatile int vanillaMappingVersion;
    private static volatile boolean badlion189Runtime;
    private static volatile boolean fabric12111Runtime;
    private static volatile boolean neoForge1201Runtime;
    private static volatile boolean neoForge1211Runtime;
    private static volatile boolean fabric262Runtime;

    public static boolean isNeoForge1201Runtime() {
        return neoForge1201Runtime;
    }

    public static boolean isNeoForge1211Runtime() {
        return neoForge1211Runtime;
    }
    static boolean alphaTestWasEnabled;
    private static Method glGetFloatMethod;
    private static Method glGetIntegerVectorMethod;
    private static Method glVertexPointerMethod;
    private static Method glColorPointerMethod;
    private static Method glTexCoordPointerMethod;

    private static void resolveGlGetFloatMethod() {
        if (glGetFloatMethod != null) {
            return;
        }
        try {
            glGetFloatMethod = GL11.class.getMethod("glGetFloatv", Integer.TYPE, FloatBuffer.class);
        }
        catch (NoSuchMethodException modernNameMissing) {
            try {
                glGetFloatMethod = GL11.class.getMethod("glGetFloat", Integer.TYPE, FloatBuffer.class);
            }
            catch (NoSuchMethodException legacyNameMissing) {
                throw new IllegalStateException("Unable to resolve OpenGL float state method", legacyNameMissing);
            }
        }
    }

    private static void readGlFloats(int state, FloatBuffer destination) {
        NativeBridge.resolveGlGetFloatMethod();
        try {
            glGetFloatMethod.invoke(null, state, destination);
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read OpenGL state " + state, exception);
        }
    }

    private static void readGlIntegers(int state, IntBuffer destination) {
        if (glGetIntegerVectorMethod == null) {
            try {
                glGetIntegerVectorMethod = GL11.class.getMethod(
                        "glGetIntegerv", Integer.TYPE, IntBuffer.class);
            }
            catch (NoSuchMethodException modernNameMissing) {
                try {
                    glGetIntegerVectorMethod = GL11.class.getMethod(
                            "glGetInteger", Integer.TYPE, IntBuffer.class);
                }
                catch (NoSuchMethodException legacyNameMissing) {
                    throw new IllegalStateException(
                            "Unable to resolve OpenGL integer state method", legacyNameMissing);
                }
            }
        }
        try {
            glGetIntegerVectorMethod.invoke(null, state, destination);
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to read OpenGL integer state " + state, exception);
        }
    }

    private static Method resolveFloatPointerMethod(String name) {
        try {
            return GL11.class.getMethod(
                    name, Integer.TYPE, Integer.TYPE, Integer.TYPE, FloatBuffer.class);
        }
        catch (NoSuchMethodException modernSignatureMissing) {
            try {
                return GL11.class.getMethod(
                        name, Integer.TYPE, Integer.TYPE, FloatBuffer.class);
            }
            catch (NoSuchMethodException legacySignatureMissing) {
                throw new IllegalStateException("Unable to resolve OpenGL pointer method " + name,
                        legacySignatureMissing);
            }
        }
    }

    private static void invokeFloatPointer(Method method, int size, int stride,
                                           FloatBuffer buffer, String name) {
        try {
            if (method.getParameterCount() == 4) {
                method.invoke(null, size, 5126, stride, buffer);
            } else {
                method.invoke(null, size, stride, buffer);
            }
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Unable to invoke OpenGL pointer method " + name, exception);
        }
    }

    public static void vertexPointer(int size, int stride, FloatBuffer buffer) {
        if (glVertexPointerMethod == null) {
            glVertexPointerMethod = NativeBridge.resolveFloatPointerMethod("glVertexPointer");
        }
        NativeBridge.invokeFloatPointer(glVertexPointerMethod, size, stride, buffer, "glVertexPointer");
    }

    public static void colorPointer(int size, int stride, FloatBuffer buffer) {
        if (glColorPointerMethod == null) {
            glColorPointerMethod = NativeBridge.resolveFloatPointerMethod("glColorPointer");
        }
        NativeBridge.invokeFloatPointer(glColorPointerMethod, size, stride, buffer, "glColorPointer");
    }

    public static void texCoordPointer(int size, int stride, FloatBuffer buffer) {
        if (glTexCoordPointerMethod == null) {
            glTexCoordPointerMethod = NativeBridge.resolveFloatPointerMethod("glTexCoordPointer");
        }
        NativeBridge.invokeFloatPointer(glTexCoordPointerMethod, size, stride, buffer, "glTexCoordPointer");
    }

    public static int ss_3(String value) {
        return 0;
    }

    public static int sts() {
        return 1;
    }

    public static void smdp(int mode, int value) {
        NativeBridge.smd(mode, value);
    }

    //GetRenderHandler
    //Java Layer Unused
    public static Object grh() {
        return null;
    }

    //MakeFont
    //Java Layer Unused
    public static int mf(int fontId, int style, String text) {
        return 0;
    }

    //Controller Exit
    public static void exit(boolean forced) {
        System.out.println("exit " + forced);
    }

    //GetTexture
    //Java Layer Unused
    public static byte[] gt(String key) {
        return new byte[0];
    }

    //Disconnect
    //Disconnect dll with loader after finished loading
    public static void dc() {
    }

    //GetClassFields
    //Java Layer Unused
    public static String[] gcf(Class<?> targetClass) {
        if (targetClass == null) {
            return new String[0];
        }
        java.lang.reflect.Field[] fields = targetClass.getDeclaredFields();
        String[] names = new String[fields.length];
        for (int index = 0; index < fields.length; ++index) {
            names[index] = fields[index].getName();
        }
        return names;
    }

    public static int gts() {
        return 1;
    }

    public static boolean isForgeAbsent() {
        return forgeAbsent;
    }

    //Translate
    public static double[] trn(double worldX, double worldY, double worldZ) {
        FloatBuffer modelViewMatrix = BufferUtils.createFloatBuffer(16);
        FloatBuffer projectionMatrix = BufferUtils.createFloatBuffer(16);
        IntBuffer viewport = BufferUtils.createIntBuffer(16);
        NativeBridge.readGlFloats(2982, modelViewMatrix);
        NativeBridge.readGlFloats(2983, projectionMatrix);
        NativeBridge.readGlIntegers(2978, viewport);
        FloatBuffer screenPosition = BufferUtils.createFloatBuffer(3);
        GLU.gluProject((float)worldX, (float)worldY, (float)worldZ,
                modelViewMatrix, projectionMatrix, viewport, screenPosition);
        return new double[]{screenPosition.get(0), screenPosition.get(1), screenPosition.get(2)};
    }

    public static boolean gtcf(Object target, int index, int flags) {
        return false;
    }

    //GetKeyName
    public static native String gkn(long keyCode);

    //MessageBox
    public static void mb(int messageCode) {
        //can't understand why manthe would print error code instead of any meaningful text
    }

    //GetKeyState
    public static native short gks(int keyCode);

    //RenderState
    public static void rs(int phase, double width, double height) {
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0, width, height, 0.0, 1000.0, 3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
        if (phase > 0) {
            if (alphaTestWasEnabled) {
                GL11.glEnable((int)3008);
            }
        } else {
            alphaTestWasEnabled = GL11.glIsEnabled((int)3008);
            if (alphaTestWasEnabled) {
                GL11.glDisable((int)3008);
            }
        }
    }

    //GetClass
    public static Class<?> gc(String internalName) {
        try {
            return Class.forName(internalName.replace("/", "."));
        }
        catch (Throwable throwable) {
            return null;
        }
    }

    //GetAccessToken
    public static native String gat();

    //GetClassObjects
    //Java Layer Unused
    //since I hate jvmti, so I'm not going to implement this
    public static Object[] gco(Class<?> targetClass) {
        return new Object[0];
    }

    //GetClassBytes
    public static native byte[] gcb(Class<?> targetClass);

    public static native void trs(int state);

    //CopyString
    //Java Layer Unused
    public static String cs(int stringId) {
        return "";
    }

    public static native byte[] gfb(String name);

    public static void scm(String sourceName, String mappedName) {
    }

    //GetClassJava
    public static Class<?> gcj(String descriptor) {
        try {
            return descriptor.startsWith("[")
                    ? Class.forName(descriptor.substring(2, descriptor.length() - 1).replace("/", "."))
                    : Class.forName(Type.getType(descriptor.substring(1, descriptor.length() - 1)).getClassName());
        }
        catch (Exception exception) {
            return null;
        }
    }

    //GetProfile
    public static String gp(String key) {
        if ("all".equals(key)) {
            return Base64Util.encodeUtf8Base64(DEFAULT_CONFIG_JSON);
        }
        return "";
    }

    public static void test() {
    }

    //GetStringHightV2
    //Java Layer Unused
    public static double gshv2(int fontId, String text) {
        return 0.0;
    }

    //GetClassSignature
    public static String gcs(Class<?> targetClass) {
        if (targetClass == null) {
            return "";
        }
        return Type.getDescriptor(targetClass);
    }

    //MapVirtualKey
    public static native int mvk(int virtualKey, int scanCode);

    //GetStringHeight
    //Java Layer Unused
    public static double gsh(int fontId, String text) {
        return 0.0;
    }

    public static void start() throws Throwable {
        // Force version detection up front: MappedClasses resolves classes
        // during Vape's constructor, and the NeoForge/Forge mojmap flags must
        // already be set so those lookups pick the direct-name path.
        detectVanillaMappingVersion(
                Thread.currentThread().getContextClassLoader(),
                NativeBridge.class.getClassLoader());
        forgeAbsent = !isClassPresent("net.minecraftforge.common.ForgeVersion")
                && !isClassPresent("net.minecraftforge.fml.loading.FMLLoader");
        Vape vape = new Vape();
        NativeBridge.invokeVoidInit(vape, "loadMappings");
        if (badlion189Runtime) {
            NativeBridge.sce("Runtime profile: Badlion Client 1.8.9 (vanilla SRG namespace)");
        }
        NativeBridge.sce("LOAD initAccountInfo");
        if (!vape.initAccountInfo()) {
            NativeBridge.sce("WARN initAccountInfo; continuing without account information");
        } else {
            NativeBridge.sce("OK initAccountInfo");
        }
        NativeBridge.invokeVoidInit(vape, "initializeManagers");
    }

    private static void invokeVoidInit(Vape vape, String name) {
        for (Method method : Vape.class.getDeclaredMethods()) {
            if (!method.getName().equals(name) || !method.getReturnType().equals(Void.TYPE)) continue;
            NativeBridge.sce("LOAD " + name);
            method.setAccessible(true);
            try {
                method.invoke(vape, new Object[0]);
                NativeBridge.sce("OK " + name);
            }
            catch (java.lang.reflect.InvocationTargetException wrapper) {
                NativeBridge.logThrowable(name, wrapper.getCause() != null ? wrapper.getCause() : wrapper);
            }
            catch (Throwable other) {
                NativeBridge.logThrowable(name, other);
            }
            return;
        }
        NativeBridge.sce("MISSING void " + name + "()");
    }

    private static void logThrowable(String context, Throwable error) {
        int depth = 0;
        for (Throwable current = error; current != null && depth < 8; current = current.getCause(), ++depth) {
            NativeBridge.sce("EXC " + context + " -> " + current.getClass().getName() + ": " + current.getMessage());
            StackTraceElement[] frames = current.getStackTrace();
            for (int frameIndex = 0; frameIndex < frames.length && frameIndex < 12; ++frameIndex) {
                NativeBridge.sce("    at " + frames[frameIndex].toString());
            }
        }
    }

    //GetStringWidthV2
    //Java Layer Unused
    public static double gswv2(int fontId, String text) {
        return 0.0;
    }

    //DrawString
    //Java Layer Unused
    public static int ds(int fontId, String text, double x, double y, int color) {
        return 0;
    }

    //GetStringWidth
    //Java Layer Unused
    public static double gsw(int fontId, String text) {
        return 0.0;
    }

    //SetUsername
    //Not available under current recovery project
    public static void su(String username) {
    }

    //ClipboardCopy
    public static native void cpy(String text);

    public static long smpm(boolean pressed, long windowHandle, int button,
                            long cursorPosition, long extraInfo) {
        return 0L;
    }

    //Reload
    //Java Layer Unused
    public static void rl() {
    }

    //GetClassMethods
    //Java Layer Unused
    public static String[] gcm(Class<?> targetClass) {
        if (targetClass == null) {
            return new String[0];
        }
        Method[] methods = targetClass.getDeclaredMethods();
        String[] names = new String[methods.length];
        for (int index = 0; index < methods.length; ++index) {
            names[index] = methods[index].getName();
        }
        return names;
    }

    //GetKey
    //Java Layer Unused
    public static int gk() {
        return 0;
    }

    //SendMouseDown
    public static native void smd(int mode, int value);

    public static void rsc() {
    }

    //UpdateDiscord
    public static void updc(String serverDescription, String clientDescription) {
    }

    public static void fs() {
    }

    //DrawStringV2
    //Java Layer Unused
    public static native int dsv2(int fontId, String text, double x, double y,
                                  int color, float scale);

    //GetMinorVersion
    public static int gmv() {
        Throwable lastFailure = null;

        try {
            Object minorVersion = readStaticField(
                    "net.minecraftforge.common.ForgeVersion", "minorVersion");
            if (minorVersion instanceof Number) {
                return ((Number)minorVersion).intValue();
            }
            lastFailure = new IllegalStateException("ForgeVersion.minorVersion is not numeric");
        }
        catch (Throwable throwable) {
            lastFailure = throwable;
        }

        try {
            Object forgeVersion = readStaticField(
                    "net.minecraftforge.fml.loading.FMLLoader", "forgeVersion");
            int parsedVersion = parseForgeVersion(forgeVersion);
            if (parsedVersion >= 0) {
                return parsedVersion;
            }
            lastFailure = new IllegalStateException("FMLLoader.forgeVersion is not supported: " + forgeVersion);
        }
        catch (Throwable throwable) {
            lastFailure = throwable;
        }

        // Newer Forge versions expose a version string instead of minorVersion.
        try {
            Object forgeVersion = readStaticField(
                    "net.minecraftforge.common.ForgeVersion", "forgeVersion");
            int parsedVersion = parseForgeVersion(forgeVersion);
            if (parsedVersion >= 0) {
                return parsedVersion;
            }
            lastFailure = new IllegalStateException("ForgeVersion.forgeVersion is not supported: " + forgeVersion);
        }
        catch (Throwable throwable) {
            lastFailure = throwable;
        }

        try {
            Class<?> fmlLoader = Class.forName("net.minecraftforge.fml.loading.FMLLoader");
            Object versionInfo = fmlLoader.getMethod("versionInfo").invoke(null);
            Object forgeVersion = versionInfo.getClass().getMethod("forgeVersion").invoke(versionInfo);
            int parsedVersion = parseForgeVersion(forgeVersion);
            if (parsedVersion >= 0) {
                return parsedVersion;
            }
            lastFailure = new IllegalStateException(
                    "FMLLoader.versionInfo().forgeVersion() is not supported: " + forgeVersion);
        }
        catch (Throwable throwable) {
            lastFailure = throwable;
        }

        int vanillaVersion = detectVanillaMappingVersion(
                Thread.currentThread().getContextClassLoader(),
                NativeBridge.class.getClassLoader());
        NativeBridge.sce("DBG gmv vanillaVersion=" + vanillaVersion);
        if (vanillaVersion != 0) {
            return vanillaVersion;
        }

        IllegalStateException failure = new IllegalStateException(
                "Unable to determine Minecraft/Forge version");
        if (lastFailure != null) {
            failure.initCause(lastFailure);
        }
        throw failure;
    }

    private static boolean isClassPresent(String className) {
        try {
            Class.forName(className, false, NativeBridge.class.getClassLoader());
            return true;
        }
        catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    private static int detectVanillaMappingVersion(
            ClassLoader... preferredLoaders) {
        int detectedVersion = vanillaMappingVersion;
        if (detectedVersion != 0) {
            return detectedVersion;
        }
        boolean vanilla1710 = Vanilla1710Mappings.isRuntimePresent(preferredLoaders);
        boolean badlion189 = Badlion189Mappings.isRuntimePresent(preferredLoaders);
        boolean vanilla189 = badlion189
                || Vanilla189Mappings.isRuntimePresent(preferredLoaders);
        boolean vanilla1122 = Vanilla1122Mappings.isRuntimePresent(preferredLoaders);
        boolean vanilla1201 = Vanilla1201Mappings.isRuntimePresent(preferredLoaders);
        boolean neoForge1211Detected = NeoForge1211Mappings.isRuntimePresent(preferredLoaders);
        // The 26.1.2/26.2 runtimes are unobfuscated mojmap environments whose
        // anchor classes (Minecraft, getInstance, instance, ItemContainerContents,
        // ...) also satisfy the NeoForge 1.21.1 probes; exclude them explicitly.
        boolean vanilla262 = Vanilla262Mappings.isRuntimePresent(preferredLoaders);
        boolean fabric262 = Fabric262Mappings.isRuntimePresent(preferredLoaders);
        boolean neoForge1211 = neoForge1211Detected && !vanilla262 && !fabric262;
        // 1.20.1 and 1.21.1 share identical mojmap anchor classes, so a 1.21.1
        // runtime also matches the 1.20.1 probes; exclude it explicitly.
        boolean neoForge1201 = NeoForge1201Mappings.isRuntimePresent(preferredLoaders)
                && !neoForge1211;
        boolean vanilla1211 = Vanilla1211Mappings.isRuntimePresent(preferredLoaders);
        boolean vanilla1206 = Vanilla1206Mappings.isRuntimePresent(preferredLoaders);
        boolean vanilla12111 = Vanilla12111Mappings.isRuntimePresent(preferredLoaders);
        boolean fabric12111 = Fabric12111Mappings.isRuntimePresent(preferredLoaders);
        int matchingVersions = (vanilla1710 ? 1 : 0)
                + (vanilla189 ? 1 : 0) + (vanilla1122 ? 1 : 0)
                + (vanilla1201 || neoForge1201 ? 1 : 0)
                + (vanilla1211 || neoForge1211 ? 1 : 0)
                + (vanilla1206 ? 1 : 0)
                + (vanilla12111 || fabric12111 ? 1 : 0)
                + (vanilla262 || fabric262 ? 1 : 0);
        NativeBridge.sce("DBG detect: v1710=" + vanilla1710 + " v189=" + vanilla189
                + " v1122=" + vanilla1122 + " v1201=" + vanilla1201
                + " nf1201=" + neoForge1201 + " v1211=" + vanilla1211
                + " nf1211=" + neoForge1211 + " v1206=" + vanilla1206
                + " v12111=" + vanilla12111 + " fabric12111=" + fabric12111
                + " v262=" + vanilla262 + " fabric262=" + fabric262
                + " matching=" + matchingVersions);
        if (matchingVersions == 1) {
            badlion189Runtime = badlion189;
            fabric12111Runtime = fabric12111;
            neoForge1201Runtime = neoForge1201;
            neoForge1211Runtime = neoForge1211;
            fabric262Runtime = fabric262;
            if (vanilla1710) {
                detectedVersion = 13;
            } else if (vanilla189) {
                detectedVersion = 15;
            } else if (vanilla1122) {
                detectedVersion = 23;
            } else if (vanilla1201 || neoForge1201) {
                detectedVersion = 47;
            } else if (vanilla1211 || neoForge1211) {
                detectedVersion = 52;
            } else if (vanilla1206) {
                detectedVersion = 50;
            } else if (vanilla12111 || fabric12111) {
                detectedVersion = 61;
            } else {
                int modernProtocol = Vanilla262Mappings.protocolVersion(
                        preferredLoaders);
                detectedVersion = modernProtocol != 0
                        ? modernProtocol : 110;
            }
        }
        if (detectedVersion != 0) {
            vanillaMappingVersion = detectedVersion;
        }
        NativeBridge.sce("DBG detect result=" + detectedVersion);
        return detectedVersion;
    }

    private static Object readStaticField(String className, String fieldName) throws Exception {
        Class<?> owner = Class.forName(className);
        Field field;
        try {
            field = owner.getField(fieldName);
        }
        catch (NoSuchFieldException ignored) {
            field = owner.getDeclaredField(fieldName);
        }
        field.setAccessible(true);
        return field.get(null);
    }

    private static int parseForgeVersion(Object value) {
        if (value == null) {
            return -1;
        }
        String text = String.valueOf(value).trim();
        int start = 0;
        while (start < text.length() && !Character.isDigit(text.charAt(start))) {
            ++start;
        }
        int end = start;
        while (end < text.length() && Character.isDigit(text.charAt(end))) {
            ++end;
        }
        if (start == end) {
            return -1;
        }
        try {
            int parsed = Integer.parseInt(text.substring(start, end));
            if (parsed == 62) {
                return 100;
            }
            if (parsed == 65) {
                return 110;
            }
            return isKnownVersionId(parsed) ? parsed : -1;
        }
        catch (NumberFormatException ignored) {
            return -1;
        }
    }

    private static boolean isKnownVersionId(int version) {
        switch (version) {
            case 13: case 15: case 23: case 28: case 35: case 36: case 37:
            case 47: case 50: case 51: case 52: case 54: case 55: case 56: case 60: case 61:
            case 100: case 110:
                return true;
            default:
                return false;
        }
    }

    public static native int ss_2(String value);

    public static String sp(String key, String value) {
        return null;
    }

    public static void reload() {
    }

    public static void printLog(String message) {
        System.out.println(message);
    }

    //SetClassBytes
    public static native int scb(Class<?> targetClass, byte[] bytecode);

    //MakeFontV2
    //Java Layer Unused
    public static native int mfv2(int fontId, int style, String text);

    //SaveSettings
    @Deprecated
    public static native void ss(String value);

    public static boolean[] gls() {
        return new boolean[0];
    }

    //GetVanillaClass
    public static Class<?> gvc(String internalName) {
        ClassLoader contextLoader = Thread.currentThread().getContextClassLoader();
        ClassLoader bridgeLoader = NativeBridge.class.getClassLoader();
        int mappingVersion = detectVanillaMappingVersion(
                contextLoader, bridgeLoader);
        if (mappingVersion == 13) {
            return Vanilla1710Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 15) {
            return Vanilla189Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 23) {
            return Vanilla1122Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 47) {
            if (neoForge1201Runtime) {
                return NeoForge1201Mappings.resolveClass(
                        internalName, contextLoader, bridgeLoader);
            }
            return Vanilla1201Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 52) {
            if (neoForge1211Runtime) {
                return NeoForge1211Mappings.resolveClass(
                        internalName, contextLoader, bridgeLoader);
            }
            return Vanilla1211Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 50) {
            return Vanilla1206Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 61) {
            if (fabric12111Runtime) {
                return Fabric12111Mappings.resolveClass(
                        internalName, contextLoader, bridgeLoader);
            }
            return Vanilla12111Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        if (mappingVersion == 110 || mappingVersion == 100) {
            if (fabric262Runtime) {
                return Fabric262Mappings.resolveClass(
                        internalName, contextLoader, bridgeLoader);
            }
            return Vanilla262Mappings.resolveClass(
                    internalName, contextLoader, bridgeLoader);
        }
        return gc(internalName);
    }

    public static boolean isBadlion189Runtime() {
        return badlion189Runtime;
    }

    //SendClientError
    public static native void sce(String message);

    public static native Object inv(Method method, Object target, Object ... arguments);

    public static boolean om(int eventId, long firstArgument, long secondArgument) {
        return GuiScreenNativeCallbackBridge.onNotification(eventId, firstArgument, secondArgument);
    }

    public static void wh(long windowHandle) {
        GuiScreenNativeCallbackBridge.h(windowHandle);
    }
}
