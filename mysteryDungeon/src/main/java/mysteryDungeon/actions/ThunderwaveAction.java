package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.NeutralStance;

public class ThunderwaveAction extends AbstractGameAction {
    public int additionalAmt;
    
    public ThunderwaveAction(AbstractCreature target, int block, int additionalAmt) {
        this.target = target;
        this.amount = block;
        this.additionalAmt = additionalAmt;
    }
    
    public void update() {
        if (AbstractDungeon.player.stance.ID == NeutralStance.STANCE_ID) {
            addToTop(new GainBlockAction(target, amount + additionalAmt));
        } 
        else {
            addToTop(new GainBlockAction(target, amount));
        } 
        this.isDone = true;
    }
}