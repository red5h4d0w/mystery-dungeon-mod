package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class BodyPressAction extends AbstractGameAction {
    private DamageInfo info;

    private int blockAmount;
  
    public BodyPressAction(AbstractCreature target, DamageInfo info, int amount, int blockAmount) {
        this.info = info;
        setValues(target, info);
        this.amount = amount;
        this.blockAmount = blockAmount;
        this.actionType = ActionType.POWER;
        this.duration = 0.1F;
    }
  
  public void update() {
    if (duration == 0.1F && this.target != null) {
        target.damage(this.info);
        if ((target.isDying || target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, amount), amount));
            addToBot(new GainBlockAction(p, p, blockAmount));
        } 
        if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead())
            AbstractDungeon.actionManager.clearPostCombatActions(); 
        } 
        tickDuration();
    }
}
