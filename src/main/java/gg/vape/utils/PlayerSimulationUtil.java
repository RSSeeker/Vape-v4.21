package gg.vape.utils;

import gg.vape.mapping.MappedClasses;
import gg.vape.module.render.hud.FreeLookHudModule;
import gg.vape.utils.MathUtil;
import gg.vape.utils.datas.BlockData;
import gg.vape.wrapper.impl.BlockPos;
import gg.vape.wrapper.impl.BlockState;
import gg.vape.wrapper.impl.EnchantmentHelper;
import gg.vape.wrapper.impl.EntityOtherPlayerMP;
import gg.vape.wrapper.impl.EntityPlayer;
import gg.vape.wrapper.impl.ForgeVersion;
import gg.vape.wrapper.impl.Minecraft;
import gg.vape.wrapper.impl.World;

public class PlayerSimulationUtil {
    public static EntityOtherPlayerMP I(EntityPlayer entityPlayer) {
        EntityOtherPlayerMP entityOtherPlayerMP = EntityOtherPlayerMP.create(Minecraft.theWorld(), entityPlayer.c$src$Lgg_vape_wrapper_impl_GameProfile_$ir8937());
        entityOtherPlayerMP.M(entityPlayer, true);
        PlayerSimulationUtil.s(entityOtherPlayerMP, entityPlayer);
        return entityOtherPlayerMP;
    }

    public static void u(EntityPlayer entityPlayer, double d, double d2, double d3) {
        int n;
        int n2 = MathUtil.floor(d);
        int n3 = MathUtil.floor(d2);
        int n4 = MathUtil.floor(d3);
        int n5 = Math.max(Math.round(entityPlayer.Y()), 1);
        double d4 = d - (double)n2;
        double d5 = d3 - (double)n4;
        boolean bl = true;
        for (n = 0; n < n5; ++n) {
            if (PlayerSimulationUtil.j(n2, n3 + n, n4)) continue;
            bl = false;
        }
        if (bl) {
            n = -1;
            double d6 = 9999.0;
            if (PlayerSimulationUtil.g(n2 - 1, n3, n4, n5) && d4 < d6) {
                d6 = d4;
                n = 0;
            }
            if (PlayerSimulationUtil.g(n2 + 1, n3, n4, n5) && 1.0 - d4 < d6) {
                d6 = 1.0 - d4;
                n = 1;
            }
            if (PlayerSimulationUtil.g(n2, n3, n4 - 1, n5) && d5 < d6) {
                d6 = d5;
                n = 4;
            }
            if (PlayerSimulationUtil.g(n2, n3, n4 + 1, n5) && 1.0 - d5 < d6) {
                d6 = 1.0 - d5;
                n = 5;
            }
            float f = 0.1f;
            if (n == 0) {
                entityPlayer.r(-f);
            }
            if (n == 1) {
                entityPlayer.r(f);
            }
            if (n == 4) {
                entityPlayer.i((double)(-f));
            }
            if (n == 5) {
                entityPlayer.i((double)f);
            }
        }
    }

    public static void t(EntityPlayer entityPlayer, boolean bl) {
        if (Math.abs(entityPlayer.t()) < 0.005) {
            entityPlayer.r(0.0);
        }
        if (Math.abs(entityPlayer.q()) < 0.005) {
            entityPlayer.k(0.0);
        }
        if (Math.abs(entityPlayer.T()) < 0.005) {
            entityPlayer.i(0.0);
        }
        if (bl) {
            entityPlayer.k$src$V$5315b7(entityPlayer.N$src$F$14ypudi() * 0.98f);
            entityPlayer.M(entityPlayer.F() * 0.98f);
        }
        PlayerSimulationUtil.p(entityPlayer, entityPlayer.z() - (double)entityPlayer.f$src$F$fst3ac() * 0.35, entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() + 0.5, entityPlayer.h() + (double)entityPlayer.f$src$F$fst3ac() * 0.35);
        PlayerSimulationUtil.p(entityPlayer, entityPlayer.z() - (double)entityPlayer.f$src$F$fst3ac() * 0.35, entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() + 0.5, entityPlayer.h() - (double)entityPlayer.f$src$F$fst3ac() * 0.35);
        PlayerSimulationUtil.p(entityPlayer, entityPlayer.z() + (double)entityPlayer.f$src$F$fst3ac() * 0.35, entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() + 0.5, entityPlayer.h() - (double)entityPlayer.f$src$F$fst3ac() * 0.35);
        PlayerSimulationUtil.p(entityPlayer, entityPlayer.z() + (double)entityPlayer.f$src$F$fst3ac() * 0.35, entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY() + 0.5, entityPlayer.h() + (double)entityPlayer.f$src$F$fst3ac() * 0.35);
        PlayerSimulationUtil.g(entityPlayer, entityPlayer.N$src$F$14ypudi(), entityPlayer.F());
    }

