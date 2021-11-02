package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Pikachu.PikachuDefend;
import mysteryDungeon.cards.Pikachu.PikachuTackle;
import mysteryDungeon.cards.Pikachu.PikachuThundershock;
import mysteryDungeon.characters.Pokemon;



public class Pikachu extends AbstractPokemon {
    public static String NAME = "Pikachu";
    public static int MAX_HP = 25;
    public static int ORB_SLOTS = 2;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new PikachuTackle(), new PikachuTackle(), new PikachuDefend(), new PikachuDefend(), new PikachuThundershock()};
    public static Color COLOR = Color.YELLOW;
    public static CardColor CARD_COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Pikachu.class.getSimpleName()+".png");
    public static String PATH_TO_SECOND_BACK_SPRITE = makeBackSpritePath("Raichu.png");
    public static String SKELETON_URL = makeSkeletonPath(Pikachu.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Pikachu.class.getSimpleName());
    public static String SECOND_SKELETON_URL = makeSkeletonPath("Raichu");
    public static String SECOND_ATLAS_URL = makeAtlasPath("Raichu");
    
    
    public Pikachu(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
    }

    @Override
    public void evolve() {
        pathToBackSprite = PATH_TO_SECOND_BACK_SPRITE;
        skeletonUrl = SECOND_SKELETON_URL;
        atlasUrl = SECOND_ATLAS_URL;
        generateBackSprite();
    }
}
