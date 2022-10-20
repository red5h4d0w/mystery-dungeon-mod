package mysteryDungeon.interfaces;


import com.megacrit.cardcrawl.core.AbstractCreature;

public interface BetterOnGainBlockInterface
{
    default void betterOnGainBlock(AbstractCreature receiver, int blockAmount){};
}
