package mysteryDungeon.stances;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.StanceStrings;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceAuraEffect;
import com.megacrit.cardcrawl.vfx.stance.StanceChangeParticleGenerator;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.powers.LockDownPower;
import mysteryDungeon.relics.CellBatteryRelic;
import mysteryDungeon.vfx.PositiveStanceParticle;


public class PositiveStance extends PokemonStance {
    public static final String STANCE_ID = MysteryDungeon.makeID(PositiveStance.class.getSimpleName());
    
    private static final StanceStrings stanceString = CardCrawlGame.languagePack.getStanceString(STANCE_ID);
  
    private static long sfxId = -1L;

    public PositiveStance() {
        ID = STANCE_ID;
        name = stanceString.NAME;
        updateDescription();
    }
    public void updateAnimation() {
        if (!Settings.DISABLE_EFFECTS) {
            this.particleTimer -= Gdx.graphics.getDeltaTime();
            if (this.particleTimer < 0.0F) {
                this.particleTimer = 0.05F;
                AbstractDungeon.effectsQueue.add(new PositiveStanceParticle());
            } 
        } 
        this.particleTimer2 -= Gdx.graphics.getDeltaTime();
        if (this.particleTimer2 < 0.0F) {
            this.particleTimer2 = MathUtils.random(0.3F, 0.4F);
            AbstractDungeon.effectsQueue.add(new StanceAuraEffect("Wrath"));
        } 
    }
    public void onEnterStance() {
        if (sfxId != -1L)
            stopIdleSfx(); 
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE");
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.YELLOW.cpy(), true));
        AbstractDungeon.effectsQueue.add(new StanceChangeParticleGenerator(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, "Wrath"));
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
    public void onEndOfTurn() {
        if(!AbstractDungeon.player.hasRelic(CellBatteryRelic.ID) && (!AbstractDungeon.player.hasPower(LockDownPower.POWER_ID)))
            addToBot(new SetPikaMeterAction(0));
    }

    @Override
    public void onExitStance() {
        addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, 4));
        stopIdleSfx();
    }
    public void stopIdleSfx() {
        if (sfxId != -1L) {
            CardCrawlGame.sound.stop("STANCE_LOOP_WRATH", sfxId);
            sfxId = -1L;
        } 
    }
}
