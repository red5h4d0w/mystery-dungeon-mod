package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ChlorophyllAction extends AbstractGameAction {
  
    public ChlorophyllAction(AbstractCreature target, AbstractCreature source) {
        this.target = target;
        this.source = source;
    }
  
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.ATTACK) {
              AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, source, new StrengthPower(target, 1), 1));
            } 
          } 
          this.isDone = true;
        }
}
