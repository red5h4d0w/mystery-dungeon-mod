package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Combusken extends AbstractPokemon {

    public static Color COLOR = Color.RED.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.TORCHIC_RED;

    public Combusken(){
        super(COLOR, CARD_COLOR);
        evolution = new Blaziken();
    }
}
