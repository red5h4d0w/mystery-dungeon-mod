package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Squirtle;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.HashSet;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class MysticWaterRelic extends PokemonRelic {

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(MysticWaterRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mystic-water.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mystic-water.png"));

    public MysticWaterRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        cardColors = new HashSet<CardColor>() {
            {
                add(Squirtle.CARD_COLOR);
            }
        };
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster++;
        basemod.BaseMod.MAX_HAND_SIZE = 8;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster--;
        basemod.BaseMod.MAX_HAND_SIZE = 10;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
