package mysteryDungeon.patches;

import java.util.ArrayList;
import java.util.stream.Collectors;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import mysteryDungeon.interfaces.BetterOnGainBlockInterface;

@SpirePatch(clz = AbstractMonster.class, method="addBlock")
public class BetterOnGainBlockPatch {

    @SpireInsertPatch(rloc=523-479, localvars="tmp")
    public static void ActivatePowersAndRelics(AbstractMonster __instance, float tmp) {
        int blockAmount = MathUtils.floor(tmp);
        for (AbstractRelic relic: AbstractDungeon.player.relics) {
            if(relic instanceof BetterOnGainBlockInterface) {
                ((BetterOnGainBlockInterface)relic).betterOnGainBlock(__instance, blockAmount);
            }
        }
        for (AbstractPower power: AbstractDungeon.player.powers) {
            if(power instanceof BetterOnGainBlockInterface) {
                ((BetterOnGainBlockInterface)power).betterOnGainBlock(__instance, blockAmount);
            }
        }
        for (AbstractPower power: AbstractDungeon.getMonsters().monsters.stream()
            .filter( monster -> !monster.isDeadOrEscaped())
            .flatMap( monster -> monster.powers.stream())
            .collect(Collectors.toCollection(ArrayList::new))) {
            if(power instanceof BetterOnGainBlockInterface) {
                ((BetterOnGainBlockInterface)power).betterOnGainBlock(__instance, blockAmount);
            }
        }
    }
}
