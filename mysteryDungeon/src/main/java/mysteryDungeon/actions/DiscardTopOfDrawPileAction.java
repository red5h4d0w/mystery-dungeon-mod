package mysteryDungeon.actions;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardTopOfDrawPileAction extends AbstractGameAction{
    private int amount;
    private Predicate<AbstractCard> addToHandConditional;
  
    public Logger logger = LogManager.getLogger(DiscardTopOfDrawPileAction.class);

    public DiscardTopOfDrawPileAction(int amount, Predicate<AbstractCard> addToHandConditional) {
        super();
        this.amount = amount;
        this.addToHandConditional = addToHandConditional;
    }

    public DiscardTopOfDrawPileAction(int amount) {
        super();
        this.amount = amount;
    }

    public DiscardTopOfDrawPileAction() {
        this.amount = 1;
        this.addToHandConditional = (card) -> false;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        } 
        else {
            if(amount>AbstractDungeon.player.drawPile.size()) {
                amount = AbstractDungeon.player.drawPile.size();
            }
            ArrayList<AbstractCard> cardsToDraw = new ArrayList<AbstractCard>();
            for(int i=0;i<amount;i++) {
                AbstractCard card = AbstractDungeon.player.drawPile.getNCardFromTop(i);
                if(addToHandConditional.test(card))
                    cardsToDraw.add(card);
                else
                    addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
            for(AbstractCard card: cardsToDraw) {
                if(AbstractDungeon.player.hand.size()<basemod.BaseMod.MAX_HAND_SIZE)
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                else {
                    AbstractDungeon.player.createHandIsFullDialog();
                }
            }
        } 
        this.isDone = true;
    }
}
