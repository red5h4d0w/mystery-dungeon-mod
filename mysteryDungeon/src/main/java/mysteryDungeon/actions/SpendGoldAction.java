package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.interfaces.onSpendGoldInterface;
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
        for (AbstractRelic relic: AbstractDungeon.player.relics) {
            if(relic instanceof onSpendGoldInterface) {
                ((onSpendGoldInterface)relic).onSpendGold(goldAmount);
            }
        }
        // Trigger every relic and power that implement the onSpendGoldInterface
        for (AbstractPower power: AbstractDungeon.player.powers) {
            if(power instanceof onSpendGoldInterface) {
                ((onSpendGoldInterface)power).onSpendGold(goldAmount);
            }
        }
        for (AbstractPower power: AbstractDungeon.getMonsters().monsters.stream()
            .filter( monster -> !monster.isDeadOrEscaped())
            .flatMap( monster -> monster.powers.stream())
            .collect(Collectors.toCollection(ArrayList::new))) {
                if(power instanceof onSpendGoldInterface) {
                    ((onSpendGoldInterface)power).onSpendGold(goldAmount);
                }
        }
        CardCrawlGame.sound.play("GOLD_JINGLE");
        AbstractDungeon.player.gold -= goldAmount;
        Pokemon.goldSpentThisCombat += goldAmount;
        isDone = true;
    }
}
