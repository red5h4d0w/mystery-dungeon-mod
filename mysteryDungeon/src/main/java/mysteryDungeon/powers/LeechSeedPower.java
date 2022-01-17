package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;
import mysteryDungeon.actions.LeechSeedAction;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


//Gain 1 dex for the turn for each card played.

public class LeechSeedPower extends MysteryDungeonPower implements CloneablePowerInterface, HealthBarRenderPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID("LeechSeedPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public int wasAttacked = 0;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(LeechSeedPower.class.getSimpleName()+"84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(LeechSeedPower.class.getSimpleName()+"32.png"));

    public LeechSeedPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public int getHealthBarAmount()
    {
        return amount;
    }


    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        if (!owner.isDeadOrEscaped() && !owner.isDying) {
            flash();
            addToBot(new LeechSeedAction(owner, new DamageInfo(source, amount, DamageType.HP_LOSS), AttackEffect.POISON));
        }
    }

    public Color getColor()
    {
        return Color.OLIVE;
    }
    public int onAttacked(DamageInfo info, int damageAmount) {
        wasAttacked++;
        return damageAmount;
    }

    public void atEndOfRound(){
    if(wasAttacked<2){
        if(amount<=1){
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
        else
            amount-= MathUtils.ceil(amount/2.0f);
        updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LeechSeedPower(owner, source, amount);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount, amount);
    }
}
