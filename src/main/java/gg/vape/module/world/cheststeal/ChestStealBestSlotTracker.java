package gg.vape.module.world.cheststeal;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.world.ChestSteal;
import gg.vape.module.world.cheststeal.ChestStealSlotScore;
import gg.vape.utils.ItemStackScoreUtil;
import gg.vape.wrapper.impl.Item;
import gg.vape.wrapper.impl.ItemStack;
import java.util.ArrayList;

public class ChestStealBestSlotTracker {
    private final ChestStealSlotScore sword = new ChestStealSlotScore();
    private final ChestStealSlotScore hoe = new ChestStealSlotScore();
    private final ChestStealSlotScore boots = new ChestStealSlotScore();
    private final ChestStealSlotScore axe = new ChestStealSlotScore();
    private final ChestSteal chestSteal;
    private final ChestStealSlotScore pickaxe = new ChestStealSlotScore();
    private final ChestStealSlotScore leggings = new ChestStealSlotScore();
    private final ChestStealSlotScore bow = new ChestStealSlotScore();
    private final ChestStealSlotScore shovel = new ChestStealSlotScore();
    private final ChestStealSlotScore helmet = new ChestStealSlotScore();
    private final ChestStealSlotScore chestplate = new ChestStealSlotScore();

    public boolean trackCandidate(ItemStack itemStack, int slotIndex) {
        if (itemStack.isNull() || itemStack.getItem().isNull()) {
            return false;
        }
        Item item = itemStack.getItem();
        if (ItemStackScoreUtil.R(item)) {
            int armorSlot = this.chestSteal.getArmorSlot(itemStack);
            double score = ItemStackScoreUtil.L(itemStack);
            if (armorSlot == 8) {
                this.boots.updateIfHigher(slotIndex, score);
            } else if (armorSlot == 7) {
                this.leggings.updateIfHigher(slotIndex, score);
            } else if (armorSlot == 6) {
                this.chestplate.updateIfHigher(slotIndex, score);
            } else if (armorSlot == 5) {
                this.helmet.updateIfHigher(slotIndex, score);
            }
            return true;
        }
        if (ItemStackScoreUtil.h(item)) {
            this.sword.updateIfHigher(slotIndex, ItemStackScoreUtil.k(itemStack));
            return true;
        }
        if (item.isInstance(MappedClasses.Vl)) {
            this.bow.updateIfHigher(slotIndex, ItemStackScoreUtil.O(itemStack));
            return true;
        }
        if (ItemStackScoreUtil.m(item)) {
            this.pickaxe.updateIfHigher(slotIndex, ItemStackScoreUtil.T(itemStack));
            return true;
        }
        if (ItemStackScoreUtil.T(item)) {
            this.axe.updateIfHigher(slotIndex, ItemStackScoreUtil.V(itemStack));
            return true;
        }
        if (ItemStackScoreUtil.g(item)) {
            this.shovel.updateIfHigher(slotIndex, ItemStackScoreUtil.g(itemStack));
            return true;
        }
        if (ItemStackScoreUtil.k(item)) {
            this.hoe.updateIfHigher(slotIndex, ItemStackScoreUtil.u(itemStack));
            return true;
        }
        return false;
    }

    public ArrayList<Integer> getBestSlotIndexes() {
        ArrayList<Integer> slotIndexes = new ArrayList<Integer>();
        this.addSlotIfPresent(slotIndexes, this.sword);
        this.addSlotIfPresent(slotIndexes, this.boots);
        this.addSlotIfPresent(slotIndexes, this.leggings);
        this.addSlotIfPresent(slotIndexes, this.chestplate);
        this.addSlotIfPresent(slotIndexes, this.helmet);
        this.addSlotIfPresent(slotIndexes, this.pickaxe);
        this.addSlotIfPresent(slotIndexes, this.axe);
        this.addSlotIfPresent(slotIndexes, this.shovel);
        this.addSlotIfPresent(slotIndexes, this.hoe);
        this.addSlotIfPresent(slotIndexes, this.bow);
        return slotIndexes;
    }

    public void reset() {
        this.sword.reset();
        this.boots.reset();
        this.leggings.reset();
        this.chestplate.reset();
        this.helmet.reset();
        this.pickaxe.reset();
        this.axe.reset();
        this.shovel.reset();
        this.hoe.reset();
        this.bow.reset();
    }

    public ChestStealBestSlotTracker(ChestSteal chestSteal) {
        this.chestSteal = chestSteal;
    }

    private void addSlotIfPresent(ArrayList<Integer> slotIndexes, ChestStealSlotScore slotScore) {
        if (slotScore.hasSlot()) {
            slotIndexes.add(slotScore.getSlotIndex());
        }
    }
}
