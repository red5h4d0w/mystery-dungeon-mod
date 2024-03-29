package mysteryDungeon.powers.deprecated;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.interfaces.onCardScriedInterface;
import mysteryDungeon.interfaces.onManualDiscardInterface;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


//Gain 1 dex for the turn for each card played.

public class AquaRingPower extends PokemonPower implements CloneablePowerInterface, onManualDiscardInterface, onCardScriedInterface {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID("AquaRingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(AquaRingPower.class));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(AquaRingPower.class));

    public AquaRingPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onManualDiscard(AbstractCard card)
    {
        if(card.type == CardType.STATUS)
        {
            addToBot(new GainBlockAction(owner, owner, amount));
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amount, DamageType.THORNS, AttackEffect.BLUNT_LIGHT));
        }
        else
        {
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        
    }

    @Override
    public void onCardScried(AbstractCard card)
    {
        if(card.type == CardType.STATUS)
        {
            addToBot(new GainBlockAction(owner, owner, amount));
            addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, amount, DamageType.THORNS, AttackEffect.BLUNT_LIGHT));
        }
        else
        {
            addToBot(new GainBlockAction(owner, owner, amount));
        }
        
    }

    @Override
    public AbstractPower makeCopy() {
        return new AquaRingPower(owner, amount);
    }
    @Override
    public void atEndOfRound() {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount, amount);
    }
}
