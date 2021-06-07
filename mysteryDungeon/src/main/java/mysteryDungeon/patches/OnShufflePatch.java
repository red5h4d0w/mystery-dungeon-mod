package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.onShufflePowerInterface;


public class OnShufflePatch
{
    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "<ctor>")
    public static class EmptyDeckShuffle
    {
        @SpireInsertPatch( rloc = 40 - 28)
        public static void activateOnShufflePowers()
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onShufflePowerInterface)
                .forEach(power -> ((onShufflePowerInterface)power).onShufflePower());
        }
    }

    @SpirePatch(clz = ShuffleAllAction.class, method = "<ctor>")
    public static class ShuffleAll
    {
        @SpireInsertPatch( rloc = 42 - 30)
        public static void activateOnShufflePowers()
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onShufflePowerInterface)
                .forEach(power -> ((onShufflePowerInterface)power).onShufflePower());
        }
    }

    @SpirePatch(clz = ShuffleAction.class, method = "update")
    public static class Shuffle
    {
        @SpireInsertPatch( rloc = 27 - 26)
        public static void activateOnShufflePowers()
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onShufflePowerInterface)
                .forEach(power -> ((onShufflePowerInterface)power).onShufflePower());
        }
    }
}
