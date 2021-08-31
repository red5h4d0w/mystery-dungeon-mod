package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


import mysteryDungeon.interfaces.onCardScriedInterface;



public class OnCardScriedPatch
{
    @SpirePatch(clz = ScryAction.class, method = "update", paramtypez = {})
    public static class OnCardScried
    {
        @SpireInsertPatch(rloc = 61-34)
        public static void onCardScried()
        {
            for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards)
            {
                AbstractDungeon.player.powers.stream()
                    .filter(power -> power instanceof onCardScriedInterface)
                    .forEach(power -> ((onCardScriedInterface)power).onCardScried(c));
                AbstractDungeon.player.relics.stream()
                    .filter(relic -> relic instanceof onCardScriedInterface)
                    .forEach(relic -> ((onCardScriedInterface)relic).onCardScried(c));
                AbstractDungeon.player.hand.group.stream()
                    .filter(card -> card instanceof onCardScriedInterface)
                    .forEach(card -> ((onCardScriedInterface)card).onCardScried(c));
                AbstractDungeon.player.discardPile.group.stream()
                    .filter(card -> card instanceof onCardScriedInterface)
                    .forEach(card -> ((onCardScriedInterface)card).onCardScried(c));
                AbstractDungeon.player.drawPile.group.stream()
                    .filter(card -> card instanceof onCardScriedInterface)
                    .forEach(card -> ((onCardScriedInterface)card).onCardScried(c));
            }
            
        }
    }
}