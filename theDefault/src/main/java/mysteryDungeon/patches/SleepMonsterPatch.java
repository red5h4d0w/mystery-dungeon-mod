package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import mysteryDungeon.powers.SleepPowderPower;

public class SleepMonsterPatch {
    @SpirePatch(
            clz = GameActionManager.class,
            method = "getNextAction"
    )
    public static class GetNextAction {
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                @Override
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals("com.megacrit.cardcrawl.monsters.AbstractMonster")
                            && m.getMethodName().equals("takeTurn")) {
                        m.replace("if (!m.hasPower(mysteryDungeon.powers.SleepPowderPower.POWER_ID)) {" +
                                "$_ = $proceed($$);" +
                                "}");
                    }
                }
            };
        }
    }

    @SpirePatch(
            clz = AbstractMonster.class,
            method = "rollMove"
    )
    public static class RollMove {
        public static SpireReturn<Void> Prefix(AbstractMonster __instance) {
            if (__instance.hasPower(SleepPowderPower.POWER_ID)) {
                return SpireReturn.Return(null);
            } else {
                return SpireReturn.Continue();
            }
        }
    }
}