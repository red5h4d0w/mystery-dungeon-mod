package mysteryDungeon.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Totodile;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

public class SplashPlateRelic extends PokemonRelic {
    public static final String ID = MysteryDungeon.makeID(SplashPlateRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("big-root.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("big-root.png"));

    private boolean used;

    public SplashPlateRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        cardColor = Totodile.CARD_COLOR;
    }

    @Override
    public void onEvokeOrb(AbstractOrb ammo) {
        super.onEvokeOrb(ammo);
        if(!used) {
            addToBot(new GainEnergyAction(1));
            used = true;
        }
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        used = false;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
