package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.util.TorchicComboManager.Combo;

public class AddComboAction extends AbstractGameAction {

    private Combo combo;

    public AddComboAction(Combo combo) {
        this.combo = combo;
    }

    public void update() {
        Pokemon.comboManager.addCombo(combo);
        isDone = true;
    }

}
