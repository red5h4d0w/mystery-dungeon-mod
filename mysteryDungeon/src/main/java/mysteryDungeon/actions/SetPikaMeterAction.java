package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.characters.Pokemon;

public class SetPikaMeterAction extends AbstractGameAction {
    private int state;
    public SetPikaMeterAction(int state) {
        this.state = state;
    }
  
    public void update() {
        if(AbstractDungeon.player instanceof Pokemon) {
            ((Pokemon)AbstractDungeon.player).setPikaMeter(state);
        }
        isDone = true;
    }
}
