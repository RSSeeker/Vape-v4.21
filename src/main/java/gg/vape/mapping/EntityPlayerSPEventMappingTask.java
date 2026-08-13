package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventMotion;
import gg.vape.event.impl.EventPostLocalPlayerTick;
import gg.vape.event.impl.EventPostMotion;
import gg.vape.event.impl.EventPreLocalPlayerTick;
import gg.vape.event.impl.EventPreMotion;
import gg.vape.event.impl.EventSetSprinting;
import gg.vape.runtime.NativeBridge;
import gg.vape.wrapper.impl.ForgeVersion;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;

public class EntityPlayerSPEventMappingTask
extends JavassistMappingTask {
    private String W;
    private String y;
    private String N;
    private MappingMethod j = null;
    private String f;
    private String Z;
    private String L;

    private Object lambda$create$0() throws CannotCompileException, NotFoundException {
        this.m();
        return null;
    }

    public static String L(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.N;
    }

    private void Q$src$V$gyu8sk() {
        if (ForgeVersion.MC_1_21_10.d()) {
            return;
        }
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().CC.z;
        this.c(mappingMethod, EventPreLocalPlayerTick.class, "$0");
        this.k(mappingMethod, EventPostLocalPlayerTick.class, "$0");
    }

    public static String T(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.y;
    }

    public EntityPlayerSPEventMappingTask() {
        super(MappedClasses.z5);
    }

    public static String S(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.f;
    }

    private void p() {
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().Rr.jW;
        this.c(mappingMethod, EventSetSprinting.class, "$0, $1");
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    @Override
    public void transform() {
        this.p();
        this.Q$src$V$gyu8sk();
        if (ForgeVersion.MC_1_7_10.Y()) {
            this.J(this::lambda$create$0);
        }
    }

    public static String O(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.W;
    }

    public static String B(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.Z;
    }

    private void m() throws CannotCompileException, NotFoundException {
        String string;
        this.j = Vape.INSTANCE.getMappings().CC.k;
        if (ForgeVersion.MC_1_20_6.d()) {
            this.L = Vape.INSTANCE.getMappings().Rr.Q.getResolvedName();
            this.Z = Vape.INSTANCE.getMappings().Rr.q.getResolvedName();
            this.y = Vape.INSTANCE.getMappings().Rr.O.getResolvedName();
        } else {
            this.W = Vape.INSTANCE.getMappings().Rr.jv.getResolvedName();
            this.f = Vape.INSTANCE.getMappings().Rr.jT.getResolvedName();
            this.N = Vape.INSTANCE.getMappings().Rr.U.getResolvedName();
        }
        String string2 = EventMotion.class.getName();
        CtBehavior ctBehavior = this.F(this.j);
        this.c(this.j, EventPreMotion.class, "$0");
        this.k(this.j, EventPostMotion.class, "$0");
        boolean bl = Vape.INSTANCE.isForgeAbsent();
        if (bl && NativeBridge.gc(string = "aaa") == null) {
            bl = false;
        }
        boolean bl2 = bl;
        CtClass ctClass = this.i(MappedClasses.zc);
        ctBehavior.instrument(new EntityPlayerSPMotionExprEditor(this, ctClass, string2, bl2));
    }

    public static String N(EntityPlayerSPEventMappingTask entityPlayerSPEventMappingTask) {
        return entityPlayerSPEventMappingTask.L;
    }
}
