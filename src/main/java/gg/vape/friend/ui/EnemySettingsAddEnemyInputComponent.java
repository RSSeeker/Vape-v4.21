package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.Enemy;
import gg.vape.friend.ui.EnemySettingsFrame;
import gg.vape.module.none.ClientSettings;
import gg.vape.ui.click.component.TextInputComponentBase;

public class EnemySettingsAddEnemyInputComponent
extends TextInputComponentBase {
    @Override
    public double C() {
        return 20.0;
    }

    @Override
    public double getAvailableTextWidth() {
        return this.A() - 35.0;
    }


    public EnemySettingsAddEnemyInputComponent(String string) {
        super(string);
        this.setShowDisabledOverlay(false);
        this.actionButtonColor = EnemySettingsAddEnemyInputComponent.J.d;
    }

    @Override
    public void submit() {
        if (!this.hasNonBlankText()) {
            this.setText("");
            return;
        }
        String[] stringArray = this.getText().split(" ");
        String string = stringArray[0];
        String string2 = stringArray.length > 1 ? stringArray[1] : stringArray[0];
        Vape.INSTANCE.getEnemyManager().addEnemy(new Enemy(string, string2));
        ClientSettings.getFrame(EnemySettingsFrame.class).refreshEntries();
        this.setText("");
    }

    @Override
    public double x() {
        return 110.0;
    }
}
