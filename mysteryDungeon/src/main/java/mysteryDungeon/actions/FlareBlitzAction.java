package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FlareBlitzAction extends AbstractGameAction {
  private int energyGainAmt;
  
  private DamageInfo info;
  
  public FlareBlitzAction(AbstractCreature target, DamageInfo info, int energyAmt) {
    this.info = info;
    setValues(target, info);
    this.energyGainAmt = energyAmt;
    this.actionType = AbstractGameAction.ActionType.DAMAGE;
    this.duration = com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FASTER;
  }
  
  public void update() {
    if (this.duration == com.megacrit.cardcrawl.core.Settings.ACTION_DUR_FASTER && 
      this.target != null) {
      AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.FIRE));
      this.target.damage(this.info);
      if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0)
        addToBot((AbstractGameAction)new GainEnergyAction(this.energyGainAmt)); 
      if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
        AbstractDungeon.actionManager.clearPostCombatActions(); 
    } 
    tickDuration();
  }
}