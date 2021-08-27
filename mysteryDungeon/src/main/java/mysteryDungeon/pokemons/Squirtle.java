package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;


import mysteryDungeon.cards.Squirtle.SquirtleDefend;
import mysteryDungeon.cards.Squirtle.SquirtleWaterGun;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.characters.Pokemon;

public class Squirtle extends AbstractPokemon {
    public static String NAME = "Squirtle";
    public static int MAX_HP = 35;
    public static int ORB_SLOTS = 0;
    public static AbstractCard[] STARTING_DECK = new AbstractCard[]{new SquirtleTackle(), new SquirtleTackle(), new SquirtleDefend(), new SquirtleDefend(), new SquirtleWaterGun()};
    public static Color COLOR = Color.BLUE;
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Squirtle.class.getSimpleName()+".png");
    
    public Squirtle(){
        super(NAME, MAX_HP, ORB_SLOTS, STARTING_DECK, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
    }
}