    private static boolean j(int n, int n2, int n3) {
        return Minecraft.theWorld().getBlockByPos(n, n2, n3).X(null);
    }

    private static boolean E(BlockPos blockPos) {
        World world = Minecraft.thePlayer().getWorld();
        BlockState blockState = null;
        if (ForgeVersion.MC_1_12_2.d()) {
            blockState = world.getBlockState(blockPos);
        }
        BlockState blockState2 = null;
        if (ForgeVersion.MC_1_12_2.d()) {
            blockState2 = world.getBlockState(blockPos.X$src$Lgg_vape_wrapper_impl_BlockPos_$jlnp6b());
        }
        return !world.getBlockState(blockPos).getBlock().X(blockState) && !world.getBlockState(blockPos.X$src$Lgg_vape_wrapper_impl_BlockPos_$jlnp6b()).getBlock().X(blockState2);
    }

    private static boolean g(int n, int n2, int n3, int n4) {
        for (int i = 0; i < n4; ++i) {
            if (!PlayerSimulationUtil.j(n, n2 + i, n3 + 1)) continue;
            return false;
        }
        return true;
    }


    public static void p(EntityPlayer entityPlayer, double d, double d2, double d3) {
        if (ForgeVersion.MC_1_7_10.L()) {
            PlayerSimulationUtil.u(entityPlayer, d, d2, d3);
            return;
        }
        BlockPos blockPos = BlockPos.D(d, d2, d3);
        double d4 = d - (double)blockPos.getX();
        double d5 = d3 - (double)blockPos.getZ();
        if (!PlayerSimulationUtil.E(blockPos)) {
            int n = -1;
            double d6 = 9999.0;
            if (PlayerSimulationUtil.E(blockPos.j()) && d4 < d6) {
                d6 = d4;
                n = 0;
            }
            if (PlayerSimulationUtil.E(blockPos.G()) && 1.0 - d4 < d6) {
                d6 = 1.0 - d4;
                n = 1;
            }
            if (PlayerSimulationUtil.E(blockPos.o$src$Lgg_vape_wrapper_impl_BlockPos_$10np7re()) && d5 < d6) {
                d6 = d5;
                n = 4;
            }
            if (PlayerSimulationUtil.E(blockPos.y()) && 1.0 - d5 < d6) {
                d6 = 1.0 - d5;
                n = 5;
            }
            float f = 0.1f;
            if (n == 0) {
                entityPlayer.r(-f);
            }
            if (n == 1) {
                entityPlayer.r(f);
            }
            if (n == 4) {
                entityPlayer.i((double)(-f));
            }
            if (n == 5) {
                entityPlayer.i((double)f);
            }
        }
    }

    public static EntityOtherPlayerMP y() {
        return PlayerSimulationUtil.I(Minecraft.thePlayer());
    }

