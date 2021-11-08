package mysteryDungeon.pokemons;

import static mysteryDungeon.MysteryDungeon.makeBackSpritePath;
import static mysteryDungeon.MysteryDungeon.makeSkeletonPath;
import static mysteryDungeon.MysteryDungeon.makeAtlasPath;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;

import mysteryDungeon.characters.Pokemon;

public class Venusaur extends AbstractPokemon {

    public static String NAME = "Venusaur";
    public static Color COLOR = Color.GREEN;
    public static CardColor CARD_COLOR = Pokemon.Enums.BULBASAUR_GREEN;
    public static String PATH_TO_BACK_SPRITE = makeBackSpritePath(Venusaur.class.getSimpleName()+".png");
    public static String SKELETON_URL = makeSkeletonPath(Venusaur.class.getSimpleName());
    public static String ATLAS_URL = makeAtlasPath(Venusaur.class.getSimpleName());
    public Venusaur(){
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
