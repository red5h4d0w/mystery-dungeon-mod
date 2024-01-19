package mysteryDungeon.potions;

import java.util.HashSet;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import mysteryDungeon.abstracts.PokemonPotion;
import mysteryDungeon.pokemons.Cyndaquil;

public class ElixirPotion extends PokemonPotion {

    public static final String POTION_ID = mysteryDungeon.MysteryDungeon.makeID(ElixirPotion.class);
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);

    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static final String IMG_STRING = "elixir.png";

    public ElixirPotion() {
        // The bottle shape and inside is determined by potion size and color. The
        // actual colors are the main MysteryDungeon.java
        super(NAME, POTION_ID, PotionRarity.UNCOMMON, IMG_STRING);

        // Potency is the damage/magic number equivalent of potions.
        potency = getPotency();

        // Do you throw this potion at an enemy or do you just consume it.
        isThrown = false;

        cardColors = new HashSet<CardColor>() {
            {
                add(Cyndaquil.CARD_COLOR);
            }
        };

        updateDescription();
        // Initialize the on-hover name + description
        tips.add(new PowerTip(name, description));

    }

    @Override
    public void use(AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) {
            addToBot(new GainEnergyAction(potency));
            addToBot(new MakeTempCardInHandAction(new Burn(), (2 * potency) / 3));
        }
    }

    @Override
    public AbstractPotion makeCopy() {
        return new ElixirPotion();
    }

    // This is your potency.
    @Override
    public int getPotency(final int potency) {
        return 3;
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
