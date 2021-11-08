package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Charmeleon extends AbstractPokemon {

    public static String NAME = "Charmeleon";
    public static Color COLOR = Color.RED;
    public static CardColor CARD_COLOR = Pokemon.Enums.CHARMANDER_RED;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Charmeleon.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Charmeleon.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Charmeleon.class.getSimpleName());
    public Charmeleon(){
        super(NAME, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Charizard();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
