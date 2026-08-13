package gg.vape.wrapper.impl;

import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.PotionEffect;
import gg.vape.wrapper.impl.PotionEntry;
import gg.vape.wrapper.impl.PotionEntryResolveException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jetbrains.annotations.Nullable;

public class PotionRegistry {
    public static final PotionEntry O;
    public static final PotionEntry u;
    public static final PotionEntry k;
    public static final PotionEntry d;
    public static final PotionEntry T;
    public static final PotionEntry Z;
    public static final PotionEntry w;
    public static final PotionEntry W;
    public static final PotionEntry v;
    private static final Map<Integer, PotionEntry> V;
    public static final PotionEntry L;
    public static final PotionEntry o;
    public static final PotionEntry c;
    public static final PotionEntry P;
    public static final PotionEntry f;
    public static final PotionEntry a;
    public static final PotionEntry h;
    public static final PotionEntry S;
    public static final PotionEntry B;
    public static final PotionEntry G;
    public static final PotionEntry K;
    public static final PotionEntry X;
    public static final PotionEntry R;
    public static final PotionEntry x;
    private static boolean A;
    public static final PotionEntry e;
    public static final PotionEntry z;
    private static boolean F;
    public static final PotionEntry i;
    public static final PotionEntry J;
    public static final PotionEntry N;
    public static final PotionEntry j;
    public static final PotionEntry t;
    public static final PotionEntry E;
    public static final PotionEntry Q;
    private static final Map<Short, PotionEntry> b;
    public static final PotionEntry C;
    public static final PotionEntry y;
    public static final PotionEntry r;
    public static final PotionEntry M;
    public static final PotionEntry l;
    public static final PotionEntry U;
    public static final PotionEntry H;

    public static List<PotionEntry> O() {
        ArrayList<PotionEntry> arrayList = new ArrayList<PotionEntry>();
        for (PotionEntry potionEntry : b.values()) {
            if (potionEntry.getResolvedObject() == null) continue;
            arrayList.add(potionEntry);
        }
        return arrayList;
    }

    public static boolean m() {
        boolean bl = PotionRegistry.g();
        return true;
    }

    @Nullable
    public static PotionEntry R(PotionEffect potionEffect) {
        return V.get(potionEffect.C());
    }

