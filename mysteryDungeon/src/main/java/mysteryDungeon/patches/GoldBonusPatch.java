package mysteryDungeon.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import mysteryDungeon.interfaces.GoldBonusInterface;


public class GoldBonusPatch
{
    @SpirePatch(clz = RewardItem.class, method = "applyGoldBonus")
    public static class PreRewardGeneration
    {
        @SpireInsertPatch(rloc = 123 - 116, localvars = {"tmp"})
        public static void preRewards(RewardItem __instance, boolean theft, int tmp)
        {
            if (!AbstractDungeon.loading_post_combat)
            {
                AbstractDungeon.player.relics.stream().filter(relic -> relic instanceof GoldBonusInterface).forEach(relic -> {__instance.bonusGold += MathUtils.round(tmp * ((GoldBonusInterface)relic).goldBonusPercentage());});
                AbstractDungeon.player.powers.stream().filter(power -> power instanceof GoldBonusInterface).forEach(power -> {__instance.bonusGold += MathUtils.round(tmp * ((GoldBonusInterface)power).goldBonusPercentage());});
            }
        }
    }

}
