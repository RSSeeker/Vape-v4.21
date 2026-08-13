package gg.vape.friend.ui;

import gg.vape.Vape;
import gg.vape.friend.Enemy;
import gg.vape.friend.ui.EnemySettingsFrame;
import gg.vape.ui.click.component.GuiClickListener;

class EnemySettingsRemoveEntryClickHandler
implements GuiClickListener {
    final Enemy enemy;
    final EnemySettingsFrame frame;

    @Override
    public void onPrimaryClick() {
        Vape.INSTANCE.saveAndStop();
        Vape.INSTANCE.getEnemyManager().removeEnemy(this.enemy);
        this.frame.refreshEntries();
    }

    EnemySettingsRemoveEntryClickHandler(EnemySettingsFrame enemySettingsFrame, Enemy enemy) {
        this.frame = enemySettingsFrame;
        this.enemy = enemy;
    }
}
