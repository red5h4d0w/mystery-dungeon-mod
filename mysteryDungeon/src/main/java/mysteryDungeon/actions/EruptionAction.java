package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.powers.BurnPower;

public class EruptionAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;

    public EruptionAction(AbstractMonster target) {
        setValues(target, source);
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_MED;
    }

    public void update() {
        if (target.hasPower(BurnPower.POWER_ID)) {
            int burnStacks = target.getPower(BurnPower.POWER_ID).amount;
            addToBot(new DamageAllEnemiesAction(p, burnStacks, DamageType.THORNS, AttackEffect.FIRE));
            addToBot(new RemoveSpecificPowerAction(target, p, target.getPower(BurnPower.POWER_ID)));
        }
        isDone = true;
    }
}