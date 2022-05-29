package mysteryDungeon.actions;


import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import mysteryDungeon.powers.BurnPower;


public class EruptionAction extends AbstractGameAction {
    private AbstractPlayer p;
    private AbstractMonster m;


    public EruptionAction() {
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    public void update() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead() && this.m.currentHealth > 0)
        {
            int burnStacks = m.hasPower(BurnPower.POWER_ID)?m.getPower(BurnPower.POWER_ID).amount:0;
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(MathUtils.floor(amount*burnStacks), true), DamageType.THORNS, AttackEffect.FIRE)); 
            adToBot(new RemoveSpecificPowerAction(m, p, BurnPower(m, burnStacks)));
        }
    }
}