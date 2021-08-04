package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ExhaustFromDeckAction extends AbstractGameAction {
    private AbstractPlayer p;
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    
    public static final String[] TEXT = uiStrings.TEXT;
    
    public ExhaustFromDeckAction(int amount) {
        this.amount = amount;
        this.p = AbstractDungeon.player;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (p.drawPile.group.size()==0) {
                isDone = true;
                return;
        }
        for (AbstractCard abstractCard : this.p.drawPile.group) {
            abstractCard.stopGlowing();
            abstractCard.unhover();
            abstractCard.unfadeOut();
        } 
        AbstractDungeon.gridSelectScreen.open(this.p.drawPile, amount, TEXT[0], false);
        tickDuration();
        return;
      } 
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                p.drawPile.moveToExhaustPile(c);
            }
        } 
        tickDuration();
    }
  }