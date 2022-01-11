package mysteryDungeon.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.CalmParticleEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.powers.LockDownPower;
import mysteryDungeon.relics.CellBatteryRelic;


public class NegativeStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(NegativeStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
    private int timesActivatedThisTurn = 0;
    private static long sfxId = -1L;
    public NegativeStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }

    public void updateAnimation() {
    if (!Settings.DISABLE_EFFECTS) {
        this.particleTimer -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer < 0.0F) {
            this.particleTimer = 0.04F;
            AbstractDungeon.effectsQueue.add(new CalmParticleEffect());
        } 
    } 
    this.particleTimer2 -= Gdx.graphics.getDeltaTime();
    if (this.particleTimer2 < 0.0F) {
        this.particleTimer2 = MathUtils.random(0.45F, 0.55F);
        AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Calm"));
    } 
    }
      
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx(); 
        CardCrawlGame.sound.play("POWER_FOCUS", 0.05F);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.YELLOW.cpy(), true));
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
        addToBot(new EvokeOrbAction(1));
        stopIdleSfx();
    }
    public void stopIdleSfx() {
        if (sfxId != -1L) {
          CardCrawlGame.sound.stop("STANCE_LOOP_CALM", sfxId);
          sfxId = -1L;
        } 
}
}
