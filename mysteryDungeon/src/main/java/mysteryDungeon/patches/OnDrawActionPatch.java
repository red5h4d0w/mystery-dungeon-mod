package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.interfaces.onDrawInterface;


public class OnDrawActionPatch
{
    private static int drawCardActionToSkip = 0;
    public static Logger logger = LogManager.getLogger(OnDrawActionPatch.class);

    @SpirePatch(clz = DrawCardAction.class, method = "<ctor>", paramtypez = {AbstractCreature.class,int.class,boolean.class})
    public static class OnDrawConstructor
    {
        @SpirePostfixPatch
        public static void onDraw(DrawCardAction __instance)
        {
            logger.info("draw Pile has "+ AbstractDungeon.player.drawPile.size());
            logger.info("drawing "+ __instance.amount);
            logger.info(drawCardActionToSkip);
            if(drawCardActionToSkip==0)
            {
                AbstractDungeon.player.powers.stream()
                    .filter(power -> power instanceof onDrawInterface)
                    .forEach(power -> ((onDrawInterface)power).onDraw());
                AbstractDungeon.player.relics.stream()
                    .filter(relic -> relic instanceof onDrawInterface)
                    .forEach(relic -> ((onDrawInterface)relic).onDraw());
            }
            if(drawCardActionToSkip>0)
            {
                drawCardActionToSkip--;
            }            
        }
    }
    @SpirePatch(clz = DrawCardAction.class, method = "update", paramtypez = {})
    public static class OnDrawUpdate
    {
        @SpireInsertPatch(rloc = 121-74, localvars = {"deckSize"})
        public static void onDraw(DrawCardAction __instance, int deckSize)
        {
            drawCardActionToSkip++;
            if(deckSize!=0)
                drawCardActionToSkip++;           
        }
    }
}