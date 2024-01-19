package mysteryDungeon.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.actions.EruptionAction;
import mysteryDungeon.pokemons.Cyndaquil;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.HashSet;

public class FlamePlateRelic extends PokemonRelic {
    public static final String ID = MysteryDungeon.makeID(FlamePlateRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("flame-plate.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("flame-plate.png"));

    public FlamePlateRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);
        cardColors = new HashSet<CardColor>() {
            {
                add(Cyndaquil.CARD_COLOR);
            }
        };
    }

    @Override
    public void atTurnStart() {
        super.atTurnStart();
        addToBot(new GainEnergyAction(1));
    }

    @Override
    public void onPlayerEndTurn() {
        super.onPlayerEndTurn();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                addToBot(new EruptionAction(m));
            }
        }

    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
