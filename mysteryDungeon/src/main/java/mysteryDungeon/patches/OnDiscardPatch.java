package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.onDiscardInterface;



public class OnDiscardPatch
{
    @SpirePatch(clz = CardGroup.class, method = "moveToDiscardPile", paramtypez = {AbstractCard.class})
    public static class AddedCardPatch
    {
        @SpirePostfixPatch
        public static void onDiscard()
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onDiscardInterface)
                .forEach(power -> ((onDiscardInterface)power).onDiscard());
            AbstractDungeon.player.relics.stream()
                .filter(relic -> relic instanceof onDiscardInterface)
                .forEach(relic -> ((onDiscardInterface)relic).onDiscard());
            AbstractDungeon.player.hand.group.stream()
                .filter(relic -> relic instanceof onDiscardInterface)
                .forEach(relic -> ((onDiscardInterface)relic).onDiscard());
            AbstractDungeon.player.discardPile.group.stream()
                .filter(relic -> relic instanceof onDiscardInterface)
                .forEach(relic -> ((onDiscardInterface)relic).onDiscard());
            AbstractDungeon.player.drawPile.group.stream()
                .filter(relic -> relic instanceof onDiscardInterface)
                .forEach(relic -> ((onDiscardInterface)relic).onDiscard());
        }
    }
}