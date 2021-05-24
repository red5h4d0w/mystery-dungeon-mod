package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import mysteryDungeon.interfaces.ClawCardInterface;

public class UpgradeClawAction extends AbstractGameAction {

    public AbstractCard card;
    public int amount;

    public UpgradeClawAction(AbstractCard card, int amount) {
        this.amount = amount;
        this.card = card;
    }
  
    public void update() {
        if (card instanceof ClawCardInterface) {
            ((ClawCardInterface)card).clawUpgrade(amount);
        }
        for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if (c instanceof ClawCardInterface) {
                ((ClawCardInterface)c).clawUpgrade(amount);
            }
            if (c instanceof com.megacrit.cardcrawl.cards.blue.Claw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }  
        } 
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (c instanceof ClawCardInterface) {
                ((ClawCardInterface)c).clawUpgrade(amount);
            }
            if (c instanceof com.megacrit.cardcrawl.cards.blue.Claw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }  
        } 
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof ClawCardInterface) {
                ((ClawCardInterface)c).clawUpgrade(amount);
            }
            if (c instanceof com.megacrit.cardcrawl.cards.blue.Claw) {
                c.baseDamage += this.amount;
                c.applyPowers();
            }  
        } 
        isDone = true;
    }
}
