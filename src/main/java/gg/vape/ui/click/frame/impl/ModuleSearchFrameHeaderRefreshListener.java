package gg.vape.ui.click.frame.impl;

import gg.vape.ui.click.component.GuiRefreshListener;
import gg.vape.ui.click.frame.impl.ModuleSearchFrameHeader;

public class ModuleSearchFrameHeaderRefreshListener
implements GuiRefreshListener {
    final ModuleSearchFrameHeader Q;

    public ModuleSearchFrameHeaderRefreshListener(ModuleSearchFrameHeader moduleSearchFrameHeader) {
        this.Q = moduleSearchFrameHeader;
    }

    @Override
    public void onRefresh() {
        this.Q.l$src$V$11ec2hr();
    }
}
