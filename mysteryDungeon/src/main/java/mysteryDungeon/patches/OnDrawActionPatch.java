package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.onDrawInterface;


public class OnDrawActionPatch
{
    private static boolean skipNextDrawCardAction = false;

    @SpirePatch(clz = DrawCardAction.class, method = "<ctor>", paramtypez = {AbstractCreature.class,int.class,boolean.class})
    public static class OnDraw
    {
        @SpirePostfixPatch
        public static void onDraw(DrawCardAction __instance)
        {
            if(!skipNextDrawCardAction&&__instance.amount<=AbstractDungeon.player.drawPile.size())
            {
                AbstractDungeon.player.powers.stream()
                    .filter(power -> power instanceof onDrawInterface)
                    .forEach(power -> ((onDrawInterface)power).onDraw());
                AbstractDungeon.player.relics.stream()
                    .filter(relic -> relic instanceof onDrawInterface)
                    .forEach(relic -> ((onDrawInterface)relic).onDraw());
            }
            else if(skipNextDrawCardAction)
            {
                skipNextDrawCardAction=false;
            }
            else if(__instance.amount>AbstractDungeon.player.drawPile.size())
            {
                skipNextDrawCardAction=true;
            }
            
        }
    }
}