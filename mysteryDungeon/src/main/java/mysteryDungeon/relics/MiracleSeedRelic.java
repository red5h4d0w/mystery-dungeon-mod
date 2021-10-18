package mysteryDungeon.relics;

import basemod.abstracts.CustomRelic;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class MiracleSeedRelic extends CustomRelic implements OnApplyPowerRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(MiracleSeedRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("mystic-water.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("mystic-water.png"));

    public MiracleSeedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void atTurnStart() {
      this.counter = 0;
    }

    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source ) {
      if(power.type == PowerType.DEBUFF){
        this.counter++;
        if (this.counter % 2 == 0) {
          flash();
          this.counter = 0;
          addToBot(new DrawCardAction(AbstractDungeon.player, 1));
        } 
      }
      return true;
    }
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
