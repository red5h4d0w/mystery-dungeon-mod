package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Bayleef extends AbstractPokemon {

    public static final Color COLOR = Color.GREEN.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.CHIKORITA_GREEN;

    public Bayleef(){
        super(COLOR, CARD_COLOR);
        evolution = new Meganium();
    }
}
