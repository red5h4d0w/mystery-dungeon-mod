package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import mysteryDungeon.powers.BurnPower;
import mysteryDungeon.powers.InfernoPower;


public class EruptionAction extends AbstractGameAction {
    private AbstractPlayer p;

    private boolean toAll;


    public EruptionAction(AbstractMonster target) {
        this(target, false);
    }

    public EruptionAction(AbstractMonster target, boolean toAll) {
        setValues(target, source);
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
        this.toAll = toAll;
    }
    
    public void update() {
        if (target.hasPower(BurnPower.POWER_ID)) {
            int burnStacks = target.getPower(BurnPower.POWER_ID).amount;
            if(toAll) {
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, burnStacks, DamageType.THORNS, AttackEffect.FIRE));
            }
            else {
                addToBot(new DamageAction(target, new DamageInfo(p, burnStacks, DamageType.THORNS), AttackEffect.FIRE));
            }
            if (AbstractDungeon.player.hasPower(InfernoPower.POWER_ID)) {
                addToBot(new ReducePowerAction(AbstractDungeon.player, source, AbstractDungeon.player.getPower(InfernoPower.POWER_ID), 1));
            }
            else {
                addToBot(new RemoveSpecificPowerAction(target, p, target.getPower(BurnPower.POWER_ID))); 
            }
        }
        isDone = true;
    }
}