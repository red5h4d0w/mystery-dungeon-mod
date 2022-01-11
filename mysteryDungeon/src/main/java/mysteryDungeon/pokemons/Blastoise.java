package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Blastoise extends AbstractPokemon {

    public static String NAME = "Blastoise";
    public static Color COLOR = Color.BLUE.cpy();
    public static CardColor CARD_COLOR = Pokemon.Enums.SQUIRTLE_BLUE;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Blastoise.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Blastoise.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Blastoise.class.getSimpleName());
    public Blastoise(){
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
