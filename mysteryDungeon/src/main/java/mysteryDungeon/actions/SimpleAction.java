package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class SimpleAction extends AbstractGameAction {

    public Runnable updateCallback;

    public SimpleAction(Runnable update) {
        this.updateCallback = update;
    }
  
    

    public void update() {
        updateCallback.run();
        isDone = true;
    }
}
