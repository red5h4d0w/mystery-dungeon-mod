package mysteryDungeon.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.characters.Pokemon;

@SpirePatch2(clz = AbstractDungeon.class, method = "generateMap")
public class EvolutionPatch {

    public static Logger logger = LogManager.getLogger(EvolutionPatch.class);

    @SpirePostfixPatch
    public static void EvolvePokemon() {
        if(AbstractDungeon.player!=null) {
            if(AbstractDungeon.player instanceof Pokemon)
                ((Pokemon)AbstractDungeon.player).evolvePokemons();
        }
        
    }
}
