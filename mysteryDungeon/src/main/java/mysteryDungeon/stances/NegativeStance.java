package mysteryDungeon.stances;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.FocusPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.powers.LockDownPower;
import mysteryDungeon.relics.CellBatteryRelic;


public class NegativeStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(NegativeStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private int timesActivatedThisTurn = 0;
  
    public NegativeStance() {
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
        if (card.type == CardType.SKILL && timesActivatedThisTurn<2) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FocusPower(AbstractDungeon.player, 1), 1));
            timesActivatedThisTurn++;
        }
            
    }

    @Override
    public void atStartOfTurn() {
        timesActivatedThisTurn=0;
    }

    @Override
    public void onEndOfTurn() {
        if(!AbstractDungeon.player.hasRelic(CellBatteryRelic.ID) && (!AbstractDungeon.player.hasPower(LockDownPower.POWER_ID)))
            addToBot(new SetPikaMeterAction(0));
    }

    @Override
    public void updateDescription() {
        description = stanceString.DESCRIPTION[0];
    }
  
    @Override
    public void onExitStance() {
        addToBot(new EvokeOrbAction(2));
    }

}
