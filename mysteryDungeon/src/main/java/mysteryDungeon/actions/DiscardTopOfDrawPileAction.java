package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardTopOfDrawPileAction extends AbstractGameAction{
    private int amount;
  

    public DiscardTopOfDrawPileAction(int amount) {
        this.amount = amount;
    }

    public DiscardTopOfDrawPileAction() {
        this.amount = 1;
    }

    public void update() {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            this.isDone = true;
            return;
        } 
        else {
            for(int i=0;i<amount;i++) {
                AbstractCard card = AbstractDungeon.player.drawPile.getNCardFromTop(i+1);
                addToTop(new DiscardSpecificCardAction(card, AbstractDungeon.player.drawPile));
            }
        } 
        this.isDone = true;
    }
}
