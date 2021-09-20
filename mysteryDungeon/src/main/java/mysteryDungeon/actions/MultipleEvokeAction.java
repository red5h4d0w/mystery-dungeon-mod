package mysteryDungeon.actions;

import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;



public class MultipleEvokeAction extends AbstractGameAction {
    int additionalAmt;


    public MultipleEvokeAction(AbstractPlayer p, int magicNumber, boolean upgraded) {
        this.p = p;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.amount = magicNumber;
        this.additionalAmt = additional;

  
    public MultipleEvokeAction() {
    
    }
  
    public void update() {
        addToBot((AbstractGameAction)new EvokeWithoutRemovingOrbAction(1)); 
        addToBot((AbstractGameAction)new AnimateOrbAction(1));
        addToBot((AbstractGameAction)new EvokeOrbAction(1));
        RepeatAction allRepeatAction; 
        
}
