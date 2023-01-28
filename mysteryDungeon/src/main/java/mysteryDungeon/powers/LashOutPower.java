package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.cards.tempCards.MeowthFlurry;
import mysteryDungeon.interfaces.BetterOnGainBlockInterface;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;


//Gain 1 dex for the turn for each card played.

public class LashOutPower extends PokemonPower implements CloneablePowerInterface, BetterOnApplyPowerPower, BetterOnGainBlockInterface {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(LashOutPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean upgraded = false;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(LashOutPower.class+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(LashOutPower.class+"32.png"));

    public LashOutPower(final AbstractCreature owner, final int amount, boolean upgraded) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.upgraded = upgraded;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public boolean betterOnApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(source == owner && target!=owner && power instanceof StrengthPower && power.amount>0) {
            AbstractCard flurry = new MeowthFlurry();
            if(upgraded)
                flurry.upgrade();
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(new MeowthFlurry(), amount, false));
        }
        return true;
    }

    @Override
    public int betterOnApplyPowerStacks(AbstractPower power, AbstractCreature target, AbstractCreature source, int stackAmount) {
        if(source == owner && target!=owner && power instanceof StrengthPower && stackAmount > 0) {
            AbstractCard flurry = new MeowthFlurry();
            if(upgraded)
                flurry.upgrade();
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(new MeowthFlurry(), amount, false));
        }
        return stackAmount;
    }

    @Override
    public void betterOnGainBlock(AbstractCreature receiver, int blockAmount) {
        if(receiver!=owner) {
            AbstractCard flurry = new MeowthFlurry();
            if(upgraded)
                flurry.upgrade();
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(new MeowthFlurry(), amount, false));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LashOutPower(owner, amount, upgraded);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
