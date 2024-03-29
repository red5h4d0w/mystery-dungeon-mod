package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Gain 1 dex for the turn for each card played.

public class OvergrowPower extends PokemonTwoAmountPower implements CloneablePowerInterface, NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(OvergrowPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader
            .getTexture(makePowerPath(OvergrowPower.class )+"84.png");
    private static final Texture tex32 = TextureLoader
            .getTexture(makePowerPath(OvergrowPower.class )+"32.png");

    public static Logger logger = LogManager.getLogger(OvergrowPower.class);

    public OvergrowPower(final AbstractCreature owner, final int amount, final int amount2) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = amount2;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        
        updateDescription();
    }

    @Override
    public int onHeal(int healAmount) {
        if (healAmount >= amount2) {
            flash();
            addToBot( new ApplyPowerAction(owner, owner, new StrengthPower(owner, (int)amount*MathUtils.floor(healAmount/amount2))));
        }
            
        return healAmount;
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if(power instanceof OvergrowPower) {
            return ((TwoAmountPower)power).amount2 == amount2;
        }
        return false;
    }

    @Override
    public AbstractPower makeCopy() {
        return new OvergrowPower(owner, amount, amount2);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount2, amount);
    }
}
