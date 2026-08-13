package gg.vape.mapping;

import gg.vape.event.impl.EventPlayerUseItem;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.EnumHand;
import gg.vape.wrapper.impl.ItemStack;

public class PlayerUseItemCallback
implements InsertedEventCallback {
    private final EntityPlayer h;
    private final EnumHand r;

    @Override
    public boolean fire() {
        ItemStack itemStack = this.h.i(this.r);
        EventPlayerUseItem eventPlayerUseItem = new EventPlayerUseItem(itemStack.getObject());
        return eventPlayerUseItem.fire();
    }

    public PlayerUseItemCallback(Object object, Object object2) {
        this.h = new EntityPlayer(object);
        this.r = new EnumHand(object2);
    }
}

