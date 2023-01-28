package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.pokemons.Pikachu;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class CellBatteryRelic extends PokemonRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(CellBatteryRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("cell-battery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("cell-battery.png"));

    public CellBatteryRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        cardColor = Pikachu.CARD_COLOR;
    }

    @Override
    public void onEquip() {
        if(AbstractDungeon.player instanceof Pokemon) {
            Pokemon.maxPikachuChargeCounter = 3;
        }
    }

    @Override
    public void onUnequip() {
        if(AbstractDungeon.player instanceof Pokemon) {
            Pokemon.maxPikachuChargeCounter = 2;
        }
    }

    

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
