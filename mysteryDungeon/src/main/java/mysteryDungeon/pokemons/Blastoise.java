package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Blastoise extends AbstractPokemon {

    public static Color COLOR = Color.BLUE.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;

    public Blastoise(){
        super(COLOR, CARD_COLOR);
        canEvolve = false;
    }

    @Override
    public AbstractPokemon evolve() {
        return null;
    }
}
