package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.cards.Charmander.CharmanderMetalClaw;
import mysteryDungeon.pokemons.Charmander;
import mysteryDungeon.powers.BurnPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.HashSet;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class GripClawRelic extends PokemonRelic {

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(GripClawRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public GripClawRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.CLINK);
        cardColors = new HashSet<CardColor>() {
            {
                add(Charmander.CARD_COLOR);
            }
        };
    }

    @Override
    public void atTurnStartPostDraw() {
        super.atTurnStartPostDraw();
        if (!AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> m.hasPower(BurnPower.POWER_ID))) {
            addToBot(new MakeTempCardInHandAction(new CharmanderMetalClaw()));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
