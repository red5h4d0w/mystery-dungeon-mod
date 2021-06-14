package mysteryDungeon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SubstituteAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("AnyCardFromDeckToHandAction");
    
    public static final String[] TEXT = uiStrings.TEXT;
    
    private AbstractPlayer p;
    private boolean upgraded;
    
    public SubstituteAction(boolean upgraded) {
      this.p = AbstractDungeon.player;
      this.upgraded = upgraded;
      setValues(this.p, this.p);
      this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
      this.duration = Settings.ACTION_DUR_MED;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.drawPile.group)
                tmp.addToRandomSpot(c); 
            if (tmp.size() == 0) {
                addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, false, false, false));
                this.isDone = true;
                return;
            } 
            if (tmp.size() == 1) {
                AbstractCard card = tmp.getTopCard();
                if (p.hand.size() == 10) {
                    if(upgraded)
                    {
                        p.discardPile.addToTop(card.makeCopy());
                    }  
                    else
                    {
                        p.drawPile.moveToDiscardPile(card);
                    }    
                    p.createHandIsFullDialog();
                } else {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0F);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    p.drawPile.group.add(p.drawPile.group.indexOf(card)+1,new Wound());
                    if(upgraded)
                    {
                        AbstractDungeon.player.hand.addToTop(card.makeCopy());
                    }
                    else
                    {
                        p.drawPile.removeCard(card);
                        p.hand.addToTop(card);
                    }
                    p.hand.refreshHandLayout();
                    p.hand.applyPowers();
                }
                addToBot(new MakeTempCardInDrawPileAction(new Wound(), 1, false, false, false)); 
                this.isDone = true;
                return;
            } 
            AbstractDungeon.gridSelectScreen.open(tmp, 1, TEXT[0], false);
            tickDuration();
            return;
        } 
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (p.hand.size() == 10) {
                    if(upgraded)
                    {
                        p.discardPile.addToTop(c.makeCopy());
                    }  
                    else
                    {
                        p.drawPile.moveToDiscardPile(c);
                    } 
                    p.createHandIsFullDialog();
                } else {
                    p.drawPile.group.add(p.drawPile.group.indexOf(c)+1,new Wound());
                    if(upgraded)
                    {
                        p.hand.addToTop(c.makeCopy());
                    }
                    else
                    {
                        p.drawPile.removeCard(c);
                        p.hand.addToTop(c);
                    }
                    p.hand.refreshHandLayout();
                    p.hand.applyPowers();
                } 
                p.hand.refreshHandLayout();
                p.hand.applyPowers();
                } 
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                p.hand.refreshHandLayout();
            } 
            tickDuration();
        }
  }