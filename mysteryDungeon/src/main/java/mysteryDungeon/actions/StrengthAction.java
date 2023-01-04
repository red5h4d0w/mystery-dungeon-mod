package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.orbs.Frost;

public class StrengthAction extends AbstractGameAction {
  
    public StrengthAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }
  
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                addToTop(new ChannelAction(new Frost()));
            } 
        } 
        isDone = true;
    }
}
