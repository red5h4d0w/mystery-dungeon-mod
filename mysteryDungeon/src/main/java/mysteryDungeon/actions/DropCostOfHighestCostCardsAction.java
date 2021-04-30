package mysteryDungeon.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import mysteryDungeon.MysteryDungeon;

public class DropCostOfHighestCostCardsAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());

    public DropCostOfHighestCostCardsAction(AbstractCreature target, int amount) {
        this.target = target;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = amount;
    }
  
    public void update() {
        if(target instanceof AbstractPlayer)
        {
            ArrayList<AbstractCard> highestCostCards = (ArrayList<AbstractCard>)AbstractDungeon.player.hand.group.clone();
            highestCostCards.sort((c1, c2) -> c1.costForTurn - c2.costForTurn);
            for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
                if (i.card != null) {
                    highestCostCards.remove(i.card);
                } 
            }
            if(!highestCostCards.isEmpty())
            {
                for(AbstractCard randomCard: highestCostCards.subList((highestCostCards.size()-amount)>0?highestCostCards.size()-amount:0, highestCostCards.size()))
                {
                    if(randomCard.cost>0)
                    {
                        randomCard.setCostForTurn(randomCard.costForTurn-1);
                    }
                }
            }
        }
        isDone = true;
    }
}
