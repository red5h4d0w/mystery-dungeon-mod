package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.cards.tempCards.SquirtleFlinch;

public class AquaRingAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private boolean upgrade;
    private AbstractPlayer p;

    private int numberOfDiscardedCards = 0;
    private boolean menuPoppedUp = false;
    public Logger logger = LogManager.getLogger(AquaRingAction.class);

    public AquaRingAction(int amount, boolean upgrade) {
        this.p = AbstractDungeon.player;
        this.amount = amount;
        this.upgrade = upgrade;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    public void update() {
        if (!menuPoppedUp) {
            if (p.hand.size() == 0) {
                this.isDone = true;
            }
            else
            {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount, true, true);
                tickDuration();
                menuPoppedUp = true;
                return;
            }      
        } 
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group)
            {
                p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
                numberOfDiscardedCards++; 
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
        if(isDone || AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            AbstractCard flinch = new SquirtleFlinch();
            if(upgrade)
                flinch.upgrade();
            addToTop(new MakeTempCardInHandAction(flinch, numberOfDiscardedCards));
            isDone = true;
        }
    }
}