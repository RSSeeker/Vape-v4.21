package gg.vape.module.render.item;

import gg.vape.module.render.ItemESP;
import gg.vape.wrapper.impl.EntityItem;
import gg.vape.wrapper.impl.EntityPlayerSP;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemESPGroup {
    public double currentZ;
    public double previousX;
    public double previousY;
    public double currentX;
    public double previousZ;
    private final List<Object> itemEntities;
    private final List<ItemStack> stackedItems;
    public double currentY;
    private final ItemESP parent;


    public List<Object> getEntityHandles() {
        return this.itemEntities;
    }

    public void addItem(EntityItem entityItem) {
        if (this.itemEntities.isEmpty()) {
            this.currentX = entityItem.z();
            this.currentY = entityItem.N();
            this.currentZ = entityItem.h();
            this.previousX = entityItem.M();
            this.previousY = entityItem.W();
            this.previousZ = entityItem.m$src$D$fwnne5();
        } else {
            int count = this.itemEntities.size();
            this.currentX = (this.currentX * (double)count + entityItem.z()) / (double)(count + 1);
            this.currentY = (this.currentY * (double)count + entityItem.N()) / (double)(count + 1);
            this.currentZ = (this.currentZ * (double)count + entityItem.h()) / (double)(count + 1);
        }
        this.itemEntities.add(entityItem.getObject());
        this.rebuildStacks();
    }

    public boolean isEmpty() {
        return this.itemEntities.isEmpty();
    }

    public void merge(ItemESPGroup other) {
        for (Object entityHandle : other.itemEntities) {
            if (this.itemEntities.contains(entityHandle)) continue;
            this.itemEntities.add(entityHandle);
        }
        double sumX = 0.0;
        double sumY = 0.0;
        double sumZ = 0.0;
        for (Object entityHandle : this.itemEntities) {
            EntityItem entityItem = new EntityItem(entityHandle);
            sumX += entityItem.z();
            sumY += entityItem.N();
            sumZ += entityItem.h();
        }
        int count = this.itemEntities.size();
        this.currentX = sumX / (double)count;
        this.currentY = sumY / (double)count;
        this.currentZ = sumZ / (double)count;
        this.rebuildStacks();
    }

    public void rebuildStacks() {
        this.stackedItems.clear();
        for (Object entityHandle : this.itemEntities) {
            EntityItem entityItem = new EntityItem(entityHandle);
            ItemStack itemStack = entityItem.getItemStack();
            if (!itemStack.isNotNull()) continue;
            if (this.parent.isGroupingEnabled()) {
                boolean merged = false;
                for (ItemStack existingStack : this.stackedItems) {
                    if (!existingStack.e(itemStack)) continue;
                    existingStack.Y(existingStack.t() + itemStack.t());
                    merged = true;
                    break;
                }
                if (merged) continue;
                ItemStack copyStack = ItemStack.S(itemStack.getItem());
                copyStack.i(itemStack.l());
                copyStack.s(itemStack.L());
                copyStack.Y(itemStack.t());
                this.stackedItems.add(copyStack);
                continue;
            }
            ItemStack singleStack = ItemStack.S(itemStack.getItem());
            singleStack.i(itemStack.l());
            singleStack.s(itemStack.L());
            singleStack.Y(itemStack.t());
            this.stackedItems.add(singleStack);
        }
    }

    public List<ItemStack> getStacks() {
        return this.stackedItems;
    }

    public ItemESPGroup(ItemESP parent, EntityItem entityItem) {
        this.parent = parent;
        this.itemEntities = new ArrayList<Object>();
        this.stackedItems = new ArrayList<ItemStack>();
        this.currentX = entityItem.z();
        this.currentY = entityItem.N();
        this.currentZ = entityItem.h();
        this.previousX = this.currentX;
        this.previousY = this.currentY;
        this.previousZ = this.currentZ;
        this.addItem(entityItem);
    }

    public Object getRepresentativeHandle() {
        return this.itemEntities.isEmpty() ? null : this.itemEntities.get(0);
    }

    public void update(List<Object> worldEntities, EntityPlayerSP player) {
        this.previousX = this.currentX;
        this.previousY = this.currentY;
        this.previousZ = this.currentZ;
        EntityItem representative = new EntityItem(this.getRepresentativeHandle());
        double distance = player.getDistanceToEntity(representative);
        double maxRadius = Math.max(1.5, distance / 5.0);
        Iterator<Object> iterator = this.itemEntities.iterator();
        while (iterator.hasNext()) {
            double deltaZ;
            double deltaY;
            Object entityHandle = iterator.next();
            if (!worldEntities.contains(entityHandle)) {
                iterator.remove();
                continue;
            }
            EntityItem entityItem = new EntityItem(entityHandle);
            double deltaX = entityItem.z() - this.currentX;
            if (!(Math.sqrt(deltaX * deltaX + (deltaY = entityItem.N() - this.currentY) * deltaY + (deltaZ = entityItem.h() - this.currentZ) * deltaZ) > maxRadius)) continue;
            iterator.remove();
        }
        if (!this.itemEntities.isEmpty()) {
            double sumX = 0.0;
            double sumY = 0.0;
            double sumZ = 0.0;
            for (Object entityHandle : this.itemEntities) {
                EntityItem entityItem = new EntityItem(entityHandle);
                sumX += entityItem.z();
                sumY += entityItem.N();
                sumZ += entityItem.h();
            }
            int count = this.itemEntities.size();
            this.currentX = sumX / (double)count;
            this.currentY = sumY / (double)count;
            this.currentZ = sumZ / (double)count;
        }
        this.rebuildStacks();
    }
}

