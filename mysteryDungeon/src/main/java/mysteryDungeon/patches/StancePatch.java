package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.stances.AbstractStance;

import mysteryDungeon.stances.NegativeStance;
import mysteryDungeon.stances.PositiveStance;

public class StancePatch {
    @SpirePatch(clz = AbstractStance.class, method = "getStanceFromName")
    public static class StanceNamePatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractStance> returnStance(String name) {
        if (name.equals(PositiveStance.STANCE_ID))
            return SpireReturn.Return(new PositiveStance());
        if (name.equals(NegativeStance.STANCE_ID))
            return SpireReturn.Return(new NegativeStance());
        return SpireReturn.Continue();
        }
    }
}