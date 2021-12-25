package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.cards.tempCards.SquirtleFlinch;

public class AquaRingAction extends AbstractGameAction {

    private boolean upgrade;

    public AquaRingAction(boolean upgraded) {
        this.upgrade = upgraded;
    }
    
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if (this.upgrade){
                AbstractCard s = (new SquirtleFlinch()).makeCopy();
                s.upgrade();
                addToTop((AbstractGameAction)new MakeTempCardInHandAction(s, amount));
            } 
            else {
                addToTop((AbstractGameAction)new MakeTempCardInHandAction((AbstractCard)new SquirtleFlinch, amount));
            }
                addToTop(new DiscardAction(player, player, amount, false));
            }
        isDone = true;
    }
}