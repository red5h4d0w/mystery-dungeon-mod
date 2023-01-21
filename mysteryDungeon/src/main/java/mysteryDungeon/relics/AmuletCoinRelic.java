package mysteryDungeon.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.RelicStrings;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.actions.SpendGoldAction;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

public class AmuletCoinRelic extends PokemonRelic {
    public static final String ID = MysteryDungeon.makeID(AmuletCoinRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("big-root.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("big-root.png"));

    public AmuletCoinRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        addToBot(new SpendGoldAction(20));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
