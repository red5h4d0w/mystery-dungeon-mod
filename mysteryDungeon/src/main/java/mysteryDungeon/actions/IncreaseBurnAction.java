package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class IncreaseBurnAction extends AbstractGameAction {
    private int miscIncrease;

    private UUID uuid;

    public IncreaseBurnAction(UUID targetUUID, int miscIncrease) {
        this.miscIncrease = miscIncrease;
        this.uuid = targetUUID;
    }

    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (!c.uuid.equals(this.uuid))
                continue;
            c.baseMagicNumber += miscIncrease;
            c.magicNumber = c.baseMagicNumber;
            c.applyPowers();
            c.isMagicNumberModified = false;
        }
        for (AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.baseMagicNumber += miscIncrease;
            c.magicNumber = c.baseMagicNumber;
            c.applyPowers();
            c.isMagicNumberModified = false;
        }
        this.isDone = true;
    }
}
