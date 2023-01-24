package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
// import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

//Gain 1 dex for the turn for each card played.

public class RecoverPower extends PokemonTwoAmountPower implements  CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(RecoverPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader
            .getTexture(makePowerPath(RecoverPower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader
            .getTexture(makePowerPath(RecoverPower.class.getSimpleName() + "32.png"));

    public RecoverPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.amount2 = 0;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    // public int getHealthBarAmount() {
    //     return -Math.min(owner.maxHealth - owner.currentHealth, amount);
    // }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type!=DamageType.NORMAL) {
            super.onAttack(info, damageAmount, target);
            return;
        }
        if(MathUtils.floor(0.3f * damageAmount)>0)
            addToBot(new HealAction(owner, owner, Math.min(MathUtils.floor(0.3f * damageAmount), amount)));
        amount -= Math.min(MathUtils.floor(0.3f * damageAmount), amount);
        
        updateDescription();
        super.onAttack(info, damageAmount, target);
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        amount2 += MathUtils.floor(damageAmount);
        updateDescription();
        return;
    }

    @Override
    public void atEndOfRound() {
        super.atEndOfRound();
        amount = amount2;
        amount2 = 0;
        updateDescription();
    }

    public Color getColor() {
        return Color.GREEN.cpy();
    }

    @Override
    public AbstractPower makeCopy() {
        return new RecoverPower(owner, amount);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
