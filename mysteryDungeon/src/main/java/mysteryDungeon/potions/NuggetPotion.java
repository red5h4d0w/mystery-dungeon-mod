package mysteryDungeon.potions;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import mysteryDungeon.abstracts.PokemonPotion;
import mysteryDungeon.pokemons.Meowth;

public class NuggetPotion extends PokemonPotion {

    public static final String POTION_ID = mysteryDungeon.MysteryDungeon.makeID(NuggetPotion.class);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String IMG_STRING = "nugget.png";

    public NuggetPotion() {
        // The bottle shape and inside is determined by potion size and color. The
        // actual colors are the main MysteryDungeon.java
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
        addToBot(new GainGoldAction(potency));
    }

    @Override
    public AbstractPotion makeCopy() {
        return new NuggetPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 10;
    }

    public boolean canUse() {
        if (AbstractDungeon.actionManager.turnHasEnded &&
                (AbstractDungeon.getCurrRoom()).phase == RoomPhase.COMBAT)
            return false;
        if ((AbstractDungeon.getCurrRoom()).event != null &&
                (AbstractDungeon.getCurrRoom()).event instanceof WeMeetAgain)
            return false;
        return true;
    }

    public void upgradePotion() {
        potency *= 2;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    public void updateDescription() {
        description = String.format(DESCRIPTIONS[0], potency);
    }
}
