package mysteryDungeon.powers;

import basemod.interfaces.CloneablePowerInterface;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.BetterOnApplyPowerPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

//Gain 1 dex for the turn for each card played.

public class WaterPledgePower extends PokemonPower implements CloneablePowerInterface, BetterOnApplyPowerPower {
    public AbstractCreature source;

    public static final String POWER_ID = MysteryDungeon.makeID("WaterPledgePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84
    // image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if
    // you accidentally put a non-existent file.
    private static final Texture tex84 = TextureLoader
            .getTexture(makePowerPath(WaterPledgePower.class.getSimpleName() + "84.png"));
    private static final Texture tex32 = TextureLoader
            .getTexture(makePowerPath(WaterPledgePower.class.getSimpleName() + "32.png"));

    public WaterPledgePower(final AbstractCreature owner, final int amount) {
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

    public boolean betterOnApplyPower(com.megacrit.cardcrawl.powers.AbstractPower power, com.megacrit.cardcrawl.core.AbstractCreature target, com.megacrit.cardcrawl.core.AbstractCreature source) {
        if(power instanceof GrassPledgePower){
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        addToBot(new ApplyPowerAction(monster, target, new WeakPower(monster, 2, false), 2));
                    }
                } 
            } 
            addToBot(new RemoveSpecificPowerAction(target, source, this));
            return false;
        }
        if(power instanceof FirePledgePower){
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        addToBot(new ApplyPowerAction(monster, target, new VulnerablePower(monster, 2, false), 2));
                    }
                } 
            }
            addToBot(new RemoveSpecificPowerAction(target, source, this));
            return false;  
        }
        else
        return true;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public AbstractPower makeCopy() {
        return new WaterPledgePower(owner, amount);
    }

    @Override
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], amount);
    }
}
