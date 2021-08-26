package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import mysteryDungeon.cards.Squirtle.SquirtleDefend;
import mysteryDungeon.cards.Squirtle.SquirtleWaterGun;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.characters.Pokemon;

public class Squirtle extends AbstractPokemon {
    public Squirtle(){
        name = "Squirtle";
        maxHp = 32;
        orbSlots = 0;
        startingDeck = new AbstractCard[]{new SquirtleTackle(), new SquirtleTackle(), new SquirtleDefend(), new SquirtleDefend(), new SquirtleWaterGun()};
        color = Color.BLUE;
        cardColor = Pokemon.Enums.SQUIRTLE_BLUE;
    }
}
