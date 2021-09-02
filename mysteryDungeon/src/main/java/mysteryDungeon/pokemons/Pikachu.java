package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;

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
    
    public Pikachu(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
    }

    @Override
    public void evolve() {
        pathToBackSprite = PATH_TO_SECOND_BACK_SPRITE;
        generateBackSprite();
    }
}
