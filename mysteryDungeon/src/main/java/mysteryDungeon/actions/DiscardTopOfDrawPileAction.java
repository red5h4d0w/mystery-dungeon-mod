package mysteryDungeon.actions;

import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardTopOfDrawPileAction extends AbstractGameAction{
    private int amount;
    private Predicate<AbstractCard> addToHandConditional;
  

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
            for(int i=0;i<amount;i++) {
                int numberOfCardsToAddToHand = 0;
                AbstractCard card = AbstractDungeon.player.drawPile.getNCardFromTop(i);
                if(addToHandConditional.test(card) && AbstractDungeon.player.hand.size()+numberOfCardsToAddToHand<AbstractDungeon.player.gameHandSize)
                    AbstractDungeon.player.drawPile.moveToHand(card, AbstractDungeon.player.drawPile);
                else if(addToHandConditional.test(card) && AbstractDungeon.player.hand.size()+numberOfCardsToAddToHand>=AbstractDungeon.player.gameHandSize)
                    AbstractDungeon.player.createHandIsFullDialog();
                else
                    addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        } 
        this.isDone = true;
    }
}
