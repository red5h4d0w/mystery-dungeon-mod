package mysteryDungeon.patches.CompatibilityPatches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.neow.NeowEvent;

import chronoMods.coop.CoopNeowEvent;
import mysteryDungeon.patches.PokemonNeowPatch;

public class chronoModsPatch {

    @SpirePatch(clz = CoopNeowEvent.class, method = "Prefix", requiredModId = "chronoMods", paramtypez = {
            NeowEvent.class, boolean.class })
    public static class ChronoNeowPatch {
        
        @SuppressWarnings("all")
        @SpireInsertPatch(rloc = 88 - 84)
        public static SpireReturn bringToPokemonsNeow(CoopNeowEvent __instance, NeowEvent neow, boolean isDone) {
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }

    }
}
