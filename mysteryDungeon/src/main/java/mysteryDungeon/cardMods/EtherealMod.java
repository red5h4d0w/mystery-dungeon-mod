package mysteryDungeon.cardMods;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import mysteryDungeon.MysteryDungeon;

public class EtherealMod extends AbstractCardModifier {
    public static String ID = MysteryDungeon.makeID("EtherealMod");
    
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return CardCrawlGame.languagePack.getUIString(ID).TEXT[0] + rawDescription;
    }
    
    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card, ID);
    }
    
    public void onRetained(AbstractCard card) {
        card.isEthereal = true;
    }
    
    public AbstractCardModifier makeCopy() {
        return new EtherealMod();
    }
    
    public String identifier(AbstractCard card) {
        return ID;
    }
  }
