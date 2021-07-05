package mysteryDungeon.actions;

import java.util.ArrayList;
import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.cards.red.Exhume;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BetterExhumeAction extends AbstractGameAction {
    private AbstractPlayer p;

    private CardGroup whereToSend;
    
    private final boolean upgrade;
    
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhumeAction");
    
    public static final String[] TEXT = uiStrings.TEXT;
    
    private ArrayList<AbstractCard> exhumes = new ArrayList<>();
    
    public BetterExhumeAction(boolean upgrade, CardGroup whereToSend) {
        this.upgrade = upgrade;
        this.p = AbstractDungeon.player;
        this.whereToSend = whereToSend;
        setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    
    public void update() {
        if (duration == Settings.ACTION_DUR_FAST) {
            if (whereToSend == AbstractDungeon.player.hand && AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                isDone = true;
                return;
        } 
        if (p.exhaustPile.isEmpty()) {
            isDone = true;
            return;
        } 
        if (p.exhaustPile.size() == 1) {
            if ((p.exhaustPile.group.get(0)).cardID.equals(Exhume.ID)) {
                isDone = true;
                return;
            } 
            AbstractCard abstractCard = p.exhaustPile.getTopCard();
            abstractCard.unfadeOut();
            whereToSend.addToHand(abstractCard);
            if (AbstractDungeon.player.hasPower(Corruption.ID) && abstractCard.type == AbstractCard.CardType.SKILL)
                abstractCard.setCostForTurn(-9); 
            this.p.exhaustPile.removeCard(abstractCard);
            if (this.upgrade && abstractCard.canUpgrade())
                abstractCard.upgrade(); 
            abstractCard.unhover();
            abstractCard.fadingOut = false;
            this.isDone = true;
            return;
        } 
        for (AbstractCard abstractCard : this.p.exhaustPile.group) {
            abstractCard.stopGlowing();
            abstractCard.unhover();
            abstractCard.unfadeOut();
        } 
        for (Iterator<AbstractCard> c = this.p.exhaustPile.group.iterator(); c.hasNext(); ) {
            AbstractCard derp = c.next();
            if (derp.cardID.equals(Exhume.ID)) {
                c.remove();
                this.exhumes.add(derp);
            } 
        } 
        if (p.exhaustPile.isEmpty()) {
            p.exhaustPile.group.addAll(this.exhumes);
            exhumes.clear();
            isDone = true;
            return;
        } 
        AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, TEXT[0], false);
        tickDuration();
        return;
      } 
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                whereToSend.addToHand(c);
                if (AbstractDungeon.player.hasPower(Corruption.ID) && c.type == AbstractCard.CardType.SKILL)
                    c.setCostForTurn(-9); 
                p.exhaustPile.removeCard(c);
                if (upgrade && c.canUpgrade())
                    c.upgrade(); 
                c.unhover();
            } 
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            whereToSend.refreshHandLayout();
            p.exhaustPile.group.addAll(this.exhumes);
            exhumes.clear();
            for (AbstractCard c : this.p.exhaustPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0F;
            } 
        } 
        tickDuration();
    }
  }