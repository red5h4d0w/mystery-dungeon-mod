package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Bulbasaur.BulbasaurDefend;
import mysteryDungeon.cards.Bulbasaur.BulbasaurLeechSeed;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.characters.Pokemon;

public class Mudkip extends AbstractPokemon {
    public static String NAME = "Bulbasaur";
    public static int MAX_HP = 35;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new BulbasaurTackle(), new BulbasaurTackle(), new BulbasaurDefend(), new BulbasaurDefend(), new BulbasaurLeechSeed()};
    public static Color COLOR = Color.GREEN;
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Bulbasaur.class.getSimpleName()+".png");

    public Mudkip(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Ivysaur();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
