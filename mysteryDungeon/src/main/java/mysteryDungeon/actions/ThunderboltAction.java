package mysteryDungeon.actions;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.MiracleEffect;

public class ThunderboltAction extends AbstractGameAction {
    public void update() {
        if (AbstractDungeon.actionManager.cardsPlayedThisCombat.size() >= 2 && ((AbstractCard)AbstractDungeon.actionManager.cardsPlayedThisCombat
          .get(AbstractDungeon.actionManager.cardsPlayedThisCombat
            .size() - 2)).type == AbstractCard.CardType.SKILL) {
          addToTop((AbstractGameAction)new GainEnergyAction(1));
          if (Settings.FAST_MODE) {
            addToTop((AbstractGameAction)new VFXAction((AbstractGameEffect)new MiracleEffect(Color.YELLOW, Color.YELLOW, "ATTACK_MAGIC_SLOW_1"), 0.0F));
          } else {
            addToTop((AbstractGameAction)new VFXAction((AbstractGameEffect)new MiracleEffect(Color.YELLOW, Color.YELLOW, "ATTACK_MAGIC_SLOW_1"), 0.3F));
          } 
        } 
        this.isDone = true;
      }
  }