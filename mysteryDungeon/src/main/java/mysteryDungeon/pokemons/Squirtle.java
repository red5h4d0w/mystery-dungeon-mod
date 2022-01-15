package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;


import mysteryDungeon.cards.Squirtle.SquirtleDefend;
import mysteryDungeon.cards.Squirtle.SquirtleWaterGun;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.characters.Pokemon;

public class Squirtle extends AbstractPokemon {

    public static int MAX_HP = 35;
    public static int ORB_SLOTS = 0;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new SquirtleTackle(), new SquirtleTackle(), new SquirtleDefend(), new SquirtleDefend(), new SquirtleWaterGun()
    };
    public static Color COLOR = Color.BLUE.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;
    
    public Squirtle(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Wartortle();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
