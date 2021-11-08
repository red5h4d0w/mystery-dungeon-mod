package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Raichu extends AbstractPokemon {

    public static String NAME = "Raichu";
    public static Color COLOR = Color.YELLOW;
    public static CardColor CARD_COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Raichu.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Raichu.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Raichu.class.getSimpleName());
    public Raichu(){
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
