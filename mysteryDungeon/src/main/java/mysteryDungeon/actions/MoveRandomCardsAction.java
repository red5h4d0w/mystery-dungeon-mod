package mysteryDungeon.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.BaseMod;

public class MoveRandomCardsAction extends AbstractGameAction{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("stslib:MoveCardsAction");
  
    public static final String[] TEXT = uiStrings.TEXT;
    
    private AbstractPlayer p;
    
    private CardGroup source;
    
    private CardGroup destination;
    
    private Predicate<AbstractCard> predicate;
    
    private Consumer<List<AbstractCard>> callback;
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount, Consumer<List<AbstractCard>> callback) {
        this.p = AbstractDungeon.player;
        this.destination = destination;
        this.source = source;
        this.predicate = predicate;
        this.callback = callback;
        setValues((AbstractCreature)this.p, (AbstractCreature)AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, Consumer<List<AbstractCard>> callback) {
        this(destination, source, predicate, 1, callback);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, int amount, Consumer<List<AbstractCard>> callback) {
        this(destination, source, c -> true, amount, callback);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, Consumer<List<AbstractCard>> callback) {
        this(destination, source, c -> true, 1, callback);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate, int amount) {
        this(destination, source, predicate, amount, (Consumer<List<AbstractCard>>)null);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, Predicate<AbstractCard> predicate) {
        this(destination, source, predicate, 1);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source, int amount) {
        this(destination, source, c -> true, amount);
    }
    
    public MoveRandomCardsAction(CardGroup destination, CardGroup source) {
        this(destination, source, c -> true, 1);
    }
    
    
    public void update() {
        if (duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : source.group) {
                if (predicate.test(c)) {
                    tmp.addToRandomSpot(c);
                } 
            } 
            if (tmp.size() == 0) {
                isDone = true;
                return;
            } 
            for(int i=0; i<amount; i++) {
                if(tmp.size() != 0) {
                    AbstractCard card = tmp.getTopCard();
                    if (source == p.exhaustPile)
                        card.unfadeOut(); 
                    if (destination == p.hand && p.hand.size() == BaseMod.MAX_HAND_SIZE) {
                        source.moveToDiscardPile(card);
                        p.createHandIsFullDialog();
                    } else {
                        card.untip();
                        card.unhover();
                        card.lighten(true);
                        card.setAngle(0.0F);
                        card.drawScale = 0.12F;
                        card.targetDrawScale = 0.75F;
                        card.current_x = CardGroup.DRAW_PILE_X;
                        card.current_y = CardGroup.DRAW_PILE_Y;
                        source.removeCard(card);
                        destination.addToTop(card);
                        AbstractDungeon.player.hand.refreshHandLayout();
                        AbstractDungeon.player.hand.applyPowers();
                    } 
                    List<AbstractCard> callbackList = new ArrayList<>();
                    callbackList.add(card);
                    if (callback != null)
                        callback.accept(callbackList); 
                        isDone = true;
                    return;
                }
                
            }
        } 
        tickDuration();
    }
}
