package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Squirtle;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;

public class WaveIncenseRelic extends PokemonRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(WaveIncenseRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("wave-incense.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("wave-incense.png"));

    public WaveIncenseRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
        cardColor = Squirtle.CARD_COLOR;
    }

    @Override
    public void atPreBattle()
    {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MasterRealityPower(AbstractDungeon.player)));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
