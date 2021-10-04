package mysteryDungeon.actions;


import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class EvokeAllWithoutRemovingOrbAction extends AbstractGameAction {
    public EvokeAllWithoutRemovingOrbAction() {

    }
    
    public void update() {
        for (AbstractOrb orb: AbstractDungeon.player.orbs.stream().filter(orb->!(orb instanceof EmptyOrbSlot)).collect(Collectors.toList()))
            orb.onEvoke();
        isDone = true;
    }
}