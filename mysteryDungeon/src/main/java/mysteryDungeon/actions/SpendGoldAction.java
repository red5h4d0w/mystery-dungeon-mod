package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.powers.FreeSpendingThisTurnPower;

public class SpendGoldAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());

    public int goldAmount;

    public SpendGoldAction(int goldAmount) {
        this.goldAmount = goldAmount;
    }
  
    public void update() {
        if (AbstractDungeon.player.hasPower(FreeSpendingThisTurnPower.POWER_ID)) {
            addToTop(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, AbstractDungeon.player.getPower(FreeSpendingThisTurnPower.POWER_ID), 1));
            isDone = true;
            return;
        }
        CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gold -= goldAmount;
        isDone = true;
    }
}
