package mysteryDungeon.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.FocusPower;

import mysteryDungeon.MysteryDungeon;


public class NegativeStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(NegativeStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
  
  
    public NegativeStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }
  
    @Override
    public void onPlayCard(AbstractCard card) {
        if (card.type == CardType.SKILL)
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
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
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 7));
    }

}
