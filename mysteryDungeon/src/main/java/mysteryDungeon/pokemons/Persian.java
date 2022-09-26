package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Persian extends AbstractPokemon {

    public static final Color COLOR = Color.WHITE.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.MEOWTH_WHITE;

    public Persian(){
        super(COLOR, CARD_COLOR);
    }
}
