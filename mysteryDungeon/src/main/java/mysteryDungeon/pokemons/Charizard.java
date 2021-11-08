package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Charizard extends AbstractPokemon {

    public static String NAME = "Charizard";
    public static Color COLOR = Color.RED;
    public static CardColor CARD_COLOR = Pokemon.Enums.CHARMANDER_RED;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Charizard.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Charizard.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Charizard.class.getSimpleName());
    public Charizard(){
        super(NAME, COLOR, CARD_COLOR, PATH_TO_BACK_SPRITE);
        skeletonUrl = SKELETON_URL;
        atlasUrl = ATLAS_URL;
        canEvolve = false;
    }

    @Override
    public AbstractPokemon evolve() {
        return null;
    }
}
