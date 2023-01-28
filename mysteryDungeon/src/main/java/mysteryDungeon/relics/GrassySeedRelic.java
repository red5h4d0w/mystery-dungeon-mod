package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.pokemons.Chikorita;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardTags;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class GrassySeedRelic extends PokemonRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(GrassySeedRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));

    public GrassySeedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

        cardColor = Chikorita.CARD_COLOR;
        
    }
 
    @Override
    public void atBattleStartPreDraw() {
        AbstractCard[] possibleCards = CardLibrary.cards.values().stream()
                .filter(c -> c instanceof PokemonCard)
                .filter(c -> c.color == ((Pokemon)AbstractDungeon.player).partner.cardColor || c.color == ((Pokemon)AbstractDungeon.player).adventurer.cardColor)
                .filter(c -> c.type == CardType.ATTACK)
                .filter(c -> !c.tags.contains(CardTags.STARTER_DEFEND))
                .filter(c -> !c.tags.contains(CardTags.STARTER_STRIKE))
                .toArray(AbstractCard[]::new);
            AbstractCard c = possibleCards[(int)AbstractDungeon.cardRng.random(possibleCards.length-1)];
            if (c.cost > 0) {
                c.cost = 0;
                c.costForTurn = 0;
                c.isCostModified = true;
              } 
        addToBot(new MakeTempCardInDrawPileAction(c, 1, true, false, false));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
