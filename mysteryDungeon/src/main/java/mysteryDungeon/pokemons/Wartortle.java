package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Wartortle extends AbstractPokemon {

    public static String NAME = "Wartortle";
    public static Color COLOR = Color.BLUE;
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Wartortle.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Wartortle.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Wartortle.class.getSimpleName());
    public Wartortle(){
        super(NAME, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
        canEvolve = false;
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Wartortle();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
