package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//Gain 1 dex for the turn for each card played.

public class EvasivenessDropPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());
    public static final String POWER_ID = MysteryDungeon.makeID("LeechSeedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

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
    public void atEndOfRound() {
        super.atStartOfTurn();
        if (!owner.isDeadOrEscaped() && !owner.isDying) {
            flash();
            if(owner.currentBlock>0)
            {
                addToBot(new DamageAction(owner, new DamageInfo(owner, (int)Math.ceil(0.25f * owner.currentBlock), DamageType.THORNS)));
            }
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LeechSeedPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
