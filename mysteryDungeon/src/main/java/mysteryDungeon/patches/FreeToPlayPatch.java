package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.FreeToPlayInterface;


public class FreeToPlayPatch
{
    @SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
    public static class PreRewardGeneration
    {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> freeToPlay(AbstractCard __instance)
        {
            if(CardCrawlGame.isInARun())
            {
                boolean relicMakesCardFree = AbstractDungeon.player.relics.stream()
                    .filter(relic -> relic instanceof FreeToPlayInterface)
                    .map(relic -> ((FreeToPlayInterface)relic).isCardFreeToPlay(__instance))
                    .reduce(false, (total, freeToPlay)->total||freeToPlay);
                boolean powerMakesCardFree = AbstractDungeon.player.powers.stream()
                    .filter(power -> power instanceof FreeToPlayInterface)
                    .map(power -> ((FreeToPlayInterface)power).isCardFreeToPlay(__instance))
                    .reduce(false, (total, freeToPlay)->total||freeToPlay);
                if(relicMakesCardFree||powerMakesCardFree)
                {
                    return SpireReturn.Return(true);
                }
            }
            
            return SpireReturn.Continue();
        }
    }

}
