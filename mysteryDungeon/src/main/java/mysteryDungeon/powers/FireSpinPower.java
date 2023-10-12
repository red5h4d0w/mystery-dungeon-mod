package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


//Gain 1 dex for the turn for each card played.

public class FireSpinPower extends PokemonPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(FireSpinPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(FireSpinPower.class)+"84.png");
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(FireSpinPower.class)+"32.png");

    public FireSpinPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }
    
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        if(power.ID == FireSpinPower.POWER_ID)
         addToBot((AbstractGameAction)new LoseHPAction(this.owner, null, this.amount, AbstractGameAction.AttackEffect.FIRE));
            updateDescription();
        return true;
    }


    @Override
    public AbstractPower makeCopy() {
        return new FireSpinPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1];
      }
}
