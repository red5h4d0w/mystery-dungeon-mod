package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseTempHpPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class SunnyDayPower extends PokemonTwoAmountPower implements CloneablePowerInterface, OnLoseTempHpPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(SunnyDayPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader
            .getTexture(makePowerPath(SunnyDayPower.class )+"84.png");
    private static final Texture tex32 = TextureLoader
            .getTexture(makePowerPath(SunnyDayPower.class )+"32.png");

    public SunnyDayPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = 3;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }


    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner) {
            flash();
            if(damageAmount-amount2>=0)
                addToBot((AbstractGameAction) new DrawCardAction(((damageAmount+3-amount2)/3)*amount));
            // Compute new remainder
            amount2 = (amount2-damageAmount) % 3 + 3 % 3;
        }
        if(amount2==0) {
            amount2+=3;
        }
    }

    public int onLoseTempHp(DamageInfo info, int damageAmount) {
        if (damageAmount > 0 && info.owner == this.owner) {
            flash();
            if(damageAmount-amount2>=0)
                addToBot((AbstractGameAction) new DrawCardAction(((damageAmount+3-amount2)/3)*amount));
            // Compute new remainder
            amount2 = (amount2-damageAmount) % 3 + 3 % 3;
        }
        if(amount2==0) {
            amount2+=3;
        }
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new SunnyDayPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        if(amount==1)
            description = String.format(DESCRIPTIONS[0], amount);
        else
            description = String.format(DESCRIPTIONS[1], amount);
    }
}
