package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Wartortle extends AbstractPokemon {

    public static Color COLOR = Color.BLUE.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;

    public Wartortle(){
        super(COLOR, CARD_COLOR);
        canEvolve = true;
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Blastoise();
        if (getShiny())
            evolution.setShiny(true);
        return evolution;
    }
}
