package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.FlameAnimationEffect;

import mysteryDungeon.powers.BurnPower;

public class RandomBurnAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    
    private static final float POST_ATTACK_WAIT_DUR = 0.1F;
    
    private int numTimes;
    
    private int amount;
    
    public RandomBurnAction(AbstractCreature target, int amount, int numTimes) {
      this.target = target;
      this.actionType = AbstractGameAction.ActionType.DEBUFF;
      this.duration = 0.01F;
      this.numTimes = numTimes;
      this.amount = amount;
    }
    
    public void update() {
      if (this.target == null) {
        this.isDone = true;
        return;
      } 
      if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
        AbstractDungeon.actionManager.clearPostCombatActions();
        this.isDone = true;
        return;
      } 
      if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        this.numTimes--;
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        addToTop(new RandomBurnAction((AbstractCreature)randomMonster, this.amount, this.numTimes));
        addToTop((AbstractGameAction)new VFXAction((AbstractGameEffect)new FlameAnimationEffect(this.target.hb), 0.4F));
      } 
      if (this.target.currentHealth > 0) {
        addToTop((AbstractGameAction)new ApplyPowerAction(this.target, (AbstractCreature)AbstractDungeon.player, (AbstractPower)new BurnPower(this.target, this.amount), this.amount, true, AbstractGameAction.AttackEffect.POISON));
        addToTop((AbstractGameAction)new WaitAction(0.1F));
      } 
      this.isDone = true;
      }
}
