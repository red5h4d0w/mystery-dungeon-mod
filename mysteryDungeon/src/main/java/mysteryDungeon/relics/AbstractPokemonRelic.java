package mysteryDungeon.relics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import basemod.abstracts.CustomRelic;

public class AbstractPokemonRelic extends CustomRelic { 
    public Color color;

    public AbstractPokemonRelic(String ID, Texture IMG, Texture OUTLINE, RelicTier relicTier, LandingSound landingSound) {
        super(ID, IMG, OUTLINE, relicTier, landingSound);
    }

}

