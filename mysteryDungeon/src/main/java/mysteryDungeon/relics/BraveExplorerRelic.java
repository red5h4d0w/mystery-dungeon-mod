package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BraveExplorerRelic extends PokemonRelic {

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(BraveExplorerRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("fierce-bandanna.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("fierce-bandanna.png"));

    public boolean trigger = false;
    private boolean used = false;

    public BraveExplorerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void onPlayerEndTurn() {
        if (!used && AbstractDungeon.player.currentBlock == 0 || trigger) {
            trigger = false;
            flash();
            stopPulse();
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster mo : AbstractDungeon.getMonsters().monsters)
                    addToTop(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, 2, false)));
            }
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            used = true;
        }
    }

    public void atTurnStart() {
        trigger = false;
        if (AbstractDungeon.player.currentBlock == 0 && !used)
            beginLongPulse();
    }

    public int onPlayerGainedBlock(float blockAmount) {
        if (blockAmount > 0.0F)
            stopPulse();
        return MathUtils.floor(blockAmount);
    }

    @Override
    public void atPreBattle() {
        used = false;
    }

    public void onVictory() {
        stopPulse();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
