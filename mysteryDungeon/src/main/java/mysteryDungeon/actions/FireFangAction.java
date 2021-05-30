package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import mysteryDungeon.powers.BurnPower;

public class FireFangAction extends AbstractGameAction {
    private DamageInfo info;
  
    public FireFangAction(AbstractCreature target, DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        setValues(target, info);
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = effect;
    }
  
    public void update() {
        if (this.duration == 0.5F)
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect)); 
        tickDuration();
        if (this.isDone) {
            boolean willInflictDamage = !target.isDeadOrEscaped();
            this.target.damage(this.info);
            if (this.target.lastDamageTaken > 0 && willInflictDamage) {
                addToTop(new ApplyPowerAction(target, source, new BurnPower(target, this.target.lastDamageTaken), this.target.lastDamageTaken));
                addToTop(new WaitAction(0.1F));
            } 
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
                AbstractDungeon.actionManager.clearPostCombatActions(); 
        } 
    }
}
