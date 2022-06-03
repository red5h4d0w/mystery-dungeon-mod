package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Quilava extends AbstractPokemon {

    public static final Color COLOR = Color.RED.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.CYNDAQUIL_RED;

    public Quilava(){
        super(COLOR, CARD_COLOR);
        evolution = new Typhlosion();
    }
}
