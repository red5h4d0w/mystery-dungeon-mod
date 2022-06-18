package mysteryDungeon.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.abstracts.PokemonPotion;
import mysteryDungeon.characters.Pokemon;


public class RemoveInadequatePotionsPatch {
    public static final Logger logger = LogManager.getLogger(RemoveInadequatePotionsPatch.class.getName());
    @SpirePatch(clz = PotionHelper.class, method = "getPotions")
    public static class PreRewardGeneration {
        @SpirePostfixPatch
        public static ArrayList<String> removeInadequatePotions(ArrayList<String> __retval, PlayerClass c, boolean getAll) {
            if(AbstractDungeon.player instanceof Pokemon && !getAll) {
                Pokemon player = ((Pokemon)AbstractDungeon.player);
                if(player.hasChosenStartersForSave()) {
                    ArrayList<String> toRemove = new ArrayList<String>();
                    for(String potionId: __retval) {
                        if(PotionHelper.getPotion(potionId) instanceof PokemonPotion) {
                            CardColor cardColor = ((PokemonPotion)PotionHelper.getPotion(potionId)).cardColor;
                            if(cardColor!=Pokemon.adventurerToSave.cardColor && cardColor!=Pokemon.partnerToSave.cardColor) {
                                toRemove.add(potionId);
                                logger.info("Je veux enlever:");
                                logger.info(potionId);
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
