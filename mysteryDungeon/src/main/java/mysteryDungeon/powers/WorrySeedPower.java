package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.interfaces.onCreateRewards;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rewards.RewardItem.RewardType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


//Gain 1 dex for the turn for each card played.

public class WorrySeedPower extends PokemonPower implements CloneablePowerInterface, onCreateRewards {
    public AbstractCreature source;

    public static Logger logger = LogManager.getLogger(WorrySeedPower.class.getName());

    public static final String POWER_ID = MysteryDungeon.makeID(WorrySeedPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(WorrySeedPower.class+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(WorrySeedPower.class+"32.png"));

    public WorrySeedPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void afterCreated(ArrayList<RewardItem> rewards) {
        for (int i=0; i<amount; i++)
        {
            for(RewardItem reward : rewards)
            {
                if(reward.type == RewardType.CARD)
                {
                    reward.cards.add(AbstractDungeon.getRewardCards().get(0));
                }
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new WorrySeedPower(owner, amount);
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
