package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Bulbasaur.BulbasaurDefend;
import mysteryDungeon.cards.Bulbasaur.BulbasaurLeechSeed;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.characters.Pokemon;

public class Bulbasaur extends AbstractPokemon {

    public static final int MAX_HP = 40;
    public static final int ORB_SLOTS = 1;
    public static final AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new BulbasaurTackle(), new BulbasaurTackle(), new BulbasaurDefend(), new BulbasaurDefend(), new BulbasaurLeechSeed()
    };
    public static final Color COLOR = Color.GREEN.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    public Bulbasaur(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Ivysaur();
    }

}
