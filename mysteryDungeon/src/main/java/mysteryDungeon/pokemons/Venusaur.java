package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Venusaur extends AbstractPokemon {

    public static Color COLOR = Color.GREEN.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    public Venusaur(){
        super(COLOR, CARD_COLOR);
        canEvolve = false;
    }

    @Override
    public AbstractPokemon evolve() {
        return null;
    }
}
