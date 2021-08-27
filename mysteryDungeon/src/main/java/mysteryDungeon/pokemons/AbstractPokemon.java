package mysteryDungeon.pokemons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

public abstract class AbstractPokemon {
    public static String NAME;
    public static int MAX_HP;
    public static int ORB_SLOTS;
    public static AbstractCard[] STARTING_DECK;
    public static Color COLOR;
    public static CardColor CARD_COLOR;
    public static String PATH_TO_BACK_SPRITE;
    
    public AbstractPokemon(String name, int maxHp, int orbSlots, AbstractCard[] startingDeck, Color color, CardColor cardColor, String pathToBackSprite) {
        this.name=name;
        this.maxHp=maxHp;
        this.orbSlots = orbSlots;
        this.startingDeck = startingDeck;
        this.color = color;
        this.cardColor = cardColor;
        this.pathToBackSprite = pathToBackSprite;
        this.backSprite = new Pixmap(Gdx.files.internal(pathToBackSprite));
    }
    public String name;
    public int maxHp;
    public int orbSlots;
    public AbstractCard[] startingDeck;
    public Color color;
    public CardColor cardColor;
    public String pathToBackSprite;
    public Pixmap backSprite;
}
