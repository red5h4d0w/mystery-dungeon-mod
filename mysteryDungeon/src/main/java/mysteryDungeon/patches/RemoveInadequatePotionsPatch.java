package mysteryDungeon.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import mysteryDungeon.abstracts.PokemonPotion;
import mysteryDungeon.characters.Pokemon;


public class RemoveInadequatePotionsPatch
{
    @SpirePatch(clz = PotionHelper.class, method = "getPotions", paramtypez = {AbstractPlayer.PlayerClass.class, boolean.class})
    public static class PreRewardGeneration
    {
        @SpirePostfixPatch
        public static ArrayList<String> removeInadequatePotions(ArrayList<String> __retval, PotionHelper __instance)
        {
            if(AbstractDungeon.player instanceof Pokemon) {
                Pokemon player = ((Pokemon)AbstractDungeon.player);
                if(player.hasChosenStarters()) {
                    ArrayList<String> toRemove = new ArrayList<String>();
                    for(String potionId: __retval) {
                        if(PotionHelper.getPotion(potionId) instanceof PokemonPotion) {
                            CardColor cardColor = ((PokemonPotion)PotionHelper.getPotion(potionId)).cardColor;
                            if(cardColor!=player.adventurer.cardColor && cardColor!=player.partner.cardColor) {
                                toRemove.add(potionId);
                            }
                        }
                    }
                    for(String idToRemove: toRemove) {
                        __retval.remove(idToRemove);
                    }
                }
            }
            
            return __retval;
        }
    }

}
