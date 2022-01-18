package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Raichu extends AbstractPokemon {

    public static Color COLOR = Color.YELLOW.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    
    public Raichu(){
        super(COLOR, CARD_COLOR);
        canEvolve = false;
    }

    @Override
    public AbstractPokemon evolve() {
        return null;
    }
}
