package mysteryDungeon.abstracts;

import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;


//Gain 1 dex for the turn for each card played.

public class PokemonTwoAmountPower extends TwoAmountPower  {
    
    @Override
    public void stackPower(int stackAmount)
    {
        super.stackPower(stackAmount);
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount)
    {
        super.reducePower(reduceAmount);
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
