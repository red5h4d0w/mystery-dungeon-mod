package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Chikorita.ChikoritaDefend;
import mysteryDungeon.cards.Chikorita.ChikoritaRazorLeaf;
import mysteryDungeon.cards.Chikorita.ChikoritaTackle;
import mysteryDungeon.characters.Pokemon;

public class Chikorita extends AbstractPokemon {
    
    public static int MAX_HP = 40;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{
        new ChikoritaTackle(), new ChikoritaTackle(), new ChikoritaDefend(), new ChikoritaDefend(), new ChikoritaRazorLeaf()
    };
    public static Color COLOR = Color.GREEN.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.CHIKORITA_GREEN;

    public Chikorita(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Bayleef();
        complexity = 3;
    }
}
