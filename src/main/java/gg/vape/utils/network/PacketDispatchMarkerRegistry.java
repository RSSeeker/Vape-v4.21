package gg.vape.utils.network;

import gg.vape.wrapper.impl.Packet;
import java.util.ArrayList;
import java.util.List;

public class PacketDispatchMarkerRegistry {
    private static List<Object> s;
    private static List<Object> y;

    public static void q(Packet packet) {
        s.add(packet.getObject());
    }

    public static void I(Packet packet) {
        s.remove(packet.getObject());
    }

    public static void p(Packet packet) {
        y.remove(packet.getObject());
    }

    public static boolean J(Packet packet) {
        return y.contains(packet.getObject());
    }

    public static boolean S(Packet packet) {
        return s.contains(packet.getObject());
    }

    public static void u(Packet packet) {
        y.add(packet.getObject());
    }

    static {
        y = new ArrayList<Object>();
        s = new ArrayList<Object>();
    }
}

