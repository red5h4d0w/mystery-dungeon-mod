package mysteryDungeon.pokemons;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Pikachu.PikachuDefend;
import mysteryDungeon.cards.Pikachu.PikachuTackle;
import mysteryDungeon.cards.Pikachu.PikachuThundershock;
import mysteryDungeon.characters.Pokemon;



public class Pikachu extends AbstractPokemon {

    public static int MAX_HP = 25;
    public static int ORB_SLOTS = 2;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new PikachuTackle(), new PikachuTackle(), new PikachuDefend(), new PikachuDefend(), new PikachuThundershock()
    };
    public static Color COLOR = Color.YELLOW.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    
    
    public Pikachu(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
    }

    @Override
    public AbstractPokemon evolve() {
        AbstractPokemon evolution = new Raichu();
        if (shiny)
            evolution.shiny = true;
        return evolution;
    }
}
