package mysteryDungeon.abstracts;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.interfaces.AtStartOfTurnPostDrawInterface;
import mysteryDungeon.powers.FreeSpendingThisTurnPower;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

public abstract class PokemonCard extends CustomCard implements AtStartOfTurnPostDrawInterface {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int secondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public int thirdMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int baseThirdMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean upgradedThirdMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public boolean isThirdMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)
    public boolean inert = false; // A boolean to indicate whether the card affects the charge meter.
    public boolean isAdventurerOnly = false; // Indicates if the card can only be obtained as adventurer of its card color
    public boolean scoopUp = false; // A boolean that indicates if the card should be scooped up when discarded manually


    public PokemonCard(final String id,
                               final String name,
                               final String img,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {

        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        if(!color.equals(CardColor.COLORLESS))
            loadJokeCardImage(makeCardPath(this.getClass().getSimpleName().split("(?=\\p{Upper})")[0].toLowerCase()+".png"));
        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isSecondMagicNumberModified = false;
        isThirdMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
        if (upgradedSecondMagicNumber) { // If we set upgradedSecondMagicNumber = true in our card.
            secondMagicNumber = baseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }
        if (upgradedThirdMagicNumber) { // If we set upgradedSecondMagicNumber = true in our card.
            thirdMagicNumber = baseThirdMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isThirdMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }
    public void upgradeSecondMagicNumber(int amount) { 
        baseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        secondMagicNumber = baseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void upgradeThirdMagicNumber(int amount) { 
        baseThirdMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        thirdMagicNumber = baseThirdMagicNumber; // Set the number to be equal to the base value.
        upgradedThirdMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    public void loadJokeCardImage(String img) {
        Texture cardTexture;
        if (imgMap.containsKey(img)) {
          cardTexture = imgMap.get(img);
        } else {
          cardTexture = ImageMaster.loadImage(img);
          imgMap.put(img, cardTexture);
        } 
        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "jokePortrait", cardImg);
    }

    public boolean canSpend(int spendAmount) {
        if(spendAmount>0 && !AbstractDungeon.player.hasPower(FreeSpendingThisTurnPower.POWER_ID)) {
            return !(spendAmount>AbstractDungeon.player.gold);
        }
        return true;
    }

    public void atStartOfTurnPostDraw() {
        if(scoopUp && AbstractDungeon.player.discardPile.contains(this)) {
            addToBot(new SimpleAction(() -> {
                AbstractDungeon.player.discardPile.removeCard(this);
                AbstractDungeon.player.drawPile.addToTop(this);
            }));
        }
    }
}