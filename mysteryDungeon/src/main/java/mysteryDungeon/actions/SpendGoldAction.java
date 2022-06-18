package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import mysteryDungeon.MysteryDungeon;

public class SpendGoldAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());

    public int goldAmount;

    public SpendGoldAction(int goldAmount) {
        this.goldAmount = goldAmount;
    }
  
    public void update() {
        CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gold -= goldAmount;
        isDone = true;
    }
}
