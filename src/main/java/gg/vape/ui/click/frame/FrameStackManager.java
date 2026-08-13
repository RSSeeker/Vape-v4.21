package gg.vape.ui.click.frame;

import gg.vape.ui.click.frame.Frame;
import gg.vape.ui.click.frame.PopupFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FrameStackManager {
    private HashMap<Frame, Boolean> X;
    private static String m;
    private List<Frame> I = new ArrayList<Frame>();

    public static String i() {
        return m;
    }

    public void A() {
    }

    public void m(Frame frame) {
        this.I.remove(frame);
    }

    public void R(Frame frame, Frame frame2) {
        if (!this.I.contains(frame) || !this.I.contains(frame2)) {
            return;
        }
        if (frame2 instanceof PopupFrame) {
            int n = this.I.indexOf(frame);
            PopupFrame popupFrame = (PopupFrame)frame2;
            ArrayList<PopupFrame> arrayList = popupFrame.Q$src$Lgg_vape_ui_click_frame_Frame_$1y8ivjg().s$src$Ljava_util_ArrayList_$1a2240q();
            for (int i = 0; i < arrayList.size(); ++i) {
                PopupFrame popupFrame2 = arrayList.get(i);
                this.I.remove(popupFrame2);
                if (n + i + 1 > this.I.size()) {
                    this.I.add(popupFrame2);
                    continue;
                }
                this.I.add(n + i + 1, popupFrame2);
            }
        } else {
            this.I.remove(frame2);
            int n = this.I.indexOf(frame);
            this.I.add(n + 1, frame2);
        }
    }

    public static void Y(String string) {
        m = string;
    }


    public void q(Frame frame) {
        if (this.I.contains(frame)) {
            return;
        }
        this.I.add(frame);
    }

    public List<Frame> Y() {
        return this.I;
    }

    public void v(Frame frame) {
        if (this.Y().contains(frame)) {
            this.m(frame);
            this.q(frame);
        }
    }

    public FrameStackManager() {
        this.X = new HashMap();
    }

    static {
        if (FrameStackManager.i() == null) {
            FrameStackManager.Y("uGuaM");
        }
    }
}

