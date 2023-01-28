package mysteryDungeon.potions;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.SmokeBomb;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

import mysteryDungeon.abstracts.PokemonPotion;
import mysteryDungeon.actions.SpendGoldAction;
import mysteryDungeon.pokemons.Meowth;

public class MeowthCandyPotion extends PokemonPotion {

    public static final String POTION_ID = mysteryDungeon.MysteryDungeon.makeID(MeowthCandyPotion.class);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String IMG_STRING = "meowth-candy.png";

    public MeowthCandyPotion() {
        // The bottle shape and inside is determined by potion size and color. The actual colors are the main MysteryDungeon.java
        super(NAME, POTION_ID, PotionRarity.COMMON, IMG_STRING);
        
        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();
        
       // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        cardColor = Meowth.CARD_COLOR;
        
        updateDescription();
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));
        
    }

    @Override
    public void use(AbstractCreature target) {
        addToBot(new SpendGoldAction(20));
        for(int i=0;i<potency;i++) {
            AbstractPotion potion;
            while(true) {
                potion = AbstractDungeon.returnRandomPotion();
                if(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss || AbstractDungeon.player.hasPower(SurroundedPower.POWER_ID)) {
                    if(potion.ID == SmokeBomb.POTION_ID) {
                        continue;
                    }
                } 
                break;
            }
            potion.use(AbstractDungeon.getRandomMonster());
        }
    }
    
    @Override
    public boolean canUse() {
        return super.canUse() && canSpend(20);
    }

    @Override
    public AbstractPotion makeCopy() {
        return new MeowthCandyPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 2;
    }

    public void upgradePotion()
    {
      potency *= 2;
      tips.clear();
      tips.add(new PowerTip(name, description));
    }
    
    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], potency);
    }
}
