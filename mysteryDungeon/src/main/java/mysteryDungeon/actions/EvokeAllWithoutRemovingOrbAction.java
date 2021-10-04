package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EvokeAllWithoutRemovingOrbAction extends AbstractGameAction {
    
    

public void update() {
    for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
        addToTop(new EvokeWithoutRemovingOrbAction(1)); 
        this.isDone = true;
        }
}