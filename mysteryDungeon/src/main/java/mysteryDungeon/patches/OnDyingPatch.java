package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.onDyingInterface;



public class OnDyingPatch
{
    @SpirePatch(clz = AbstractPlayer.class, method = "damage", paramtypez = {DamageInfo.class})
    public static class AddedCardPatch
    {
        @SpireInsertPatch(rloc=1873-1725)
        @SuppressWarnings("all")
        public static SpireReturn onDying(AbstractPlayer __instance)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onDyingInterface)
                .forEach(power -> ((onDyingInterface)power).onDying());
            AbstractDungeon.player.relics.stream()
                .filter(relic -> relic instanceof onDyingInterface)
                .forEach(relic -> ((onDyingInterface)relic).onDying());
            return SpireReturn.Return();
        }
    }
}