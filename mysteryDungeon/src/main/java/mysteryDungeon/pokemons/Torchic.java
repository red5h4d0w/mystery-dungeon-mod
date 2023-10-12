package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.cards.Torchic.TorchicDefend;
import mysteryDungeon.cards.Torchic.TorchicFlameCharge;
import mysteryDungeon.cards.Torchic.TorchicScratch;
import mysteryDungeon.characters.Pokemon;

public class Torchic extends AbstractPokemon {

    // TODO: change stats
    public static int MAX_HP = 30;
    public static int ORB_SLOTS = 1;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[] {
        new TorchicScratch(), new TorchicScratch(), new TorchicDefend(), new TorchicDefend(), new TorchicFlameCharge()
    };
    public static Color COLOR = Color.RED.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.TORCHIC_RED;

    public Torchic(){
        super(MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR);
        evolution = new Combusken();
        complexity = 4;
    }
}
