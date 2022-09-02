package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import mysteryDungeon.actions.EvasivenessDropAction;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class EvasivenessDropPower extends PokemonPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(EvasivenessDropPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(EvasivenessDropPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(EvasivenessDropPower.class.getSimpleName()+"32.png"));

    public EvasivenessDropPower(final AbstractCreature owner, final int amount) {
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
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        super.atEndOfTurnPreEndTurnCards(isPlayer);
        flash();
        addToBot(new EvasivenessDropAction(owner));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new EvasivenessDropPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        if(amount == 1)
        {
            description = DESCRIPTIONS[0];
        } 
        else
        {
            description = String.format(DESCRIPTIONS[1], amount);
        }
    }
}
