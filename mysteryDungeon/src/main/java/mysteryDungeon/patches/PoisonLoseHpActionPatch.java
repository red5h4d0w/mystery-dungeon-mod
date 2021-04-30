package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PoisonLoseHpActionPatch {

    private static final Logger logger = LogManager.getLogger(PoisonLoseHpActionPatch.class.getName());

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
            AbstractPower toxic = __instance.target.getPower("mysteryDungeon:ToxicPower");
            if(toxic != null)
            {
                logger.info(toxic.amount);
                p.amount+= toxic.amount+1;
            }
        }
    }
}
