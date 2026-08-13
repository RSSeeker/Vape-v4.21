package gg.vape.module.combat.crystalaura;


public class ExplosionType {
    public static final ExplosionType ANCHOR;
    public static final ExplosionType BED;
    private static int controlFlowState;
    private final String label;
    public static final ExplosionType CRYSTAL;
    private final float explosionPower;

    static {
        if (ExplosionType.getDerivedControlFlowState() == 0) {
            ExplosionType.setControlFlowState(55);
        }
        CRYSTAL = new ExplosionType(6.0f, "Crystal");
        ANCHOR = new ExplosionType(5.0f, "Anchor");
        BED = new ExplosionType(5.0f, "Bed");
    }

    public static void setControlFlowState(int state) {
        controlFlowState = state;
    }

    private ExplosionType(float power, String name) {
        this.explosionPower = power;
        this.label = name;
    }

    public String getLabel() {
        return this.label;
    }

    public static int getDerivedControlFlowState() {
        int state = ExplosionType.getControlFlowState();
        if (state == 0) {
            return 87;
        }
        return 0;
    }


    public float getExplosionPower() {
        return this.explosionPower;
    }

    public static int getControlFlowState() {
        return controlFlowState;
    }
}

