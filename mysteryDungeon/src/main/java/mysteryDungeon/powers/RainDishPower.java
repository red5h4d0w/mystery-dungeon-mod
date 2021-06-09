package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.interfaces.onDiscardInterface;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//Gain 1 dex for the turn for each card played.

public class RainDishPower extends MysteryDungeonPower implements CloneablePowerInterface, onDiscardInterface {
    public AbstractCreature source;

    public Logger logger = LogManager.getLogger(RainDishPower.class);
    public static final String POWER_ID = MysteryDungeon.makeID("RainDishPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    public boolean cardWasPlayed = false;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public RainDishPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onDiscard() {
        logger.info(cardWasPlayed);
        if(!cardWasPlayed)
        {
            logger.info("block!");
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        else
        {
            cardWasPlayed = false;
        }
    }
    @Override
    public void onAfterUseCard (AbstractCard card, UseCardAction action)
    {
        boolean spoonProc = false;
        if (action.exhaustCard && AbstractDungeon.player.hasRelic("Strange Spoon") && card.type != AbstractCard.CardType.POWER)
            spoonProc = AbstractDungeon.cardRandomRng.randomBoolean(); 
        if(!card.purgeOnUse || !(card.type == CardType.POWER) || spoonProc || !action.exhaustCard || !action.reboundCard || !action.returnToHand || card.shuffleBackIntoDrawPile)
            cardWasPlayed = true;
    }

    @Override
    public AbstractPower makeCopy() {
        return new RainDishPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
