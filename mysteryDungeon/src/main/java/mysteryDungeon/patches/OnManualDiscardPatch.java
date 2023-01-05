package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.actions.unique.GamblingChipAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.actions.ScaldAction;
import mysteryDungeon.interfaces.onManualDiscardInterface;



public class OnManualDiscardPatch
{
    @SpirePatch(clz = DiscardAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard
    {
        @SpireInsertPatch(rloc = 55-42, localvars = {"c"})
        public static void onManualDiscard(DiscardAction __instance, boolean ___endTurn, AbstractCard c)
        {
            if(!___endTurn) {
                AbstractDungeon.player.powers.stream()
                    .filter(power -> power instanceof onManualDiscardInterface)
                    .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(c));
                AbstractDungeon.player.hand.group.stream()
                    .filter(card -> card instanceof onManualDiscardInterface)
                    .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(c));
            }  
        }
    }
    @SpirePatch(clz = DiscardAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard2
    {
        @SpireInsertPatch(rlocs = {70-42, 97-42}, localvars = {"c"})
        public static void onManualDiscard(DiscardAction __instance, AbstractCard c)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onManualDiscardInterface)
                .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(c));
            AbstractDungeon.player.hand.group.stream()
                .filter(card -> card instanceof onManualDiscardInterface)
                .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(c));
        }
    }
    @SpirePatch(clz = DiscardSpecificCardAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard3
    {
        @SpireInsertPatch(rloc = 37-30)
        public static void onManualDiscard(DiscardSpecificCardAction __instance, AbstractCard ___targetCard)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onManualDiscardInterface)
                .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(___targetCard));
            AbstractDungeon.player.hand.group.stream()
                .filter(card -> card instanceof onManualDiscardInterface)
                .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(___targetCard));
        }
    }

    @SpirePatch(clz = ScrapeFollowUpAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard4
    {
        @SpireInsertPatch(rloc = 26-18, localvars = {"c"})
        public static void onManualDiscard(ScrapeFollowUpAction __instance, AbstractCard c)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onManualDiscardInterface)
                .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(c));
            AbstractDungeon.player.hand.group.stream()
                .filter(card -> card instanceof onManualDiscardInterface)
                .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(c));
        }
    }

    @SpirePatch(clz = GamblingChipAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard5
    {
        @SpireInsertPatch(rloc = 57-35, localvars = {"c"})
        public static void onManualDiscard(GamblingChipAction __instance, AbstractCard c)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onManualDiscardInterface)
                .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(c));
            AbstractDungeon.player.hand.group.stream()
                .filter(card -> card instanceof onManualDiscardInterface)
                .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(c));
        }
    }

    @SpirePatch(clz = ScaldAction.class, method = "update", paramtypez = {})
    public static class OnManualDiscard6
    {
        @SpireInsertPatch(rloc = 58-40, localvars = {"c"})
        public static void onManualDiscard(ScaldAction __instance, AbstractCard c)
        {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onManualDiscardInterface)
                .forEach(power -> ((onManualDiscardInterface)power).onManualDiscard(c));
            AbstractDungeon.player.hand.group.stream()
                .filter(card -> card instanceof onManualDiscardInterface)
                .forEach(card -> ((onManualDiscardInterface)card).onManualDiscard(c));
        }
    }
}