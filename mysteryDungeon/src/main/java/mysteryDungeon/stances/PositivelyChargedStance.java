package mysteryDungeon.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import mysteryDungeon.MysteryDungeon;


public class PositivelyChargedStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(PositivelyChargedStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
  
  
    public PositivelyChargedStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }
  
    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == CardType.ATTACK)
            AbstractDungeon.player.channelOrb(new Lightning());
    }

    @Override
    public void updateDescription() {
        description = stanceString.DESCRIPTION[0];
    }
  
    @Override
    public void onEnterStance() { 
        addToBot(new GainEnergyAction(1));
    }
  
    @Override
    public void onExitStance() {
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, 1), 1));
    }

}