    @Nullable
    public static PotionEntry A(short s) {
        return b.get(s);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static void x(boolean bl) {
        A = bl;
    }

    public static boolean g() {
        return A;
    }

    public static List<PotionEntry> S() {
        return new ArrayList<PotionEntry>(b.values());
    }

    public static void d() {
        if (F) {
            return;
        }
        F = true;
        for (Field field : PotionRegistry.class.getDeclaredFields()) {
            if (!field.getType().equals(PotionEntry.class)) continue;
            PotionEntry potionEntry = null;
            try {
                potionEntry = (PotionEntry)field.get(null);
                potionEntry.resolve();
            }
            catch (PotionEntryResolveException potionEntryResolveException) {
            }
            catch (Throwable throwable) {
                // empty catch block
            }
            if (potionEntry == null) continue;
            b.put(potionEntry.getLegacyId(), potionEntry);
            V.put(potionEntry.getResolvedId(), potionEntry);
        }
    }

    static {
        if (PotionRegistry.g()) {
            PotionRegistry.x(true);
        }
        String[] stringArray = new String[]{"Strength", "Weaving", "Health Boost", "Levitation", "Instant Health", "Absorption", "Glowing", "Dolphin's Grace", "Saturation", "Raid Omen", "Haste", "Mining Fatigue", "Slowness", "Luck", "Bad Omen", "Regeneration", "Infested", "Weakness", "Speed", "Hunger", "Fire Resistance", "Jump Boost", "Conduit Power", "Poison", "Hero of the Village", "Wind Charge", "Bad Luck", "Oozing", "Night Vision", "Slow Falling", "Nausea", "Resistance", "Invisibility", "Trial Omen", "Blindness", "Water Breathing", "Instant Damage", "Wither", "Darkness"};
        U = PotionEntry.builder().setName(stringArray[18]).setLegacyId((short)0).addId(ForgeVersion.MC_1_7_10, 1).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        o = PotionEntry.builder().setName(stringArray[12]).setLegacyId((short)1).addId(ForgeVersion.MC_1_7_10, 2).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        t = PotionEntry.builder().setName(stringArray[0]).setLegacyId((short)2).addId(ForgeVersion.MC_1_7_10, 5).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        z = PotionEntry.builder().setName(stringArray[4]).setLegacyId((short)3).addId(ForgeVersion.MC_1_7_10, 6).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        i = PotionEntry.builder().setName(stringArray[15]).setLegacyId((short)4).addId(ForgeVersion.MC_1_7_10, 10).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        P = PotionEntry.builder().setName(stringArray[31]).setLegacyId((short)5).addId(ForgeVersion.MC_1_7_10, 11).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        W = PotionEntry.builder().setName(stringArray[20]).setLegacyId((short)6).addId(ForgeVersion.MC_1_7_10, 12).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        Z = PotionEntry.builder().setName(stringArray[21]).setLegacyId((short)7).addId(ForgeVersion.MC_1_7_10, 8).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        K = PotionEntry.builder().setName(stringArray[34]).setLegacyId((short)8).addId(ForgeVersion.MC_1_7_10, 15).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        E = PotionEntry.builder().setName(stringArray[10]).setLegacyId((short)9).addId(ForgeVersion.MC_1_7_10, 3).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        X = PotionEntry.builder().setName(stringArray[30]).setLegacyId((short)10).addId(ForgeVersion.MC_1_7_10, 9).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        u = PotionEntry.builder().setName(stringArray[11]).setLegacyId((short)11).addId(ForgeVersion.MC_1_7_10, 4).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        B = PotionEntry.builder().setName(stringArray[36]).setLegacyId((short)12).addId(ForgeVersion.MC_1_7_10, 7).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        c = PotionEntry.builder().setName(stringArray[35]).setLegacyId((short)13).addId(ForgeVersion.MC_1_7_10, 13).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        R = PotionEntry.builder().setName(stringArray[32]).setLegacyId((short)14).addId(ForgeVersion.MC_1_7_10, 14).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        T = PotionEntry.builder().setName(stringArray[28]).setLegacyId((short)15).addId(ForgeVersion.MC_1_7_10, 16).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        j = PotionEntry.builder().setName(stringArray[19]).setLegacyId((short)16).addId(ForgeVersion.MC_1_7_10, 17).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        M = PotionEntry.builder().setName(stringArray[17]).setLegacyId((short)17).addId(ForgeVersion.MC_1_7_10, 18).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        r = PotionEntry.builder().setName(stringArray[23]).setLegacyId((short)18).addId(ForgeVersion.MC_1_7_10, 19).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        G = PotionEntry.builder().setName(stringArray[37]).setLegacyId((short)19).addId(ForgeVersion.MC_1_7_10, 20).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        J = PotionEntry.builder().setName(stringArray[2]).setLegacyId((short)20).addId(ForgeVersion.MC_1_7_10, 21).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        d = PotionEntry.builder().setName(stringArray[5]).setLegacyId((short)21).addId(ForgeVersion.MC_1_7_10, 22).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        Q = PotionEntry.builder().setName(stringArray[8]).setLegacyId((short)22).addId(ForgeVersion.MC_1_7_10, 23).setSupportedVersion(ForgeVersion.MC_1_7_10).build();
        v = PotionEntry.builder().setName(stringArray[6]).setLegacyId((short)23).addId(ForgeVersion.MC_1_12_2, 24).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        h = PotionEntry.builder().setName(stringArray[3]).setLegacyId((short)24).addId(ForgeVersion.MC_1_12_2, 25).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        L = PotionEntry.builder().setName(stringArray[13]).setLegacyId((short)25).addId(ForgeVersion.MC_1_12_2, 26).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        C = PotionEntry.builder().setName(stringArray[26]).setLegacyId((short)26).addId(ForgeVersion.MC_1_12_2, 27).setSupportedVersion(ForgeVersion.MC_1_12_2).build();
        k = PotionEntry.builder().setName(stringArray[29]).setLegacyId((short)27).addId(ForgeVersion.MC_1_16_5, 28).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        f = PotionEntry.builder().setName(stringArray[22]).setLegacyId((short)28).addId(ForgeVersion.MC_1_16_5, 29).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        H = PotionEntry.builder().setName(stringArray[7]).setLegacyId((short)29).addId(ForgeVersion.MC_1_16_5, 30).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        a = PotionEntry.builder().setName(stringArray[14]).setLegacyId((short)30).addId(ForgeVersion.MC_1_16_5, 31).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        y = PotionEntry.builder().setName(stringArray[24]).setLegacyId((short)31).addId(ForgeVersion.MC_1_16_5, 32).setSupportedVersion(ForgeVersion.MC_1_16_5).build();
        S = PotionEntry.builder().setName(stringArray[38]).setLegacyId((short)32).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        x = PotionEntry.builder().setName(stringArray[33]).setLegacyId((short)33).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        w = PotionEntry.builder().setName(stringArray[9]).setLegacyId((short)34).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        N = PotionEntry.builder().setName(stringArray[25]).setLegacyId((short)35).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        l = PotionEntry.builder().setName(stringArray[1]).setLegacyId((short)36).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        e = PotionEntry.builder().setName(stringArray[27]).setLegacyId((short)37).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        O = PotionEntry.builder().setName(stringArray[16]).setLegacyId((short)38).setSupportedVersion(ForgeVersion.MC_1_21_0).build();
        b = new LinkedHashMap<Short, PotionEntry>();
        V = new LinkedHashMap<Integer, PotionEntry>();
        F = false;
    }
}
