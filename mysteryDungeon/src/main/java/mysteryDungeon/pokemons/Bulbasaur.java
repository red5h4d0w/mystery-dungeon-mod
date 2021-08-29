package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Bulbasaur.BulbasaurDefend;
import mysteryDungeon.cards.Bulbasaur.BulbasaurLeechSeed;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.characters.Pokemon;

public class Bulbasaur extends AbstractPokemon {

    public static String NAME = "Bulbasaur";
    public static int MAX_HP = 40;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new BulbasaurTackle(), new BulbasaurTackle(), new BulbasaurDefend(), new BulbasaurDefend(), new BulbasaurLeechSeed()};
    public static Color COLOR = Color.GREEN;
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Bulbasaur.class.getSimpleName()+".png");
    public static String PATH_TO_SECOND_BACK_SPRITE = makeBackSpritePath("Ivysaur.png");
    public static String PATH_TO_THIRD_BACK_SPRITE = makeBackSpritePath("Venusaur.png");

    public Bulbasaur(){
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
