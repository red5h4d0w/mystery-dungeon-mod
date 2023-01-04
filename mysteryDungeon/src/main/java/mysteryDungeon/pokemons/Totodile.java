package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Totodile.TotodileDefend;
import mysteryDungeon.cards.Totodile.TotodileIceFang;
import mysteryDungeon.cards.Totodile.TotodileScratch;
import mysteryDungeon.characters.Pokemon;

public class Totodile extends AbstractPokemon {

    public static int MAX_HP = 45;
    public static int ORB_SLOTS = 2;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{
        new TotodileScratch(), new TotodileScratch(), new TotodileDefend(), new TotodileDefend(), new TotodileIceFang()
    };
    public static final Color COLOR = Color.BLUE.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.TOTODILE_BLUE;

    public Totodile() {
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        complexity = 2;
        evolution = new Croconaw();
    }
}
