package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.helpers.CardModifierManager;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cardMods.EtherealMod;

public class RetainCardsButEtherealModAction extends AbstractGameAction {

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(MysteryDungeon.makeID("RetainCardsButEtherealModAction"));
  
    public static final String[] TEXT = uiStrings.TEXT;

    public AbstractGameAction action;

    public RetainCardsButEtherealModAction(AbstractCreature source, int amount) {
        setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
      }
  
      public void update() {
        if (duration == 0.5F) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, false, true, false, false, true);
            addToBot(new WaitAction(0.25F));
            tickDuration();
            return;
        } 
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (!c.isEthereal)
                {
                    c.retain = true; 
                    CardModifierManager.addModifier(c, new EtherealMod());
                }
                    
                AbstractDungeon.player.hand.addToTop(c);
            } 
          AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        } 
        tickDuration();
      }
}
