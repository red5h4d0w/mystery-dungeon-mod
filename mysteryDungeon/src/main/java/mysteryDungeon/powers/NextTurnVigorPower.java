package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;


//Gain 1 dex for the turn for each card played.

public class NextTurnVigorPower extends PokemonTwoAmountPower implements CloneablePowerInterface, NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(NextTurnVigorPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(NextTurnVigorPower.class+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(NextTurnVigorPower.class+"32.png"));

    public NextTurnVigorPower(final AbstractCreature owner, final int amount, final int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount), amount));
        amount2--;
        if(amount2<1)
        {
            addToBot(new RemoveSpecificPowerAction(owner, source, this));
        }
        updateDescription();
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if(power instanceof NextTurnVigorPower) {
            return ((NextTurnVigorPower)power).amount2 == this.amount2;
        }
        return false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new NextTurnVigorPower(owner, amount, amount2);
    }

    @Override
    public void updateDescription() {
        if(amount == 1 && amount2 == 1)
            description = DESCRIPTIONS[0];
        else if(amount != 1 && amount2 == 1)
            description = String.format(DESCRIPTIONS[1], amount);
        else if(amount == 1 && amount2 != 1)
            description = String.format(DESCRIPTIONS[2], amount2);
        else if(amount != 1 && amount2 != 1)
            description = String.format(DESCRIPTIONS[3], amount2, amount);

    }
}
