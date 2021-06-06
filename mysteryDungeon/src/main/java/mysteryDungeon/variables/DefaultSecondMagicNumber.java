package mysteryDungeon.variables;

import basemod.abstracts.DynamicVariable;
import mysteryDungeon.cards.PokemonCard;

import static mysteryDungeon.MysteryDungeon.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class DefaultSecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("M2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "Pokemon:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((PokemonCard) card).isSecondMagicNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((PokemonCard) card).secondMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((PokemonCard) card).baseSecondMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((PokemonCard) card).upgradedSecondMagicNumber;
    }
}