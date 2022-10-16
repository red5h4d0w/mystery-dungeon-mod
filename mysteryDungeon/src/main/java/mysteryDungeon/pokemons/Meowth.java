package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Meowth.MeowthDefend;
import mysteryDungeon.cards.Meowth.MeowthScratch;
import mysteryDungeon.cards.Meowth.MeowthSnatch;
import mysteryDungeon.characters.Pokemon;

public class Meowth extends AbstractPokemon {

    public static final int MAX_HP = 35;
    public static final int ORB_SLOTS = 1;
    public static final AbstractCard[] STARTING_DECK = new AbstractCard[]{
        new MeowthScratch(), new MeowthScratch(), new MeowthDefend(), new MeowthDefend(), new MeowthSnatch()
    };
    public static final Color COLOR = Color.WHITE.cpy();
    public static final CardColor CARD_COLOR = Pokemon.Enums.MEOWTH_WHITE;

    public Meowth(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Persian();
    }
}
