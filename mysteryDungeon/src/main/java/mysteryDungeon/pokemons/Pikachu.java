package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;

import mysteryDungeon.cards.Pikachu.PikachuDefend;
import mysteryDungeon.cards.Pikachu.PikachuTackle;
import mysteryDungeon.cards.Pikachu.PikachuThundershock;
import mysteryDungeon.characters.Pokemon;

public class Pikachu extends AbstractPokemon {
    public Pikachu(){
        name = "Pikachu";
        maxHp = 30;
        orbSlots = 3;
        startingDeck = new AbstractCard[]{new PikachuTackle(), new PikachuTackle(), new PikachuDefend(), new PikachuDefend(), new PikachuThundershock()};
        color = Color.YELLOW;
        cardColor = Pokemon.Enums.PIKACHU_YELLOW;
    }
}