    public static void g(EntityPlayer entityPlayer, float f, float f2) {
        World world = Minecraft.thePlayer().getWorld();
        entityPlayer.z(false);
        if (!entityPlayer.h$src$Z$ftwoya() || entityPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
            if (!entityPlayer.Q$src$Z$fh9faz() || entityPlayer.C$src$Lgg_vape_wrapper_impl_ModelPlayer_$19uhx86().isFlying()) {
                boolean bl;
                float f3 = 0.91f;
                if (entityPlayer.b$src$Z$fqlxe4()) {
                    if (ForgeVersion.MC_1_7_10.Y()) {
                        f3 = world.getBlockState(BlockPos.create(MathUtil.floor(entityPlayer.z()), MathUtil.floor(entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY()) - 1, MathUtil.floor(entityPlayer.h()))).getBlock().c() * 0.91f;
                    } else {
                        BlockData blockData = new BlockData(MathUtil.floor(entityPlayer.z()), MathUtil.floor(entityPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY()) - 1, MathUtil.floor(entityPlayer.h()));
                        f3 = world.getBlockByPos(blockData.D(), blockData.B(), blockData.G()).c() * 0.91f;
                    }
                }
                float f4 = 0.16277136f / (f3 * f3 * f3);
                float f5 = entityPlayer.b$src$Z$fqlxe4() ? entityPlayer.C$src$F$1i1kt1e() * f4 : entityPlayer.y$src$F$15mczw1();
                if (ForgeVersion.MC_1_12_2.v()) {
                    entityPlayer.i(f, f2, f5);
                }
                f3 = 0.91f;
                if (entityPlayer.b$src$Z$fqlxe4()) {
                    if (ForgeVersion.MC_1_7_10.Y()) {
                        f3 = world.getBlockState(BlockPos.create(MathUtil.floor(entityPlayer.z()), MathUtil.floor(entityPlayer.u$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$kogbsu().getMinY()) - 1, MathUtil.floor(entityPlayer.h()))).getBlock().c() * 0.91f;
                    } else {
                        BlockData blockData = new BlockData(MathUtil.floor(entityPlayer.z()), MathUtil.floor(entityPlayer.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().getMinY()) - 1, MathUtil.floor(entityPlayer.h()));
                        f3 = world.getBlockByPos(blockData.D(), blockData.B(), blockData.G()).c() * 0.91f;
                    }
                }
                if (entityPlayer.S$src$Z$151gttj()) {
                    boolean bl2;
                    float f6 = 0.15f;
                    entityPlayer.r(MathUtil.clamp(entityPlayer.t(), (double)(-f6), (double)f6));
                    entityPlayer.i(MathUtil.clamp(entityPlayer.T(), (double)(-f6), (double)f6));
                    entityPlayer.U(0.0f);
                    if (entityPlayer.q() < -0.15) {
                        entityPlayer.k(-0.15);
                    }
                    if ((bl2 = entityPlayer.P()) && entityPlayer.q() < 0.0) {
                        entityPlayer.k(0.0);
                    }
                }
                entityPlayer.b(entityPlayer.t(), entityPlayer.q(), entityPlayer.T());
                if (entityPlayer.r() && entityPlayer.S$src$Z$151gttj()) {
                    entityPlayer.k(0.2);
                }
                if (ForgeVersion.MC_1_7_10.Y()) {
                    bl = !world.j$src$Z$11aji0a(BlockPos.create((int)entityPlayer.z(), 0, (int)entityPlayer.h())) || !world.j(BlockPos.create((int)entityPlayer.z(), 0, (int)entityPlayer.h())).F();
                } else {
                    boolean bl3 = bl = !world.M((int)entityPlayer.z(), 0, (int)entityPlayer.h()) || !world.P((int)entityPlayer.z(), (int)entityPlayer.h()).F();
                }
                if (world.I() && bl) {
                    if (entityPlayer.N() > 0.0) {
                        entityPlayer.k(-0.1);
                    } else {
                        entityPlayer.k(0.0);
                    }
                } else {
                    entityPlayer.k(entityPlayer.q() - 0.08);
                }
                entityPlayer.k(entityPlayer.q() * (double)0.98f);
                entityPlayer.r(entityPlayer.t() * (double)f3);
                entityPlayer.i(entityPlayer.T() * (double)f3);
            } else {
                double d = entityPlayer.N();
                if (ForgeVersion.MC_1_12_2.v()) {
                    entityPlayer.i(f, f2, 0.02f);
                }
                entityPlayer.b(entityPlayer.t(), entityPlayer.q(), entityPlayer.T());
                entityPlayer.r(entityPlayer.t() * 0.5);
                entityPlayer.k(entityPlayer.q() * 0.5);
                entityPlayer.i(entityPlayer.T() * 0.5);
                entityPlayer.k(entityPlayer.q() - 0.02);
                if (entityPlayer.r() && entityPlayer.i$src$Z$avhpwd(entityPlayer.t(), entityPlayer.q() + (double)0.6f - entityPlayer.N() + d, entityPlayer.T())) {
                    entityPlayer.k((double)0.3f);
                }
            }
        } else {
            float f7;
            double d = entityPlayer.N();
            float f8 = 0.8f;
            float f9 = 0.02f;
            float f10 = f7 = ForgeVersion.MC_1_7_10.Y() ? (float)EnchantmentHelper.y(entityPlayer) : 0.0f;
            if (f7 > 3.0f) {
                f7 = 3.0f;
            }
            if (!entityPlayer.b$src$Z$fqlxe4()) {
                f7 *= 0.5f;
            }
            if (f7 > 0.0f) {
                f8 += (0.54600006f - f8) * f7 / 3.0f;
                f9 += (entityPlayer.C$src$F$1i1kt1e() * 1.0f - f9) * f7 / 3.0f;
            }
            if (ForgeVersion.MC_1_12_2.v()) {
                entityPlayer.i(f, f2, f9);
            }
            entityPlayer.b(entityPlayer.t(), entityPlayer.q(), entityPlayer.T());
            entityPlayer.r(entityPlayer.t() * (double)f8);
            entityPlayer.k(entityPlayer.q() * (double)0.8f);
            entityPlayer.i(entityPlayer.T() * (double)f8);
            entityPlayer.k(entityPlayer.q() - 0.02);
            if (entityPlayer.r() && entityPlayer.i$src$Z$avhpwd(entityPlayer.t(), entityPlayer.q() + (double)0.6f - entityPlayer.N() + d, entityPlayer.T())) {
                entityPlayer.k((double)0.3f);
            }
        }
    }

