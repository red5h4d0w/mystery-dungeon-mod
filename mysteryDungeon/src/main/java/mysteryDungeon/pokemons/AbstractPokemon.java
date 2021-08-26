package mysteryDungeon.pokemons;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

public abstract class AbstractPokemon {
    public String name;
    public int maxHp;
    public int orbSlots;
    public AbstractCard[] startingDeck;
    public Color color;
    public CardColor cardColor;
}
