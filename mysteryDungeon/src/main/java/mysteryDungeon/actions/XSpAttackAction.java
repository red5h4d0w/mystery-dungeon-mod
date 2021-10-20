package mysteryDungeon.actions;



import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;

public class XSpAttackAction extends AbstractGameAction {
    public XSpAttackAction(int potency) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = potency;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
                for (int j = 0; j < amount; j++)
                AbstractDungeon.player.channelOrb(new Lightning()); 
            } 
            if (Settings.FAST_MODE) {
                isDone = true;
                return;
            } 
        } 
        tickDuration();
    }
}