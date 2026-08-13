package gg.vape.ui.unmap;

import com.google.gson.JsonObject;
import gg.vape.Vape;
import gg.vape.utils.Base64Util;
import gg.vape.utils.BlockUtil;
import gg.vape.utils.MutableColor;
import java.awt.Color;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;
import org.jetbrains.annotations.Nullable;

public class SearchBlock {
    private final AtomicBoolean D = new AtomicBoolean(true);
    private MutableColor Y;
    private final AtomicBoolean l = new AtomicBoolean(false);
    private String P;
    private int K = -1;
    private static int[] N;
    @Nullable
    private Predicate<Character> R;
    private int H;

    public JsonObject I() {
        JsonObject jsonObject = new JsonObject();
        String string = "b64:" + Base64Util.encodeUtf8Base64(this.P);
        jsonObject.addProperty("blockname", string);
        jsonObject.addProperty("color2", (Number)this.Y.l());
        jsonObject.addProperty("enabled", Boolean.valueOf(this.D.get()));
        jsonObject.addProperty("tracersEnabled", Boolean.valueOf(this.l.get()));
        return jsonObject;
    }

    private void o(String string) {
        try {
            this.K = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
    }

    public void M(boolean bl) {
        this.D.set(bl);
    }

    public void H(boolean bl) {
        this.l.set(bl);
    }

    public Color B() {
        return this.Y;
    }

    public boolean b(int n, int n2) {
        if (!this.D.get()) {
            return false;
        }
        if (this.H == -1) {
            return false;
        }
        if (this.R != null) {
            return this.R.test(Character.valueOf(BlockUtil.t(n, n2)));
        }
        return this.H == n && (this.K == -1 || this.K == n2 || n2 == -1);
    }

    @Nullable
    public Predicate<Character> E() {
        return this.R;
    }

    private void X() {
        String string = this.P;
        Predicate<Character> predicate = Vape.INSTANCE.getItemHelper().findCharacterRule(string);
        if (predicate != null) {
            this.R = predicate;
            return;
        }
        if (string.contains(":")) {
            String[] stringArray = string.split(":");
            if (stringArray.length == 2) {
                this.b(stringArray[0]);
                this.o(stringArray[1]);
            }
        } else {
            this.b(string);
        }
    }

    public void c(int n) {
        this.Y = new MutableColor(n);
    }

    public int M() {
        return this.H;
    }

    private void b(String string) {
        int n = 0;
        int n2 = -1;
        try {
            n = Integer.parseInt(string);
        }
        catch (NumberFormatException numberFormatException) {
            int[] nArray = BlockUtil.r(string);
            n = nArray[0];
            n2 = nArray[1];
        }
        this.H = n;
        this.K = n2;
    }

    public AtomicBoolean I$src$Ljava_util_concurrent_atomic_AtomicBoolean_$10pq3bz() {
        return this.D;
    }

    public static void m(int[] nArray) {
        N = nArray;
    }

    public SearchBlock(String string, int n) {
        this.Y = new MutableColor(n);
        this.P = string;
        this.X();
    }

    public String d() {
        return this.P;
    }

    public boolean T() {
        return this.D.get();
    }

    public static int[] J() {
        return N;
    }

    public AtomicBoolean x() {
        return this.l;
    }

    public int i() {
        return this.K;
    }

    public boolean W() {
        return this.l.get();
    }

    static {
        SearchBlock.m(null);
    }

    public SearchBlock(JsonObject jsonObject) {
        if (jsonObject.get("blockname") != null && !jsonObject.get("blockname").isJsonNull()) {
            this.P = jsonObject.get("blockname").getAsString();
            if (this.P.startsWith("b64:")) {
                this.P = Base64Util.decodeUtf8Base64(this.P.split(":")[1]);
            }
            this.X();
        }
        if (jsonObject.get("color") != null && !jsonObject.get("color").isJsonNull()) {
            this.Y = new MutableColor(jsonObject.get("color").getAsInt());
        }
        if (jsonObject.get("color2") != null && !jsonObject.get("color2").isJsonNull()) {
            Color color = new Color(jsonObject.get("color2").getAsInt());
            this.Y = new MutableColor(color);
        }
        if (jsonObject.get("enabled") != null && !jsonObject.get("enabled").isJsonNull()) {
            this.D.set(jsonObject.get("enabled").getAsBoolean());
        }
        if (jsonObject.get("tracersEnabled") != null && !jsonObject.get("tracersEnabled").isJsonNull()) {
            this.l.set(jsonObject.get("tracersEnabled").getAsBoolean());
        }
    }

    private static NumberFormatException a(NumberFormatException numberFormatException) {
        return numberFormatException;
    }

    public JsonObject com_google_gson_JsonObject_I() {
        return this.I();
    }
}

