package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import mysteryDungeon.MysteryDungeon;

public class QueueActionAction extends AbstractGameAction {

    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());

    public AbstractGameAction action;

    public QueueActionAction(AbstractGameAction action) {
        this.action = action;
    }
  
    public void update() {
        addToBot(action);
        isDone = true;
    }
}
