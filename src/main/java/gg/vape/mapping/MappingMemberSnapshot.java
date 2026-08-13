package gg.vape.mapping;

import gg.vape.mapping.MappedClasses;
import gg.vape.mapping.Mapping;

public class MappingMemberSnapshot {
    private Class[] t;
    private Class g;
    private Class I;
    private boolean J;
    private String Z;
    private Mapping c;
    private Class[] n;
    private String b;
    private Class l;


    public String a() {
        return MappedClasses.b(this.I);
    }

    public String P() {
        if (this.g == null) {
            return "null";
        }
        if (this.t == null) {
            return MappedClasses.b(this.g);
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.t) {
            String string = MappedClasses.b(clazz);
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(MappedClasses.b(this.g));
        return stringBuilder.toString();
    }

    public String p() {
        if (this.l == null) {
            return "null";
        }
        if (this.n == null) {
            return MappedClasses.b(this.l);
        }
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.n) {
            String string = MappedClasses.b(clazz);
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(MappedClasses.b(this.l));
        return stringBuilder.toString();
    }

    public MappingMemberSnapshot(Mapping mapping, Class clazz, String string, Class clazz2, Class[] classArray, String string2, Class clazz3, Class[] classArray2, boolean bl) {
        this.c = mapping;
        this.I = clazz;
        this.Z = string;
        this.g = clazz2;
        this.t = classArray;
        this.b = string2;
        this.l = clazz3;
        this.n = classArray2;
        this.J = bl;
    }

    public String A() {
        StringBuilder stringBuilder = new StringBuilder("(");
        for (Class clazz : this.t) {
            String string = clazz.getName();
            stringBuilder.append(string).append(", ");
        }
        if (stringBuilder.length() > 1) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 2);
        }
        stringBuilder.append(")").append(this.g.getName());
        return stringBuilder.toString();
    }
}

