package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;

import java.util.Random;

import static mysteryDungeon.MysteryDungeon.makeAtlasPath;
import static mysteryDungeon.MysteryDungeon.makeID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import mysteryDungeon.MysteryDungeon;

public abstract class AbstractPokemon {
    
    public String ID;
    public String name;
    public int maxHp;
    public int orbSlots;
    public AbstractCard[] startingDeck;
    public Color color;
    public CardColor cardColor;
    public String pathToBackSprite;
    public Pixmap backSprite;
    public String skeletonUrl;
    public String atlasUrl;
    private boolean shiny;
    public AbstractPokemon evolution;

    public int complexity = 0;

    public AbstractPokemon(int maxHp, int orbSlots, AbstractCard[] startingDeck, Color color, CardColor cardColor) {
        this.ID = makeID(getClass().getSimpleName());
        this.name = localizedName();
        this.maxHp = maxHp;
        this.orbSlots = orbSlots;
        this.startingDeck = startingDeck;
        this.color = color.cpy();
        this.cardColor = cardColor;
        if(MysteryDungeon.TOGGLE_ON_SHINY) {
            setShiny(true);
        }
        else {
            setShiny((new Random()).nextInt(100)<8);
        }
    }

    public AbstractPokemon(Color color, CardColor cardColor) {
        this(0, 0, new AbstractCard[]{}, color, cardColor);
    }

    public String localizedName() {
        if(CardCrawlGame.languagePack.getCharacterString(ID)!=null) {
            return CardCrawlGame.languagePack.getCharacterString(ID).NAMES[0];
        }
        else {
            return "ERROR: POKÉMON NAME NOT FOUND";
        }
    }

    public void generateBackSprite() {
        backSprite = new Pixmap(Gdx.files.internal(pathToBackSprite));
    }

    public void setShiny(boolean shiny) {
        this.shiny = shiny;
        this.pathToBackSprite = makeBackSpritePath(getClass().getSimpleName(), shiny);
        this.skeletonUrl = makeSkeletonPath(getClass().getSimpleName(), shiny);
        this.atlasUrl = makeAtlasPath(getClass().getSimpleName(), shiny);
        generateBackSprite();
    }

    public boolean getShiny() {
        return shiny;
    }

    public AbstractPokemon evolve() {
        if(evolution!=null) {
            evolution.setShiny(getShiny());
        }
        return evolution;
    };
}
