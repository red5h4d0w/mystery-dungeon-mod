package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.powers.BurnPower;

public class ScaldAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("DiscardAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private DamageInfo info;
    private AbstractPlayer p;

    private int numberOfDiscardedCards = 0;
    private int burnAmount;
    private boolean menuPoppedUp = false;
    public Logger logger = LogManager.getLogger(ScaldAction.class);
  
    public ScaldAction(AbstractCreature target, DamageInfo info, int amount, int burnAmount) {
        this.p = AbstractDungeon.player;
        this.info = info;
        this.burnAmount = burnAmount;
        setValues(target, info);
        this.amount = amount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
    }
  
    public void update() {
        if (!menuPoppedUp) {
            if (p.hand.size() == 0) {
                this.isDone = true;
            }
            else
            {
                logger.info(amount);
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
                numberOfDiscardedCards++; 
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        if(isDone || AbstractDungeon.handCardSelectScreen.wereCardsRetrieved)
        {
            for(int i=0;i<numberOfDiscardedCards;i++)
            {
                addToBot(new DamageAction(target, info));
                addToBot(new ApplyPowerAction(target, info.owner, new BurnPower(target, burnAmount)));
            }
            isDone = true;
        }
    }
}
