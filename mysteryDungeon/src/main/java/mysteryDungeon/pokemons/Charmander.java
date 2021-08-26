package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import mysteryDungeon.cards.Charmander.CharmanderDefend;
import mysteryDungeon.cards.Charmander.CharmanderEmber;
import mysteryDungeon.cards.Charmander.CharmanderScratch;
import mysteryDungeon.characters.Pokemon;

public class Charmander extends AbstractPokemon {
    public Charmander(){
        name = "Charmander";
        maxHp = 30;
        orbSlots = 1;
        startingDeck = new AbstractCard[]{new CharmanderScratch(), new CharmanderScratch(), new CharmanderDefend(), new CharmanderDefend(), new CharmanderEmber()};
        color = Color.RED;
        cardColor = Pokemon.Enums.CHARMANDER_RED;
    }
}
