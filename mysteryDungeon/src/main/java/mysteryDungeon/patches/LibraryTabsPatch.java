package mysteryDungeon.patches;

import basemod.ReflectionHacks;
import basemod.patches.com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar.ColorTabBarFix;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import mysteryDungeon.MysteryDungeon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.mainMenu.ColorTabBar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch(clz = ColorTabBarFix.Render.class, method = "Insert")
public class LibraryTabsPatch {
    public static final Logger logger = LogManager.getLogger(LibraryTabsPatch.class.getName());


    @SpireInsertPatch(locator = Locator.class)
    public static void Insert(ColorTabBar __instance, SpriteBatch sb, float y, ColorTabBar.CurrentTab curTab, @ByRef String[] ___tabName, int ___i) {
        ArrayList<ColorTabBarFix.ModColorTab> modTabs = ReflectionHacks.getPrivateStatic(ColorTabBarFix.Fields.class, "modTabs");
        String pokemonName = modTabs.get(___i).color.name().split("_")[0].substring(0, 1).toUpperCase()+modTabs.get(___i).color.name().split("_")[0].substring(1).toLowerCase();
        if(pokemonNames.contains(pokemonName))
            ___tabName[0] = CardCrawlGame.languagePack.getCharacterString(MysteryDungeon.makeID(pokemonName)).NAMES[0];
    }
    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher matcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
            return LineFinder.findInOrder(ctMethodToPatch, matcher);
        }

    }
    public static ArrayList<String> pokemonNames = new ArrayList<String>() {{
        add("Bulbasaur");
        add("Charmander");
        add("Squirtle");
        add("Pikachu");
        add("Meowth");
        add("Chikorita");
        add("Cyndaquil");
        add("Totodile");
    }};
}
