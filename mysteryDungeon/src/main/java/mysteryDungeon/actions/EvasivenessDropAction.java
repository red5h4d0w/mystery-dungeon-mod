package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class EvasivenessDropAction extends AbstractGameAction {
    public AbstractCreature owner;
    public EvasivenessDropAction(AbstractCreature owner) {
        this.owner = owner;
    }

    public void update() {
        if (!owner.isDeadOrEscaped() && !owner.isDying) {
            if(owner.currentBlock>0)
            {
                addToTop(new DamageAction(owner, new DamageInfo(owner, (int)Math.ceil(0.25f * owner.currentBlock), DamageType.THORNS)));
            }
        }
        isDone = true;
    }
}
