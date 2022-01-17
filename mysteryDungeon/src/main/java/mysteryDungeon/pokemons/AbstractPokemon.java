package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;
import static mysteryDungeon.MysteryDungeon.makeID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

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
    public boolean shiny;
    public boolean canEvolve = true;

    public AbstractPokemon(int maxHp, int orbSlots, AbstractCard[] startingDeck, Color color, CardColor cardColor) {
        this.ID = makeID(getClass().getSimpleName());
        this.name = localizedName();
        this.maxHp = maxHp;
        this.orbSlots = orbSlots;
        this.startingDeck = startingDeck;
        this.color = color;
        this.cardColor = cardColor;
        if(MysteryDungeon.TOGGLE_ON_SHINY) {
            this.shiny = true;
        }
        else {
            this.shiny = AbstractDungeon.miscRng.randomBoolean(1.0F/100.0F);
        }
        this.pathToBackSprite = makeBackSpritePath(getClass().getSimpleName(), shiny);
        this.skeletonUrl = makeSkeletonPath(getClass().getSimpleName(), shiny);
        this.atlasUrl = makeAtlasPath(getClass().getSimpleName(), shiny);
        generateBackSprite();
    }

    public AbstractPokemon(Color color, CardColor cardColor) {
        this(0, 0, new AbstractCard[]{}, color, cardColor);
    }

    public String localizedName() {
        return CardCrawlGame.languagePack.getCharacterString(ID).NAMES[0];
    }

    public void generateBackSprite() {
        if(shiny) {
            backSprite = new Pixmap(Gdx.files.internal(pathToBackSprite));
        }
        else {
            backSprite = new Pixmap(Gdx.files.internal(pathToBackSprite));
        }
    }

    public abstract AbstractPokemon evolve();
}
