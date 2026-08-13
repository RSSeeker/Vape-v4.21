package gg.vape.module.combat;

import gg.vape.event.EventHandler;
import gg.vape.event.impl.EventPacketSend;
import gg.vape.mapping.MappedClasses;
import gg.vape.module.Category;
import gg.vape.module.Mod;
import gg.vape.unmap.ItemLimitData;
import gg.vape.value.LimitValue;
import gg.vape.wrapper.impl.CPacketPlayerDigging;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.Packet;

public class BlockHit
extends Mod {
    public final LimitValue allowedItems = LimitValue.create(this, "noitemrelease-alloweditems", "Allowed items", LimitValue.ALLOW_LIST_COLOR, new ItemLimitData("swords"), new ItemLimitData("food"), new ItemLimitData("potions"));
    private static final long MODULE_ID = -2073545794591715018L;


    public BlockHit() {
        super("NoItemRelease", (int)MODULE_ID, Category.OTHER);
        this.addValue(this.allowedItems);
    }

    @EventHandler
    public void onPacketSend(EventPacketSend event) {
        Packet packet = event.getPacket();
        boolean inGui = Minecraft.l$src$Z$b9uwii();
        if (inGui) {
            if (packet.isInstance(MappedClasses.DN)
                    && new CPacketPlayerDigging(packet).isReleaseUseItem()) {
                event.setCancelled(true);
            }
            return;
        }
        if (packet.isInstance(MappedClasses.DN)
                && new CPacketPlayerDigging(packet).isReleaseUseItem()
                && this.allowedItems.matches(Minecraft.thePlayer().B$src$Lgg_vape_wrapper_impl_ItemStack_$impdvt())) {
            event.setCancelled(true);
        }
    }
}

