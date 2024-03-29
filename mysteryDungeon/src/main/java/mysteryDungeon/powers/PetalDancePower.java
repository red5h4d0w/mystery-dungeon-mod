package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnReceivePowerPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.PenNib;
import com.megacrit.cardcrawl.vfx.PetalEffect;


//Gain 1 dex for the turn for each card played.

public class PetalDancePower extends PokemonPower implements CloneablePowerInterface, OnReceivePowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID(PetalDancePower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(PetalDancePower.class)+"84.png");
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(PetalDancePower.class)+"32.png");

    public PetalDancePower(final AbstractCreature owner, final int amount) {
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
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new VFXAction(new PetalEffect(), 0.7f));
        addToBot(new DamageAllEnemiesAction((AbstractPlayer)owner, 7, DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(owner instanceof AbstractPlayer) {
            if(((AbstractPlayer)owner).hasRelic(PenNib.ID)) {
                ((PenNib)((AbstractPlayer)owner).getRelic(PenNib.ID)).onUseCard(new BulbasaurTackle(), null);
            }
        }
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public AbstractPower makeCopy() {
        return new PetalDancePower(owner, amount);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        updateDescription();
        super.onApplyPower(power, target, source);
    }

    @Override
    public boolean onReceivePower(AbstractPower power, AbstractCreature target, AbstractCreature source)
    {
        updateDescription();
        return true;
    }

    @Override
    public void updateDescription() {
        int minDamageDealt = 0;
        for(AbstractMonster mo: AbstractDungeon.getMonsters().monsters) {
            DamageInfo damageInfo = new DamageInfo(owner, 7);
            damageInfo.applyPowers(owner, mo);
            if(minDamageDealt == 0) {
                minDamageDealt = damageInfo.output;
            }
            if(damageInfo.output < minDamageDealt) {
                minDamageDealt = damageInfo.output;
            }
        }
        
        if(amount == 1) {
            description = String.format(DESCRIPTIONS[0], minDamageDealt);
        } 
        else {
            description = String.format(DESCRIPTIONS[1], amount, minDamageDealt);
        }
    }
}
