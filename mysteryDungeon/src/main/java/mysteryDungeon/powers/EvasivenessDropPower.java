package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;

import mysteryDungeon.MysteryDungeon;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Gain 1 dex for the turn for each card played.

public class EvasivenessDropPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());
    public static final String POWER_ID = MysteryDungeon.makeID("EvasivenessDropPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EvasivenessDropPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // Use the basegame frailPower icon.
        loadRegion("frail");

        updateDescription();
    }

    
    @Override
    public void atEndOfRound() {
        super.atStartOfTurn();
        if (!owner.isDeadOrEscaped() && !owner.isDying) {
            if(owner.currentBlock>0)
            {
                flash();
                addToBot(new DamageAction(owner, new DamageInfo(owner, (int)Math.ceil(0.25f * owner.currentBlock), DamageType.THORNS)));
            }
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LeechSeedPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
