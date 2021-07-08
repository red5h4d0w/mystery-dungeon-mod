package mysteryDungeon.stances;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.AbstractStance;

public class PokemonStance extends AbstractStance{
    public void updateDescription() {};

    public void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }
    
}
