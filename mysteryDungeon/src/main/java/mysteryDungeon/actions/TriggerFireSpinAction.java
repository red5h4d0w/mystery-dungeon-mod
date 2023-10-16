package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.abstracts.PokemonPower;

public class TriggerFireSpinAction extends AbstractGameAction {
  
  public TriggerFireSpinAction() { }
  
  public void update() {
    for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
      for (PokemonPower p : mo.powers.stream().filter(p->p instanceof PokemonPower).toArray(PokemonPower[]::new))
        p.triggerFireSpin(); 
    } 
    this.isDone = true;
  }
}
