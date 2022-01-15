package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Ivysaur extends AbstractPokemon {

    public static Color COLOR = Color.GREEN.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    public Ivysaur(){
        super(COLOR, CARD_COLOR);
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Venusaur();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
