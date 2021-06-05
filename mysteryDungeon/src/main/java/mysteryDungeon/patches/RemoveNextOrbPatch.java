package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.PostRemoveNextOrbInterface;


public class RemoveNextOrbPatch
{
    @SpirePatch(clz = AbstractPlayer.class, method = "removeNextOrb")
    public static class AfterOrbIsRemoved
    {
        @SpireInsertPatch( rloc = 2830 - 2819)
        public static void removeNextOrb()
        {
            AbstractDungeon.player.relics.stream()
                .filter(relic -> relic instanceof PostRemoveNextOrbInterface)
                .forEach(relic -> ((PostRemoveNextOrbInterface)relic).postRemoveNextOrb());
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof PostRemoveNextOrbInterface)
                .forEach(power -> ((PostRemoveNextOrbInterface)power).postRemoveNextOrb());
        }
    }

}
