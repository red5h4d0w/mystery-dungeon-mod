package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class MetronomeRelic extends PokemonRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(MetronomeRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("metronome.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("metronome.png"));

    public MetronomeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atTurnStart() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            List<AbstractCard> possibleCards = CardLibrary.cards.values().stream()
                .filter(c -> c instanceof PokemonCard)
                .filter(c -> c.color != Pokemon.partner.cardColor)
                .filter(c -> c.color != Pokemon.adventurer.cardColor)
                .collect(Collectors.toList());
            AbstractCard c = possibleCards.get((int) AbstractDungeon.cardRng.random(possibleCards.size()));
            c.setCostForTurn(0);
            addToBot((AbstractGameAction)new MakeTempCardInHandAction(c, true));
            
        } 
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
