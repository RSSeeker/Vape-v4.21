package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.InsertedEventCallback;
import gg.vape.mapping.MappedClasses;
import gg.vape.ui.click.component.GuiComponent;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.ItemStack;
import gg.vape.wrapper.impl.TagBase;
import gg.vape.wrapper.impl.TagCompound;
import gg.vape.wrapper.impl.TextComponentString;
import java.util.ArrayList;
import java.util.List;

public class ItemStackTooltipCallback
implements InsertedEventCallback {
    private final Object P;
    private final Object z;
    public static boolean T;
    private List<Object> U;
    private final Object M;
    private static GuiComponent[] q;

    @Override
    public boolean fire() {
        if (T) {
            T = false;
            return false;
        }
        ItemStack itemStack = new ItemStack(this.z);
        EntityPlayer entityPlayer = new EntityPlayer(this.P);
        T = true;
        this.U = (List)itemStack.A(entityPlayer, this.M);
        if (Vape.INSTANCE.getClientSettings().showNbtTags.getEffectiveValue().booleanValue()) {
            if (itemStack.isNull() || itemStack.l() == null || this.U == null) {
                return true;
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            for (String string : new TagCompound(itemStack.l()).getKeySet()) {
                if (string.equals("display") || string.equals("Unbreakable") || string.equals("HideFlags") || string.equals("overrideMeta") || string.equals("AttributeModifiers")) continue;
                TagBase tagBase = new TagCompound(itemStack.l()).getTag(string);
                arrayList.add("");
                if (tagBase.isInstance(MappedClasses.Yg)) {
                    TagCompound tagCompound = new TagCompound(tagBase);
                    arrayList.add("\u00a79" + string);
                    for (String string2 : tagCompound.getKeySet()) {
                        TagBase tagBase2 = tagCompound.getTag(string2);
                        if (tagBase2.isInstance(MappedClasses.Yg) || tagBase2.isInstance(MappedClasses.qt)) continue;
                        arrayList.add(string2 + ": " + tagBase2.getObject().toString());
                    }
                    continue;
                }
                arrayList.add(string + ": " + tagBase.getObject().toString());
            }
            if (arrayList.isEmpty()) {
                return true;
            }
            if (ForgeVersion.MC_1_16_5.d()) {
                for (String string : arrayList) {
                    this.U.add(TextComponentString.create(string).getObject());
                }
            } else {
                this.U.addAll(arrayList);
            }
        }
        return true;
    }

    public static GuiComponent[] c() {
        return q;
    }

    public static void m(GuiComponent[] guiComponentArray) {
        q = guiComponentArray;
    }


    public ItemStackTooltipCallback(Object object, Object object2, boolean bl) {
        this.z = object;
        this.P = object2;
        this.M = bl;
    }

    static {
        ItemStackTooltipCallback.m(new GuiComponent[4]);
        T = false;
    }

    public ItemStackTooltipCallback(Object object, Object object2, Object object3) {
        this.z = object;
        this.P = object2;
        this.M = object3;
    }

    public List<?> getTooltip() {
        return this.U;
    }
}

