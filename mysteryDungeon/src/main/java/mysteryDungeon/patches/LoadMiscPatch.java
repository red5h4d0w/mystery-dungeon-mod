package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import javassist.CtBehavior;
import mysteryDungeon.interfaces.onLoadCardMiscInterface;

public class LoadMiscPatch {
    @SpirePatch(
            clz = CardLibrary.class,
            method = "getCopy",
            paramtypez = { String.class, int.class, int.class }
    )
    public static class MiscPatch
    {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = { "retVal" }
        )
        public static void LoadMisc(String key, int upgradeTime, int misc, AbstractCard retVal)
        {
            if(retVal instanceof onLoadCardMiscInterface) {
                ((onLoadCardMiscInterface)retVal).onLoadCardMisc(misc);
            }
        }

        private static class Locator extends SpireInsertLocator
        {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception
            {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "misc");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }   
}
