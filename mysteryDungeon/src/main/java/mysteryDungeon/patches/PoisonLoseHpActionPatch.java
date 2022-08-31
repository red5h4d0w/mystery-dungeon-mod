package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.powers.AbstractPower;

import mysteryDungeon.powers.ToxicPower;

public class PoisonLoseHpActionPatch {

    @SpirePatch(
        clz = PoisonLoseHpAction.class,
        method = "update"
    )
    public static class ToxicPatch {
        @SpireInsertPatch(
            loc = 61,
            localvars = {"p"}
        )
        public static void Insert(PoisonLoseHpAction __instance, AbstractPower p){
            AbstractPower toxic = __instance.target.getPower(ToxicPower.POWER_ID);
            if(toxic != null) {
                p.amount+= toxic.amount+1;
            }
        }
    }
}
