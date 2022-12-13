package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Charmander.CharmanderDefend;
import mysteryDungeon.cards.Charmander.CharmanderEmber;
import mysteryDungeon.cards.Charmander.CharmanderScratch;
import mysteryDungeon.characters.Pokemon;

public class Charmander extends AbstractPokemon {
    
    public static final int MAX_HP = 30;
    public static final int ORB_SLOTS = 1;
    public static final AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new CharmanderScratch(), new CharmanderScratch(), new CharmanderDefend(), new CharmanderDefend(), new CharmanderEmber()
    };
    public static final Color COLOR = Color.RED.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.CHARMANDER_RED;

    public Charmander(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Charmeleon();
        complexity = 2;
    }
}
