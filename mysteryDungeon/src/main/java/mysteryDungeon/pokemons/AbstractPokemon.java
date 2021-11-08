package mysteryDungeon.pokemons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

public abstract class AbstractPokemon {
    
    public AbstractPokemon(String name, int maxHp, int orbSlots, AbstractCard[] startingDeck, Color color, CardColor cardColor, String pathToBackSprite) {
        this.name=name;
        this.maxHp=maxHp;
        this.orbSlots = orbSlots;
        this.startingDeck = startingDeck;
        this.color = color;
        this.cardColor = cardColor;
        this.pathToBackSprite = pathToBackSprite;
        generateBackSprite();
    }

    public AbstractPokemon(String name, Color color, CardColor cardColor, String pathToBackSprite) {
        this.name=name;
        this.color = color;
        this.cardColor = cardColor;
        this.pathToBackSprite = pathToBackSprite;
        generateBackSprite();
    }
    public String name;
    public int maxHp;
    public int orbSlots;
    public AbstractCard[] startingDeck;
    public Color color;
    public CardColor cardColor;
    public String pathToBackSprite;
    public Pixmap backSprite;
    public boolean hasEvolved = false;
    public String skeletonUrl;
    public String atlasUrl;
    public boolean shiny = false;
    public boolean canEvolve = true;

    public void generateBackSprite() {
        this.backSprite = new Pixmap(Gdx.files.internal(pathToBackSprite));
    }

    public abstract AbstractPokemon evolve();
}
