package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Gain 1 dex for the turn for each card played.

public class ReduceHighestCostAtStartOfTurnPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());
    public static final String POWER_ID = MysteryDungeon.makeID("ReduceHighestCostAtStartOfTurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public ReduceHighestCostAtStartOfTurnPower(final AbstractCreature owner, final int amount) {
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
    public void atStartOfTurnPostDraw() {
        super.atStartOfTurnPostDraw();
        if(owner instanceof AbstractPlayer)
        {
            ArrayList<AbstractCard> highestCostCards = new ArrayList<AbstractCard>();
            for(AbstractCard c: AbstractDungeon.player.hand.group)
            {
                if(highestCostCards.isEmpty())
                {
                    highestCostCards.add(c);
                    continue;
                }
                if(highestCostCards.get(0).cost<c.cost)
                {
                    highestCostCards.clear();
                    highestCostCards.add(c);
                    continue;
                }
                if(highestCostCards.get(0).cost==c.cost)
                {
                    highestCostCards.add(c);
                    continue;
                }
            }
            for (CardQueueItem i : AbstractDungeon.actionManager.cardQueue) {
                if (i.card != null) {
                  logger.info("INVALID: " + i.card.name);
                  highestCostCards.remove(i.card);
                } 
            }
            if(!highestCostCards.isEmpty())
            {
                ArrayList<AbstractCard> randomCards = new ArrayList<AbstractCard>();
                for(int i=0;i<amount;i++)
                {
                    if(!highestCostCards.isEmpty())
                    {
                        AbstractCard randomCard = highestCostCards.get(AbstractDungeon.cardRandomRng.random(0, highestCostCards.size() - 1));
                        randomCards.add(randomCard);
                        highestCostCards.remove(randomCard);
                    } 
                }
                for(AbstractCard randomCard: randomCards)
                {
                    if(randomCard.cost>0)
                    {
                        randomCard.setCostForTurn(randomCard.costForTurn-1);
                    }
                }
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ReduceHighestCostAtStartOfTurnPower(owner, amount);
    }
}
