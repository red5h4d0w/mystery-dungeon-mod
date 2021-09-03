package mysteryDungeon.stances;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.Lightning;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cards.PokemonCard;


public class PositiveStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(PositiveStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
  
  
    public PositiveStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }
  
    @Override
    public void onPlayCard(AbstractCard card) {
        if(card instanceof PokemonCard) {
            if(((PokemonCard)card).inert) {
                return;
            }
        }
        if (AbstractDungeon.player.hasEmptyOrb()){
            if (card.type == CardType.ATTACK)
                AbstractDungeon.player.channelOrb(new Lightning());
        }
    }

    @Override
    public void updateDescription() {
        description = stanceString.DESCRIPTION[0];
    }
  
    @Override
    public void onExitStance() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 7));
    }

}
