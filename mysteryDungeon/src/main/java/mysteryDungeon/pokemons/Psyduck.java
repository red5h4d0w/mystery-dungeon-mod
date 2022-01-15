package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Bulbasaur.BulbasaurDefend;
import mysteryDungeon.cards.Bulbasaur.BulbasaurLeechSeed;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.characters.Pokemon;

public class Psyduck extends AbstractPokemon {

    public static int MAX_HP = 35;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new BulbasaurTackle(), new BulbasaurTackle(), new BulbasaurDefend(), new BulbasaurDefend(), new BulbasaurLeechSeed()
    };
    public static Color COLOR = Color.GREEN.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    public Psyduck(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
    }
    
    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Raichu();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
