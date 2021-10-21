package mysteryDungeon.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.characters.Pokemon;

public class PikachuMeterPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class RenderNoteQueue {
        public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
            if(((Pokemon)AbstractDungeon.player).hasChosenPikachu())
                Pokemon.pikaMeter.render(sb, __instance);
        }
    }
}