    public static void I$src$V$imkltv(EntityPlayer entityPlayer) {
        PlayerSimulationUtil.t(entityPlayer, false);
    }

    public static void s(EntityPlayer entityPlayer, EntityPlayer entityPlayer2) {
        entityPlayer.H(entityPlayer2.z());
        entityPlayer.u(entityPlayer2.N());
        entityPlayer.l(entityPlayer2.h());
        entityPlayer.n(entityPlayer2.f());
        entityPlayer.w(entityPlayer2.H());
        entityPlayer.A(entityPlayer2.R());
        entityPlayer.C(entityPlayer2.M());
        entityPlayer.L(entityPlayer2.W());
        entityPlayer.s(entityPlayer2.m$src$D$fwnne5());
        entityPlayer.D(entityPlayer2.R$src$Lgg_vape_wrapper_impl_AxisAlignedBB_$r19dfl().expand(0.0, 0.0, 0.0));
        entityPlayer.r(entityPlayer2.t());
        entityPlayer.k(entityPlayer2.q());
        entityPlayer.i(entityPlayer2.T());
        if (FreeLookHudModule.isActive() && entityPlayer2.isInstance(MappedClasses.z5)) {
            entityPlayer.H(FreeLookHudModule.getSavedPitch());
            entityPlayer.C(FreeLookHudModule.getSavedYaw());
        } else {
            entityPlayer.H(entityPlayer2.J());
            entityPlayer.D(entityPlayer2.j());
            entityPlayer.C(entityPlayer2.V());
            entityPlayer.l(entityPlayer2.D());
        }
        entityPlayer.M(entityPlayer2.F());
        entityPlayer.k$src$V$5315b7(entityPlayer2.N$src$F$14ypudi());
        entityPlayer.R(entityPlayer2.B$src$Z$f90iek());
        entityPlayer.I(entityPlayer2.C$src$F$1i1kt1e());
        if (ForgeVersion.MC_1_17.v()) {
            entityPlayer.t(entityPlayer2.y$src$F$15mczw1());
        }
        entityPlayer.L(entityPlayer2.B$src$I$14s4bbr());
        if (ForgeVersion.MC_1_7_10.L()) {
            entityPlayer.i(entityPlayer2.c$src$F$fr5pi9());
        }
        if (ForgeVersion.MC_1_20_6.v()) {
            entityPlayer.K(entityPlayer2.u());
        }
    }
}

