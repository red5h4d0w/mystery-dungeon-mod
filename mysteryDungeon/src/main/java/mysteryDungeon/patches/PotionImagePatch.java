package mysteryDungeon.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.potions.RegenPotion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.ReflectionHacks;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;


public class PotionImagePatch {
    public Logger logger = LogManager.getLogger(PotionImagePatch.class);


    @SpirePatch(clz = AbstractPotion.class, method = "initializeImage")
    public static class ChangeImage {
        @SpirePostfixPatch
        @SuppressWarnings("all")
        public static void SubstituteImage(AbstractPotion __instance) {
            if(__instance instanceof RegenPotion) {
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "containerImg", TextureLoader.getTexture(MysteryDungeon.makePotionPath("oran-berry.png")));
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "outlineImg", TextureLoader.getTexture(MysteryDungeon.makePotionOutlinePath("oran-berry.png")));
            }
            else if(__instance instanceof FairyPotion) {
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "containerImg", TextureLoader.getTexture(MysteryDungeon.makePotionPath("revive.png")));
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "outlineImg", TextureLoader.getTexture(MysteryDungeon.makePotionOutlinePath("revive.png")));
            }
        }
    }
    @SpirePatch(clz = AbstractPotion.class, method = "initializeColor")
    public static class ChangeColor {
        @SpirePostfixPatch
        @SuppressWarnings("all")
        public static void SubstituteImage(AbstractPotion __instance) {
            if(__instance instanceof RegenPotion) {
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "liquidColor", Color.CLEAR);
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "hybridColor", null);
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "spotsColor", null);
            } else if(__instance instanceof FairyPotion) {
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "liquidColor", Color.CLEAR);
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "hybridColor", null);
                ReflectionHacks.setPrivate(__instance, AbstractPotion.class, "spotsColor", null);
            }
        }
    }

    
}
