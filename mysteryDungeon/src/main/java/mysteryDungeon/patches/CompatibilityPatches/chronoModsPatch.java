package mysteryDungeon.patches.CompatibilityPatches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.neow.NeowEvent;


import chronoMods.coop.CoopNeowEvent;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.patches.PokemonNeowPatch;

public class chronoModsPatch {

    @SpirePatch(clz = CoopNeowEvent.ControlNeowEvent.class, method = "blessing", requiredModId = "chronoMods")
    public static class SkipBlessingPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> removeBlessing(NeowEvent __instance) {
            if((AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(clz = CoopNeowEvent.ControlNeowEvent.class, method = "Prefix", requiredModId = "chronoMods")
    public static class ChronoNeowPatch {

        @SpireInsertPatch(rloc = 183-180)
        public static SpireReturn<SpireReturn<Void>> bringToPokemonsNeow(NeowEvent __instance, int buttonPressed) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return(SpireReturn.Continue());
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = CoopNeowEvent.NeowUpdateAdditions.class, method = "Postfix", requiredModId = "chronoMods")
    public static class DoNotUpdateAdditions {

        @SpireInsertPatch(rloc = 147-146)
        public static SpireReturn<Void> skip(NeowEvent __instance) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = CoopNeowEvent.NeowLinkRender.class, method = "Postfix", requiredModId = "chronoMods")
    public static class removeLinkIcon {

        @SpirePrefixPatch()
        public static SpireReturn<Void> removeIfStillChoosingPokemon(RoomEventDialog __instance, SpriteBatch sb) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}
