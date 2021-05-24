package mysteryDungeon.interfaces;


import com.megacrit.cardcrawl.rewards.RewardItem;

import java.util.ArrayList;

public interface onCreateRewards
{
    default void beforeCreated(ArrayList<RewardItem> rewards){};
    default void afterCreated(ArrayList<RewardItem> rewards){};
}
