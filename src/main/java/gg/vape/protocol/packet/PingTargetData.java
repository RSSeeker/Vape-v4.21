package gg.vape.protocol.packet;

import gg.vape.protocol.ZeusPacketBuffer;
import gg.vape.protocol.packet.PingTargetKind;
import org.jetbrains.annotations.Nullable;

public class PingTargetData {
    @Nullable
    private Double A;
    private final PingTargetKind r;
    @Nullable
    private Long w;
    @Nullable
    private Double M;
    @Nullable
    private Integer z;
    @Nullable
    private Double P;
    static final boolean b;
    @Nullable
    private Integer f;
    @Nullable
    private Integer Z;
    @Nullable
    private Integer o;


    PingTargetData(int n, int n2, int n3) {
        this.r = PingTargetKind.BLOCK;
        this.o = n;
        this.z = n2;
        this.f = n3;
    }

    @Nullable
    public Double W() {
        return this.P;
    }

    @Nullable
    public Integer u() {
        return this.z;
    }

    @Nullable
    public Long t() {
        return this.w;
    }

    PingTargetData(@Nullable Long l, int n, double d, double d2, double d3) {
        this.r = PingTargetKind.ENTITY;
        this.w = l;
        this.Z = n;
        this.P = d;
        this.A = d2;
        this.M = d3;
    }

    @Nullable
    public Double w() {
        return this.A;
    }

    public static PingTargetData a(@Nullable Long l, int n, double d, double d2, double d3) {
        return new PingTargetData(l, n, d, d2, d3);
    }

    @Nullable
    public Integer e() {
        return this.Z;
    }

    public static PingTargetData C(double d, double d2, double d3) {
        return new PingTargetData(d, d2, d3);
    }

    public PingTargetData(ZeusPacketBuffer zeusPacketBuffer) {
        this.r = zeusPacketBuffer.readEnum(PingTargetKind.class);
        if (this.r == PingTargetKind.POSITION) {
            this.P = zeusPacketBuffer.readDouble();
            this.A = zeusPacketBuffer.readDouble();
            this.M = zeusPacketBuffer.readDouble();
        } else if (this.r == PingTargetKind.BLOCK) {
            this.o = zeusPacketBuffer.readInt();
            this.z = zeusPacketBuffer.readInt();
            this.f = zeusPacketBuffer.readInt();
        } else if (this.r == PingTargetKind.ENTITY) {
            this.Z = zeusPacketBuffer.readInt();
            this.w = zeusPacketBuffer.readLong();
            if (this.w == -1L) {
                this.w = null;
            }
            this.P = zeusPacketBuffer.readDouble();
            this.A = zeusPacketBuffer.readDouble();
            this.M = zeusPacketBuffer.readDouble();
        }
    }

    @Nullable
    public Integer y() {
        return this.o;
    }

    @Nullable
    public Integer r() {
        return this.f;
    }

    public void n(ZeusPacketBuffer zeusPacketBuffer) {
        zeusPacketBuffer.writeEnum(this.r);
        if (this.r == PingTargetKind.POSITION) {
            if (!(b || this.P != null && this.A != null && this.M != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeDouble(this.P);
            zeusPacketBuffer.writeDouble(this.A);
            zeusPacketBuffer.writeDouble(this.M);
        } else if (this.r == PingTargetKind.BLOCK) {
            if (!(b || this.o != null && this.z != null && this.f != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeInt(this.o);
            zeusPacketBuffer.writeInt(this.z);
            zeusPacketBuffer.writeInt(this.f);
        } else if (this.r == PingTargetKind.ENTITY) {
            if (!(b || this.Z != null && this.P != null && this.A != null && this.M != null)) {
                throw new AssertionError();
            }
            zeusPacketBuffer.writeInt(this.Z);
            ZeusPacketBuffer zeusPacketBuffer2 = zeusPacketBuffer;
            long l = this.w != null ? this.w : -1L;
            zeusPacketBuffer2.writeLong(l);
            zeusPacketBuffer.writeDouble(this.P);
            zeusPacketBuffer.writeDouble(this.A);
            zeusPacketBuffer.writeDouble(this.M);
        }
    }

    @Nullable
    public Double L() {
        return this.M;
    }

    public PingTargetKind K() {
        return this.r;
    }

    public static PingTargetData Y(int n, int n2, int n3) {
        return new PingTargetData(n, n2, n3);
    }

    PingTargetData(double d, double d2, double d3) {
        this.r = PingTargetKind.POSITION;
        this.P = d;
        this.A = d2;
        this.M = d3;
    }

    static {
        boolean bl;
        b = bl = !PingTargetData.class.desiredAssertionStatus();
    }
}

