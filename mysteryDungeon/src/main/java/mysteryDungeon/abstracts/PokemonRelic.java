package mysteryDungeon.abstracts;

import java.util.Set;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;

import basemod.abstracts.CustomRelic;

public abstract class PokemonRelic extends CustomRelic {
    public Set<CardColor> cardColors;

    public PokemonRelic(String ID, Texture IMG, Texture OUTLINE, RelicTier relicTier, LandingSound landingSound) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    @Override
    public void playLandingSFX() {
        CardCrawlGame.sound.play("MYSTERY_DUNGEON_RELIC_GET");
    }

}
