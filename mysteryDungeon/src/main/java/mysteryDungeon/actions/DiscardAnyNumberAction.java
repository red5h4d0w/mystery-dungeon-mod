package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class DiscardAnyNumberAction extends AbstractGameAction {
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAnyNumberAction");
    
    public static final String[] TEXT = uiStrings.TEXT;
    
    private boolean notchip;
    
    public DiscardAnyNumberAction(AbstractCreature source) {
        setValues(AbstractDungeon.player, source, -1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.notchip = false;
    }
    
    public DiscardAnyNumberAction(AbstractCreature source, boolean notChip) {
        setValues(AbstractDungeon.player, source, -1);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.notchip = notChip;
    }
    
    public void update() {
        if (this.duration == 0.5F) {
            if (this.notchip) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[1], 99, true, true);
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
            } 
            addToBot((AbstractGameAction)new WaitAction(0.25F));
            tickDuration();
            return;
        } 
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            if (!AbstractDungeon.handCardSelectScreen.selectedCards.group.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    AbstractDungeon.player.hand.moveToDiscardPile(c);
                    GameActionManager.incrementDiscard(false);
                    c.triggerOnManualDiscard();
                } 
                isDone = true;
            } 
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        } 
        tickDuration();
    }
}