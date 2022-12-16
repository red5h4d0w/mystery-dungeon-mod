package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.AtStartOfTurnPostDrawInterface;



public class AtStartOfTurnPostDrawPatch
{
    @SpirePatch(clz = GameActionManager.class, method = "getNextAction", paramtypez = {})
    public static class AtStartOfTurnPostDraw
    {
        @SpireInsertPatch(rloc = 470-210)
        public static void atStartOfTurnPostDraw()
        {
            AbstractDungeon.player.hand.group.stream()
                    .filter(card -> card instanceof AtStartOfTurnPostDrawInterface)
                    .forEach(card -> ((AtStartOfTurnPostDrawInterface)card).atStartOfTurnPostDraw());
            AbstractDungeon.player.discardPile.group.stream()
                    .filter(card -> card instanceof AtStartOfTurnPostDrawInterface)
                    .forEach(card -> ((AtStartOfTurnPostDrawInterface)card).atStartOfTurnPostDraw());
            AbstractDungeon.player.drawPile.group.stream()
                    .filter(card -> card instanceof AtStartOfTurnPostDrawInterface)
                    .forEach(card -> ((AtStartOfTurnPostDrawInterface)card).atStartOfTurnPostDraw());
            
        }
    }
}