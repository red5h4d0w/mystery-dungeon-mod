package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;


public class DoubleEdgeAction extends AbstractGameAction {
    private DamageInfo info;
  
    public DoubleEdgeAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
    }
  
    public void update() {
        if (duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, attackEffect)); 
        tickDuration();
        if (isDone) {
            boolean willInflictDamage = !target.isDeadOrEscaped();
            target.damage(info);
            if (this.target.lastDamageTaken > 0 && willInflictDamage) {
                addToTop(new ApplyPowerAction(source, source, new StrengthPower(source, target.lastDamageTaken), target.lastDamageTaken));
            } 
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions(); 
        } 
    }
}
