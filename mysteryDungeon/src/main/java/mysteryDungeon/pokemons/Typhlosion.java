package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Typhlosion extends AbstractPokemon {

    public static final Color COLOR = Color.RED.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.CYNDAQUIL_RED;

    public Typhlosion(){
        super(COLOR, CARD_COLOR);
    }
}
