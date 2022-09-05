package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.relics.RunicPyramid;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class TimidExplorerRelic extends PokemonRelic { 
    public static final String ID = MysteryDungeon.makeID(TimidExplorerRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("band5.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    public static Logger logger = LogManager.getLogger(TimidExplorerRelic.class);

    public TimidExplorerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    @Override
    public void onPlayerEndTurn() {
        if(EnergyPanel.getCurrentEnergy()>=1 && !AbstractDungeon.player.hasRelic(RunicPyramid.ID) && !AbstractDungeon.player.hand.isEmpty() ) {
            EnergyPanel.useEnergy(1);
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
