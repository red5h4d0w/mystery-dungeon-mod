package mysteryDungeon.abstracts;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.potions.AbstractPotion;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomPotion;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.util.TextureLoader;

public abstract class AbstractPokemonPotion extends CustomPotion {
    public AbstractPokemonPotion(String name, String potionId, PotionRarity rarity, String image) {
        super(name, potionId, rarity, PotionSize.ANVIL, PotionColor.ANCIENT);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "containerImg", TextureLoader.getTexture(MysteryDungeon.makePotionPath(image)));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "outlineImg", TextureLoader.getTexture(MysteryDungeon.makePotionOutlinePath(image)));
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "liquidColor", Color.CLEAR);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "hybridColor", null);
        ReflectionHacks.setPrivate(this, AbstractPotion.class, "spotsColor", null);
    }

    public abstract void updateDescription();
}
