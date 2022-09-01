package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.actions.EruptionAction;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;


//Gain 1 dex for the turn for each card played.

public class NextTurnEruptionPower extends PokemonPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public int counter = 0;

    public static final String POWER_ID = MysteryDungeon.makeID("NextTurnEruption");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(NextTurnEruptionPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(NextTurnEruptionPower.class.getSimpleName()+"32.png"));

    public NextTurnEruptionPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }





    @Override
    public void atStartOfTurn() {
        if(counter == 1)
        {
        AbstractMonster target = (AbstractMonster)owner;
        addToBot(new EruptionAction((AbstractMonster)target, false));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        
        }
        counter++;
    }

    @Override
    public AbstractPower makeCopy() {
        return new NextTurnEruptionPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        if(amount == 1) {
            description = DESCRIPTIONS[0];
        } 
        else {
            description = String.format(DESCRIPTIONS[1], amount);
        }
    }
}
