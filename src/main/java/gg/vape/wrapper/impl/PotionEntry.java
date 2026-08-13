package gg.vape.wrapper.impl;

import gg.vape.Vape;
import gg.vape.mapping.MappedClasses;
import gg.vape.utils.StringUtils;
import gg.vape.wrapper.Wrapper;
import gg.vape.wrapper.impl.EntityLiving;
import gg.vape.wrapper.impl.EntityLivingBase;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.I18n;
import gg.vape.wrapper.impl.Potion;
import gg.vape.wrapper.impl.PotionEntryBuilder;
import gg.vape.wrapper.impl.PotionEntryResolveException;
import gg.vape.wrapper.impl.StatusEffect;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class PotionEntry {
    private final String name;
    private final ForgeVersion supportedVersion;
    @Nullable
    private Potion legacyPotion;
    private static final long CONTROL_FLOW_SEED;
    private final Map<ForgeVersion, Integer> idsByVersion;
    private final short legacyId;
    @Nullable
    private StatusEffect statusEffect;

    @Nullable
    public Potion getLegacyPotion() {
        return this.legacyPotion;
    }

    public boolean matchesLegacyPotion(Potion potion) {
        return this.legacyPotion.equals(potion);
    }

    static {
        CONTROL_FLOW_SEED = 3010331125236105215L;
    }

    public boolean isSupported() {
        return ForgeVersion.c() >= this.supportedVersion.i();
    }

    private static Exception propagateException(Exception exception) {
        return exception;
    }

    public static PotionEntryBuilder builder() {
        return new PotionEntryBuilder();
    }

    void resolve() throws PotionEntryResolveException {
        if (!this.isSupported()) {
            return;
        }
        Integer n = null;
        for (Map.Entry<ForgeVersion, Integer> entry : this.idsByVersion.entrySet()) {
            ForgeVersion forgeVersion = entry.getKey();
            int n2 = entry.getValue();
            if (!forgeVersion.d()) continue;
            n = n2;
        }
        if (n != null) {
            if (ForgeVersion.MC_1_16_5.d()) {
                if (ForgeVersion.MC_1_21_4.d()) {
                    n = n - 1;
                }
                this.statusEffect = StatusEffect.E(n);
            } else {
                this.legacyPotion = Potion.getPotionById(n);
            }
            if (this.statusEffect != null && this.statusEffect.isNotNull() || this.legacyPotion != null && this.legacyPotion.isNotNull()) {
                return;
            }
        }
        int n3 = 0;
        int n4 = (int)CONTROL_FLOW_SEED;
        int n5 = -1;
        while (true) {
            String string;
            Wrapper wrapper;
            if (ForgeVersion.MC_1_16_5.d()) {
                wrapper = StatusEffect.E(n3);
                if (wrapper.isNull()) {
                    if (n3 != 0) break;
                    ++n3;
                    continue;
                }
                string = ((StatusEffect)wrapper).d();
            } else {
                wrapper = Potion.getPotionById(n3);
                if (wrapper.isNull()) {
                    if (n3 != 0) break;
                    ++n3;
                    continue;
                }
                string = I18n.format(((Potion)wrapper).y$src$Ljava_lang_String_$yl6pfj(), new Object[0]);
            }
            int n6 = StringUtils.Q(this.name, string);
            if (n6 < n4) {
                n5 = n3;
                n4 = n6;
            }
            ++n3;
        }
        if (ForgeVersion.MC_1_16_5.d()) {
            this.statusEffect = StatusEffect.E(n5);
        } else {
            this.legacyPotion = Potion.getPotionById(n5);
        }
        if (this.legacyPotion == null && this.statusEffect == null) {
            throw new PotionEntryResolveException(this);
        }
    }

    public void t(EntityLivingBase entityLivingBase, Object object, int n) {
        EntityLiving entityLiving;
        Map map;
        if (ForgeVersion.MC_1_16_5.d() && entityLivingBase.isInstance(MappedClasses.zQ) && (map = (entityLiving = new EntityLiving(entityLivingBase.getObject())).T$src$Ljava_util_Map_$f5d6t2()).containsKey(this.statusEffect.getObject())) {
            map.remove(this.statusEffect.getObject());
            entityLiving.C(true);
        }
        Vape.INSTANCE.getMappings().qU.b(this.getResolvedObject(), entityLivingBase.getObject(), object, n);
    }

    @Nullable
    public StatusEffect getStatusEffect() {
        return this.statusEffect;
    }

    public short getLegacyId() {
        return this.legacyId;
    }

    public int getResolvedId() {
        if (ForgeVersion.MC_1_16_5.d()) {
            if (this.statusEffect == null) {
                return -1;
            }
            return StatusEffect.v(this.statusEffect);
        }
        if (this.legacyPotion == null) {
            return -1;
        }
        return this.legacyPotion.getId();
    }

    public PotionEntry(PotionEntryBuilder potionEntryBuilder) {
        this.name = PotionEntryBuilder.getName(potionEntryBuilder);
        this.legacyId = PotionEntryBuilder.getLegacyId(potionEntryBuilder);
        this.idsByVersion = new LinkedHashMap<ForgeVersion, Integer>(PotionEntryBuilder.getIdsByVersion(potionEntryBuilder));
        this.supportedVersion = PotionEntryBuilder.getSupportedVersion(potionEntryBuilder);
    }

    public boolean isResolved() {
        if (ForgeVersion.MC_1_16_5.d()) {
            return !this.statusEffect.p();
        }
        return this.legacyPotion.n();
    }

    public Object getResolvedObject() {
        if (ForgeVersion.MC_1_16_5.d()) {
            if (this.statusEffect == null) {
                return null;
            }
            return this.statusEffect.getObject();
        }
        if (this.legacyPotion == null) {
            return null;
        }
        return this.legacyPotion.getObject();
    }

    public String getName() {
        return this.name;
    }
}
