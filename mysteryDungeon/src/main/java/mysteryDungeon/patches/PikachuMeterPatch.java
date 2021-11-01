package mysteryDungeon.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import mysteryDungeon.characters.Pokemon;

public class PikachuMeterPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class RenderPikaMeter {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            if(Pokemon.hasChosenPikachu())
                Pokemon.pikaMeter.render(sb, __instance);
        }
    }
    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class UpdatePikaMeter {
        public static void Postfix(AbstractPlayer __instance) {
            if(Pokemon.hasChosenPikachu())
                Pokemon.pikaMeter.update();
        }
    }
}
