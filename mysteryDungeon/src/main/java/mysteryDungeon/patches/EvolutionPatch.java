package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.characters.Pokemon;

@SpirePatch(clz = AbstractDungeon.class, method = "generateMap")
public class EvolutionPatch {

    @SpirePostfixPatch
    public static void EvolvePokemon() {
        if(AbstractDungeon.player!=null) {
            if(AbstractDungeon.player instanceof Pokemon)
                ((Pokemon)AbstractDungeon.player).tryToEvolvePokemons();
        }
        
    }
}
