package gg.vape.ui.click.component;

public interface GuiClickListener {
    public void onPrimaryClick();

    default public void onSecondaryClick() {
    }
}
