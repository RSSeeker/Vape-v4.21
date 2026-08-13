package gg.vape.module.world;

import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.module.world.fakelag.FakeLagDelayedPacketMode;
import gg.vape.module.world.fakelag.FakeLagPacketDelaySubModule;
import gg.vape.module.world.fakelag.LegacyFakeLagCombatPacketQueueMode;
import gg.vape.value.ModeValue;
import gg.vape.value.NumberValue;
import gg.vape.value.SubModuleValue;
import gg.vape.wrapper.impl.ForgeVersion;

public class FakeLag
extends Mod {
    private final SubModuleValue repelMode;
    private final ModeValue modeValue;
    private static final long MOD_ID = -7445823933619103425L;
    public final NumberValue delay;
    private final SubModuleValue dynamicMode;
    private final SubModuleValue latencyMode = new FakeLagDelayedPacketMode(this, "Latency").getSelectionValue();

    public FakeLag() {
        super("FakeLag", (int)MOD_ID, Category.UTILITY, "Simulates lag");
        this.dynamicMode = new FakeLagPacketDelaySubModule(this, "Dynamic").getSelectionValue();
        this.repelMode = new LegacyFakeLagCombatPacketQueueMode(this, "Repel").getSelectionValue();
        this.delay = NumberValue.create((Object)this, "Delay", "#", "ms", 1.0, 100.0, 1000.0, 10.0);
        this.modeValue = ForgeVersion.MC_1_7_10.Y() ? ModeValue.create((Object)this, "Mode", this.latencyMode, this.latencyMode, this.dynamicMode, this.repelMode) : ModeValue.create((Object)this, "Mode", this.latencyMode, this.latencyMode, this.repelMode);
        this.addValue(this.modeValue, this.delay);
        this.delay.setMaximumFractionDigits(0);
    }

    @Override
    public String getSimpleSuffix() {
        return this.modeValue.getDisplayValue();
    }

    @Override
    public String getDetailedSuffix() {
        SubModuleValue subModuleValue = (SubModuleValue)this.modeValue.getValue();
        return ((Mod)subModuleValue.getInstance()).getDetailedSuffix();
    }
}
