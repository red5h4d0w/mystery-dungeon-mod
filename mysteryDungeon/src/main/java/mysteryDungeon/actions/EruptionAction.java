package mysteryDungeon.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;

import mysteryDungeon.powers.BurnPower;
import mysteryDungeon.powers.CrushClawPower;
import mysteryDungeon.powers.InfernoPower;
import mysteryDungeon.powers.LavaPlumePower;


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
        // Check if target has Burn
        if (target.hasPower(BurnPower.POWER_ID)) {

            // make a variable out the target's burnStacks
            int burnStacks = 0;
            if(AbstractDungeon.player.hasPower(CrushClawPower.POWER_ID)){
                 burnStacks = MathUtils.floor(1.5f*target.getPower(BurnPower.POWER_ID).amount);
                 addToBot(new ReducePowerAction(AbstractDungeon.player, source, AbstractDungeon.player.getPower(CrushClawPower.POWER_ID), 1));
            }
            else{
                 burnStacks = target.getPower(BurnPower.POWER_ID).amount;
            }

            addToBot(new VFXAction(new ScreenOnFireEffect()));

            // Process damage to deal 
            if(toAll) {
                addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, burnStacks, DamageType.THORNS, AttackEffect.FIRE));
            }
            else {
                addToBot(new DamageAction(target, new DamageInfo(p, burnStacks, DamageType.THORNS), AttackEffect.FIRE));
            }

            // Remove Inferno or burn depending
            if (AbstractDungeon.player.hasPower(InfernoPower.POWER_ID)) {
                addToBot(new ReducePowerAction(AbstractDungeon.player, source, AbstractDungeon.player.getPower(InfernoPower.POWER_ID), 1));
            }
            else {
                addToBot(new RemoveSpecificPowerAction(target, p, target.getPower(BurnPower.POWER_ID))); 
            }

            // Place Burn back if player has Lava Plume
            if(AbstractDungeon.player.hasPower(LavaPlumePower.POWER_ID)){
                addToBot(new ApplyPowerAction(target, source, new BurnPower(target,  target.getPower(LavaPlumePower.POWER_ID).amount)));
            }
        }
        isDone = true;
    }
}