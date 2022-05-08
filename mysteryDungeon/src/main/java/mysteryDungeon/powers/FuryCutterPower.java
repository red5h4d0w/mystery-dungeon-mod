package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.cards.Chikorita.ChikoritaFuryCutter;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class FuryCutterPower extends PokemonTwoAmountPower implements CloneablePowerInterface {
    public AbstractCreature source;
    public boolean upgrade;
    public static final String POWER_ID = MysteryDungeon.makeID("FuryCutterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader
            .getTexture(makePowerPath(FuryCutterPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader
            .getTexture(makePowerPath(FuryCutterPower.class.getSimpleName() + "32.png"));

    public FuryCutterPower(final AbstractCreature owner, final int amount, final int upgradeAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = upgradeAmount;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile,
                card -> card.cardID == ChikoritaFuryCutter.ID & card.upgraded, amount));
        addToBot(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile,
                card -> card.cardID == ChikoritaFuryCutter.ID & !card.upgraded, amount2));
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        addToBot(new LoseHPAction(owner, owner, 1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new FuryCutterPower(owner, amount, amount2);
    }

    @Override
    public void updateDescription() {
        if (amount2==0)
            description = String.format(DESCRIPTIONS[0], amount);
        else if (amount==0)
            description = String.format(DESCRIPTIONS[1], amount2);
        else 
            description = String.format(DESCRIPTIONS[2], amount, amount2);
    }
}
