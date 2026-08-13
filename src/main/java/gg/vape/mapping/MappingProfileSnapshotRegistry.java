package gg.vape.mapping;

import gg.vape.Vape;
import gg.vape.mapping.MappingField;
import gg.vape.mapping.MappingMemberSnapshot;
import gg.vape.mapping.MappingMethod;
import gg.vape.utils.StringPair;
import java.util.ArrayList;

public class MappingProfileSnapshotRegistry {
    private static int X;
    private static int H;
    private static ArrayList<MappingMemberSnapshot> k;
    private static int p;
    private static ArrayList<StringPair> W;
    private static ArrayList<MappingMemberSnapshot> y;
    private static int[] O;
    private static int v;
    private static int N;

    public static void h() {
    }

    public static void Y() {
        W.clear();
        v = 0;
    }

    public static void X(MappingMethod mappingMethod) {
        if (!Vape.INSTANCE.isMappingsRemapped() && !Vape.INSTANCE.isForgeRemapInactive()) {
            return;
        }
        MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(mappingMethod.getMappingOwner(), mappingMethod.getOwnerClass(), mappingMethod.getOriginalName(), mappingMethod.getDeclaredReturnType(), mappingMethod.getDeclaredParameterTypes(), mappingMethod.getRuntimeName(), mappingMethod.getResolvedReturnType(), mappingMethod.getResolvedParameterTypes(), mappingMethod.isMappedMember());
        k.add(mappingMemberSnapshot);
        ++H;
    }

    public static void y(int[] nArray) {
        O = nArray;
    }

    public static void j() {
        ++N;
    }


    public static int[] T() {
        return O;
    }

    static {
        v = 0;
        p = 0;
        N = 0;
        H = 0;
        X = 0;
        W = new ArrayList();
        y = new ArrayList();
        k = new ArrayList();
        MappingProfileSnapshotRegistry.y(new int[5]);
    }

    public static void y() {
        p = 0;
        N = 0;
        H = 0;
        X = 0;
    }

    public static void i() {
        ++v;
    }

    public static void n(MappingField mappingField) {
        if (!Vape.INSTANCE.isMappingsRemapped() && !Vape.INSTANCE.isForgeRemapInactive()) {
            return;
        }
        MappingMemberSnapshot mappingMemberSnapshot = new MappingMemberSnapshot(mappingField.getMappingOwner(), mappingField.getOwnerClass(), mappingField.getOriginalName(), mappingField.getDeclaredFieldType(), null, mappingField.getRuntimeName(), mappingField.getResolvedFieldType(), null, mappingField.isMappedMember());
        y.add(mappingMemberSnapshot);
        ++p;
    }

    public static void X() {
    }

    public static void O() {
        ++X;
    }

    public static void v(String string, String string2) {
        W.add(new StringPair(string, string2));
    }
}

