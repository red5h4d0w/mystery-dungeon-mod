package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;


import mysteryDungeon.cards.Squirtle.SquirtleDefend;
import mysteryDungeon.cards.Squirtle.SquirtleWaterGun;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.characters.Pokemon;

public class Squirtle extends AbstractPokemon {
    public static String NAME = "Squirtle";
    public static int MAX_HP = 35;
    public static int ORB_SLOTS = 0;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new SquirtleTackle(), new SquirtleTackle(), new SquirtleDefend(), new SquirtleDefend(), new SquirtleWaterGun()};
    public static Color COLOR = Color.BLUE;
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Squirtle.class.getSimpleName()+".png");
    public static String PATH_TO_SECOND_BACK_SPRITE = makeBackSpritePath("Wartortle.png");
    public static String PATH_TO_THIRD_BACK_SPRITE = makeBackSpritePath("Blastoise.png");
    public static String SKELETON_URL = makeSkeletonPath(Squirtle.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Squirtle.class.getSimpleName());
    public static String SECOND_SKELETON_URL = makeSkeletonPath("Wartortle");
    public static String SECOND_ATLAS_URL = makeAtlasPath("Wartortle");
    public static String THIRD_SKELETON_URL = makeSkeletonPath("Blastoise");
    public static String THIRD_ATLAS_URL = makeAtlasPath("Blastoise");
    
    public Squirtle(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
    }

    @Override
    public void evolve() {
        if(hasEvolved) {
            pathToBackSprite = PATH_TO_THIRD_BACK_SPRITE;
        }
        else {
            pathToBackSprite = PATH_TO_SECOND_BACK_SPRITE;
            hasEvolved = true;
        }
        generateBackSprite();
    }
}
