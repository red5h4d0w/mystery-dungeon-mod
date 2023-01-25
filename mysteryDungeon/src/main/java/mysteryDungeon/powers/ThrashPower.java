package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.util.TextureLoader;
import static mysteryDungeon.MysteryDungeon.makePowerPath;


public class ThrashPower extends PokemonTwoAmountPower implements CloneablePowerInterface, NonStackablePower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(ThrashPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ThrashPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ThrashPower.class.getSimpleName()+"32.png"));

    private int cardAmount;

    public ThrashPower(final AbstractCreature owner, final int amount, final int cardAmount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.cardAmount = cardAmount;
        this.amount2 = cardAmount;

        type = PowerType.BUFF;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new ThrashPower(owner, amount, cardAmount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if(card.type == CardType.ATTACK) {
            amount2--;
            if(amount2==0) {
                addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, amount), amount));
                amount2=cardAmount;
            }
        }
        updateDescription();
        super.onPlayCard(card, m);
    }

    @Override
    public boolean isStackable(AbstractPower power) {
        if(power instanceof ThrashPower) {
            return ((ThrashPower)power).cardAmount == cardAmount;
        }
        return false;
    }

    @Override
    public void updateDescription() {
        if(amount2<2 && amount<2) {
            description = String.format(DESCRIPTIONS[0], cardAmount, amount, amount2);
        }
        else if(amount<2) {
            description = String.format(DESCRIPTIONS[1], cardAmount, amount, amount2);
        }
        else if(amount2<2) {
            description = String.format(DESCRIPTIONS[2], cardAmount, amount, amount2);
        }
        else {
            description = String.format(DESCRIPTIONS[3], cardAmount, amount, amount2);
        }
    }
}
