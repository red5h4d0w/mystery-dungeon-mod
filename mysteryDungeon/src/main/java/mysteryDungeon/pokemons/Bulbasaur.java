package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import mysteryDungeon.cards.Bulbasaur.BulbasaurDefend;
import mysteryDungeon.cards.Bulbasaur.BulbasaurLeechSeed;
import mysteryDungeon.cards.Bulbasaur.BulbasaurTackle;
import mysteryDungeon.characters.Pokemon;

public class Bulbasaur extends AbstractPokemon {
    public Bulbasaur(){
        name = "Bulbasaur";
        maxHp = 35;
        orbSlots = 1;
        startingDeck = new AbstractCard[]{new BulbasaurTackle(), new BulbasaurTackle(), new BulbasaurDefend(), new BulbasaurDefend(), new BulbasaurLeechSeed()};
        color = Color.GREEN;
        cardColor = Pokemon.Enums.BULBASAUR_GREEN;
    }
}
