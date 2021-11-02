package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Charmander.CharmanderDefend;
import mysteryDungeon.cards.Charmander.CharmanderEmber;
import mysteryDungeon.cards.Charmander.CharmanderScratch;
import mysteryDungeon.characters.Pokemon;

public class Charmander extends AbstractPokemon {
    
    public static String NAME = "Charmander";
    public static int MAX_HP = 30;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new CharmanderScratch(), new CharmanderScratch(), new CharmanderDefend(), new CharmanderDefend(), new CharmanderEmber()};
    public static Color COLOR = Color.RED;
    public static CardColor CARD_COLOR = Pokemon.Enums.CHARMANDER_RED;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Charmander.class.getSimpleName()+".png");
    public static String PATH_TO_SECOND_BACK_SPRITE = makeBackSpritePath("Charmeleon.png");
    public static String PATH_TO_THIRD_BACK_SPRITE = makeBackSpritePath("Charizard.png");
    public static String SKELETON_URL = makeSkeletonPath(Charmander.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Charmander.class.getSimpleName());
    public static String SECOND_SKELETON_URL = makeSkeletonPath("Charmeleon");
    public static String SECOND_ATLAS_URL = makeAtlasPath("Charmeleon");
    public static String THIRD_SKELETON_URL = makeSkeletonPath("Charizard");
    public static String THIRD_ATLAS_URL = makeAtlasPath("Charizard");

    public Charmander(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
    }

    @Override
    public void evolve() {
        if(hasEvolved) {
            pathToBackSprite = PATH_TO_THIRD_BACK_SPRITE;
            skeletonUrl = THIRD_SKELETON_URL;
            atlasUrl = THIRD_ATLAS_URL;
        }
        else {
            pathToBackSprite = PATH_TO_SECOND_BACK_SPRITE;
            skeletonUrl = SECOND_SKELETON_URL;
            atlasUrl = SECOND_ATLAS_URL;
            hasEvolved = true;
        }
        generateBackSprite();
    }
}
