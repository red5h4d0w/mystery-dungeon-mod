package mysteryDungeon.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

import mysteryDungeon.interfaces.onCreateTempCardInterface;


public class OnCreateTempCardPatch {
    @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class})
    @SpirePatch2(clz = ShowCardAndAddToDiscardEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class})
    @SpirePatch2(clz = ShowCardAndAddToHandEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class})
    @SpirePatch2(clz = ShowCardAndAddToHandEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class})
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class})
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method = "<ctor>", paramtypez = {AbstractCard.class, boolean.class, boolean.class})
    public static class AddedCardPatch {
        @SpirePostfixPatch
        public static void onCreateTempCard(AbstractCard ___card) {
            AbstractDungeon.player.powers.stream()
                .filter(power -> power instanceof onCreateTempCardInterface)
                .forEach(power -> ((onCreateTempCardInterface)power).onCreateTempCard(___card));
            AbstractDungeon.player.relics.stream()
                .filter(relic -> relic instanceof onCreateTempCardInterface)
                .forEach(relic -> ((onCreateTempCardInterface)relic).onCreateTempCard(___card));
        }
    }
}