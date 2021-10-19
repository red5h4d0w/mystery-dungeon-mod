package mysteryDungeon.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.abstracts.CustomRelic;

public abstract class AbstractPokemonRelic extends CustomRelic { 
    public CardColor cardColor;

    public AbstractPokemonRelic(String ID, Texture IMG, Texture OUTLINE, RelicTier relicTier, LandingSound landingSound) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
    }

    @Override
    public void playLandingSFX() {
        CardCrawlGame.sound.play("MYSTERY_DUNGEON_RELIC_GET");
    }

}
