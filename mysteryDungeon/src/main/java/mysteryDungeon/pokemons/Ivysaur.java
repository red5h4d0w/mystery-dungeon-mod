package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Ivysaur extends AbstractPokemon {

    public static String NAME = "Ivysaur";
    public static Color COLOR = Color.GREEN.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Ivysaur.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Ivysaur.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Ivysaur.class.getSimpleName());
    public Ivysaur(){
        super(NAME, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Venusaur();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
