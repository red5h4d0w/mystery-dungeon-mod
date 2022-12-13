package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Cyndaquil.CyndaquilDefend;
import mysteryDungeon.cards.Cyndaquil.CyndaquilFlameWheel;
import mysteryDungeon.cards.Cyndaquil.CyndaquilTackle;
import mysteryDungeon.characters.Pokemon;

public class Cyndaquil extends AbstractPokemon {
    
    public static int MAX_HP = 30;
    public static int ORB_SLOTS = 0;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new CyndaquilTackle(), new CyndaquilTackle(), new CyndaquilDefend(), new CyndaquilDefend(), new CyndaquilFlameWheel()
    };
    public static Color COLOR = Color.RED.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.CYNDAQUIL_RED;

    public Cyndaquil(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Quilava();
        complexity = 3;
    }
}
