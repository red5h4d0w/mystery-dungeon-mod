package mysteryDungeon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class GyroBallAction extends AbstractGameAction {

    private int damage;

    public GyroBallAction(AbstractCreature target, int damage) {
        this.target = target;
        this.damage = damage;
    }
    
    public void update() {
        for (AbstractCard card : AbstractDungeon.player.hand.group) {
            if(card.type==CardType.STATUS)
            {
                addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage, DamageType.NORMAL)));
                addToTop(new DiscardSpecificCardAction(card));
            }
        }
    }
}