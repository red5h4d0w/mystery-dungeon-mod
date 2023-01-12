package mysteryDungeon.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TotodileSubstituteAction extends AbstractGameAction{
    private int amount;
  

    public TotodileSubstituteAction(int amount) {
        this.amount = amount;
    }

    public TotodileSubstituteAction() {
        this.amount = 1;
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
                AbstractCard card = AbstractDungeon.player.drawPile.getNCardFromTop(i);
                addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.drawPile));
                if (card.type == AbstractCard.CardType.ATTACK) {
                    addToTop(new MoveCardsAction(AbstractDungeon.player.discardPile, AbstractDungeon.player.hand));
                }
            }  
        } 
        this.isDone = true;
    }
}
