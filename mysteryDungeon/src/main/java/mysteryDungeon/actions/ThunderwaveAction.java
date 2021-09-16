package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class ThunderwaveAction extends AbstractGameAction {
    int additionalAmt;
    
    public ThunderwaveAction(AbstractCreature target, int block, int additional) {
        this.target = target;
        this.amount = block;
        this.additionalAmt = additional;
    }
    
    public void update() {
        if (AbstractDungeon.player.stance.ID == NeutralStance.STANCE_ID) {
            addToTop((AbstractGameAction)new GainBlockAction(this.target, this.amount + this.additionalAmt));
          } else {
            addToTop((AbstractGameAction)new GainBlockAction(this.target, this.amount));
          } 
          this.isDone = true;
        }
  }