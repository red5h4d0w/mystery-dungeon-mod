package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class TimidExplorerRelic extends PokemonRelic { 
    public static final String ID = MysteryDungeon.makeID(TimidExplorerRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("band5.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    public TimidExplorerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        if(AbstractDungeon.player.energy.energy>1) {
            AbstractDungeon.player.energy.use(1);
            addToTop(new RetainCardsAction(AbstractDungeon.player, 1));
        }
        super.onPlayerEndTurn();
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
