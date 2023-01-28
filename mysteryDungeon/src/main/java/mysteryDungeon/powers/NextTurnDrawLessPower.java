package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


//Gain 1 dex for the turn for each card played.

public class NextTurnDrawLessPower extends PokemonPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(NextTurnDrawLessPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int baseHandSize;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(NextTurnDrawLessPower.class+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(NextTurnDrawLessPower.class+"32.png"));

    public NextTurnDrawLessPower(final AbstractCreature owner, final int amount) {
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
    public void onInitialApplication() {
        super.onInitialApplication();
        baseHandSize = AbstractDungeon.player.gameHandSize;
        ((AbstractPlayer)owner).gameHandSize-=amount;
    }

    public boolean onReceivePower(AbstractPower p, AbstractCreature target, AbstractCreature source) {
        if(p instanceof NextTurnDrawLessPower && target==owner) {
            ((AbstractPlayer)owner).gameHandSize = (((AbstractPlayer)owner).gameHandSize - p.amount >0)?((AbstractPlayer)owner).gameHandSize - p.amount:0;
        }
        return true;
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new NextTurnDrawLessPower(owner, amount);
    }

    @Override
    public void onRemove() {
        ((AbstractPlayer)owner).gameHandSize=baseHandSize;
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
