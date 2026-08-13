package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.event.impl.EventBedBreakerUpdate;
import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.event.impl.EventPostAttack;
import gg.vape.event.impl.EventPreAttack;
import gg.vape.event.impl.EventWindowClick;
import gg.vape.wrapper.impl.ForgeVersion;

public class PlayerControllerMPEventMappingTask
extends JavassistMappingTask {
    public static final boolean U = ForgeVersion.MC_1_16_5.d() && (Vape.INSTANCE.isForgeAbsent() || Vape.INSTANCE.isVanillaMinecraftPresent());

    public PlayerControllerMPEventMappingTask() {
        super(MappedClasses.ld);
    }

    @Override
    public void transform() {
        Object object;
        MappingMethod mappingMethod = Vape.INSTANCE.getMappings().hj.c;
        if (ForgeVersion.MC_1_12_2.v()) {
            this.O(mappingMethod, EventPlayerUseItem.class, "$3", "false");
        } else if (ForgeVersion.MC_1_12_2.d()) {
            object = ForgeVersion.MC_1_20_6.d() ? "$1, $2" : "$1, $3";
            this.O(mappingMethod, PlayerUseItemCallback.class, (String)object, MappedClasses.zr.getName() + "." + Vape.INSTANCE.getMappings().enumActionResult.passField.getResolvedName());
        }
        this.c(Vape.INSTANCE.getMappings().hj.L, EventBedBreakerUpdate.class, "");
        if (!U) {
            object = Vape.INSTANCE.getMappings().hj.i;
            this.c((MappingMethod)object, EventPreAttack.class, "$2");
            this.k((MappingMethod)object, EventPostAttack.class, "$2");
        }
        this.O(Vape.INSTANCE.getMappings().hj.I, EventWindowClick.class, "$0", "null");
    }

}

