package mysteryDungeon.characters;

import basemod.abstracts.CustomPlayer;
import basemod.abstracts.CustomSavable;
import basemod.animations.SpriterAnimation;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cards.Bulbasaur.*;
import mysteryDungeon.cards.Charmander.*;
import mysteryDungeon.cards.Squirtle.SquirtleDefend;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.cards.Squirtle.SquirtleWaterGun;
import mysteryDungeon.relics.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;

import com.google.gson.reflect.TypeToken;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static mysteryDungeon.MysteryDungeon.*;
import static mysteryDungeon.characters.Pokemon.Enums.COLOR_GRAY;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

//Wiki-page https://github.com/daviscook477/BaseMod/wiki/Custom-Characters
//and https://github.com/daviscook477/BaseMod/wiki/Migrating-to-5.0
//All text (starting description and loadout, anything labeled TEXT[]) can be found in MysteryDungeon-character-Strings.json in the resources

public class Pokemon extends CustomPlayer implements CustomSavable<Pokemon.Adventurer[]>{
    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());

    // =============== CHARACTER ENUMERATORS =================
    // These are enums for your Characters color (both general color and for the card library) as well as
    // an enum for the name of the player class - IRONCLAD, THE_SILENT, DEFECT, YOUR_CLASS ...
    // These are all necessary for creating a character. If you want to find out where and how exactly they are used
    // in the basegame (for fun and education) Ctrl+click on the PlayerClass, CardColor and/or LibraryType below and go down the
    // Ctrl+click rabbit hole

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_POKEMON;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor COLOR_GRAY;
        @SpireEnum(name = "DEFAULT_GRAY_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
        @SpireEnum(name = "BULBASAUR_GREEN") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor BULBASAUR_GREEN;
        @SpireEnum(name = "BULBASAUR_GREEN") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_BULBASAUR_GREEN;
        @SpireEnum(name = "CHARMANDER_RED") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor CHARMANDER_RED;
        @SpireEnum(name = "CHARMANDER_RED") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_CHARMANDER_RED;
        @SpireEnum(name = "SQUIRTLE_BLUE") // These two HAVE to have the same absolutely identical name.
        public static AbstractCard.CardColor SQUIRTLE_BLUE;
        @SpireEnum(name = "SQUIRTLE_BLUE") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_SQUIRTLE_BLUE;
    }

    // =============== CHARACTER ENUMERATORS  =================



    // =============== BASE STATS =================

    public static final int ENERGY_PER_TURN = 3;
    public static final int STARTING_HP = 66;
    public static final int MAX_HP = 66;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 3;
    public static Nature nature;
    public static Adventurer adventurer;
    public static Adventurer partner;

    // =============== /BASE STATS/ =================


    // =============== STRINGS =================

    private static final String ID = makeID("Pokemon");
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    // =============== /STRINGS/ =================

    public static enum Nature
    {
        Brave,
        Calm,
        Docile,
        Hardy,
        Hasty,
        Impish,
        Jolly,
        Lonely,
        Naive,
        Quirky,
        Relaxed,
        Sassy,
        Timid
    }
    public static enum Adventurer
    {
        Bulbasaur,
        Charmander,
        Squirtle,
        Pikachu,
        Meowth,
        Psyduck,
        Machop,
        Cubone,
        Eevee,
        Chikorita,
        Cyndaquil,
        Totodile,
        Treecko,
        Torchic,
        Mudkip,
        Skitty
    }


    // =============== TEXTURES OF BIG ENERGY ORB ===============

    public static final String[] orbTextures = {
            "mysteryDungeonResources/images/char/pokemon/orb/layer1.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer2.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer3.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer4.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer5.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer6.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer1d.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer2d.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer3d.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer4d.png",
            "mysteryDungeonResources/images/char/pokemon/orb/layer5d.png",};

    // =============== /TEXTURES OF BIG ENERGY ORB/ ===============

    // =============== CHARACTER CLASS START =================

    public Pokemon(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures,
                "mysteryDungeonResources/images/char/pokemon/orb/vfx.png", null,
                new SpriterAnimation(
                        "mysteryDungeonResources/images/char/pokemon/Spriter/mysteryDungeonAnimation.scml"));


        // =============== TEXTURES, ENERGY, LOADOUT =================  

        initializeClass(null, // required call to load textures and setup energy/loadout.
                // I left these in MysteryDungeon.java (Ctrl+click them to see where they are, Ctrl+hover to see what they read.)
                THE_DEFAULT_SHOULDER_2, // campfire pose
                THE_DEFAULT_SHOULDER_1, // another campfire pose
                THE_DEFAULT_CORPSE, // dead corpse
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN)); // energy manager

        // =============== /TEXTURES, ENERGY, LOADOUT/ =================


        // =============== ANIMATIONS =================  

        loadAnimation(
                THE_DEFAULT_SKELETON_ATLAS,
                THE_DEFAULT_SKELETON_JSON,
                1.0f);
        AnimationState.TrackEntry e = state.setAnimation(0, "animation", true);
        e.setTime(e.getEndTime() * MathUtils.random());

        // =============== /ANIMATIONS/ =================


        // =============== TEXT BUBBLE LOCATION =================

        dialogX = (drawX + 0.0F * Settings.scale); // set location for text bubbles
        dialogY = (drawY + 220.0F * Settings.scale); // you can just copy these values

        // =============== /TEXT BUBBLE LOCATION/ =================

    }

    // =============== /CHARACTER CLASS END/ =================

    // Starting description and loadout
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
	public ArrayList<AbstractCard> getCardPool(ArrayList<AbstractCard> tmpPool)
	{
        ArrayList<AbstractCard.CardColor> colors = getUsedSubColors();
        for(AbstractCard.CardColor color : colors)
        {
            for (Map.Entry<String, AbstractCard> c : CardLibrary.cards.entrySet()) {
                AbstractCard card = c.getValue();
                if (card.color.equals(color) && card.rarity != AbstractCard.CardRarity.BASIC &&
                        (!UnlockTracker.isCardLocked(c.getKey()) || Settings.isDailyRun)) {
                    tmpPool.add(card);
                }
            }
        }
		
		return tmpPool;
	}

    // Starting Deck
    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();

        logger.info("Begin loading starter Deck Strings");
        retVal.add(BulbasaurDefend.ID);
        retVal.add(BulbasaurDefend.ID);
        return retVal;
    }

    public void awardStartingDecks(Adventurer pokemon)
    {
        ArrayList<AbstractCard> retVal = new ArrayList<AbstractCard>();
        switch(pokemon)
        {
            case Bulbasaur:
                retVal.add(new BulbasaurTackle());
                retVal.add(new BulbasaurTackle());
                retVal.add(new BulbasaurDefend());
                retVal.add(new BulbasaurDefend());
                retVal.add(new BulbasaurLeechSeed());
                break;
            case Charmander:
                retVal.add(new CharmanderScratch());
                retVal.add(new CharmanderScratch());
                retVal.add(new CharmanderDefend());
                retVal.add(new CharmanderDefend());
                retVal.add(new CharmanderEmber());
                break;
            case Squirtle:
                retVal.add(new SquirtleTackle());
                retVal.add(new SquirtleTackle());
                retVal.add(new SquirtleDefend());
                retVal.add(new SquirtleDefend());
                retVal.add(new SquirtleWaterGun());
                break;
            case Pikachu:
                break;
            case Meowth:
                break;
            case Psyduck:
                break;
            case Machop:
                break;
            case Cubone:
                break;
            case Eevee:
                break;
            case Chikorita:
                break;
            case Cyndaquil:
                break;
            case Totodile:
                break;
            case Treecko:
                break;
            case Torchic:
                break;
            case Mudkip:
                break;
            case Skitty:
                break;
            default:
                break;
        }
        for(AbstractCard card: retVal)
        {
            masterDeck.addToTop(card);
        }
    }

    // Starting Relics	
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();

        retVal.add(NatureRelatedRelic.ID);

        // Mark relics as seen - makes it visible in the compendium immediately
        // If you don't have this it won't be visible in the compendium until you see them in game
        UnlockTracker.markRelicAsSeen(NatureRelatedRelic.ID);

        return retVal;
    }

    @Override
    public Adventurer[] onSave()
    {
        return new Adventurer[]{adventurer, partner};
    }

    @Override
    public void onLoad(Adventurer[] adventurerAndPartner)
    {
        if(adventurerAndPartner!=null && adventurerAndPartner instanceof Adventurer[])
        {
            adventurer = ((Adventurer[])adventurerAndPartner)[0];
            partner = ((Adventurer[])adventurerAndPartner)[1];
        } 
    }

    @Override
    public Type savedType()
    {
        return new TypeToken<Adventurer[]>(){}.getType();
    }

    // character Select screen effect
    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_1", 1.25f); // Sound Effect
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false); // Screen Effect
    }

    // character Select on-button-press sound effect
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_DAGGER_1";
    }

    // Should return how much HP your maximum HP reduces by when starting a run at
    // Ascension 14 or higher. (ironclad loses 5, defect and silent lose 4 hp respectively)
    @Override
    public int getAscensionMaxHPLoss() {
        return 6;
    }

    // Should return the card color enum to be associated with your character.
    @Override
    public AbstractCard.CardColor getCardColor() {
        return COLOR_GRAY;
    }

    // Should return a color object to be used to color the trail of moving cards
    @Override
    public Color getCardTrailColor() {
        return adventurerOrPartnerColor();
    }

    // Should return a BitmapFont object that you can use to customize how your
    // energy is displayed from within the energy orb.
    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    // Should return class name as it appears in run history screen.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    //Which card should be obtainable from the Match and Keep event?
    @Override
    public AbstractCard getStartCardForEvent() {
        return new BulbasaurTackle();
    }

    // The class name as it appears next to your player name in-game
    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    // Should return a new instance of your character, sending name as its name parameter.
    @Override
    public AbstractPlayer newInstance() {
        return new Pokemon(name, chosenClass);
    }

    // Should return a Color object to be used to color the miniature card images in run history.
    @Override
    public Color getCardRenderColor() {
        return mysteryDungeon.MysteryDungeon.DEFAULT_GRAY;
    }

    // Should return a Color object to be used as screen tint effect when your
    // character attacks the heart.
    @Override
    public Color getSlashAttackColor() {
        return mysteryDungeon.MysteryDungeon.DEFAULT_GRAY;
    }

    // Should return an AttackEffect array of any size greater than 0. These effects
    // will be played in sequence as your character's finishing combo on the heart.
    // Attack effects are the same as used in DamageAction and the like.
    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY};
    }

    // Should return a string containing what text is shown when your character is
    // about to attack the heart. For example, the defect is "NL You charge your
    // core to its maximum..."
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    // The vampire events refer to the base game characters as "brother", "sister",
    // and "broken one" respectively.This method should return a String containing
    // the full text that will be displayed as the first screen of the vampires event.
    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public void DefineNature(String natureAsAString)
    {
        nature = Nature.valueOf(natureAsAString);
    }

    public void setAdventurer(String adventurerAsString)
    {
        adventurer = Adventurer.valueOf(adventurerAsString);
        awardStartingDecks(adventurer);
    }

    public void setPartner(String adventurerAsString)
    {
        partner = Adventurer.valueOf(adventurerAsString);
        awardStartingDecks(partner);
    }

    // Adventurer/Partner related switch statements

    public static Color adventurersColor(Adventurer pokemon)
    {
        switch(pokemon)
        {
            case Bulbasaur:
                return Color.GREEN;
            case Charmander:
                return Color.RED;
            case Squirtle:
                return Color.BLUE;
            case Pikachu:
                return Color.YELLOW;
            case Meowth:
                return Color.WHITE;
            case Psyduck:
                return Color.VIOLET;
            case Machop:
                return Color.BROWN;
            case Cubone:
                return Color.MAROON;
            case Eevee:
                return Color.WHITE;
            case Chikorita:
                return Color.GREEN;
            case Cyndaquil:
                return Color.RED;
            case Totodile:
                return Color.BLUE;
            case Treecko:
                return Color.GREEN;
            case Torchic:
                return Color.RED;
            case Mudkip:
                return Color.BLUE;
            case Skitty:
                return Color.WHITE;
            default:
                return mysteryDungeon.MysteryDungeon.DEFAULT_GRAY;
        }
    }

    public static Color adventurersColor(String pokemon)
    {
        return adventurersColor(Adventurer.valueOf(pokemon));
    }

    public Color adventurerOrPartnerColor()
    {
        return (new Random().nextInt() % 2 == 0)?adventurersColor(adventurer):adventurersColor(partner);
    }

    public ArrayList<AbstractCard.CardColor> getUsedSubColors()
    {
        ArrayList<AbstractCard.CardColor> subcolors = new ArrayList<AbstractCard.CardColor>();
        if(adventurer == null || partner == null)
        {
            subcolors.add(Enums.COLOR_GRAY);
            return subcolors;
        }
        switch(adventurer)
        {
            case Bulbasaur:
                subcolors.add(Enums.BULBASAUR_GREEN);
                break;
            case Charmander:
                subcolors.add(Enums.CHARMANDER_RED);
                break;
            case Squirtle:
                subcolors.add(Enums.SQUIRTLE_BLUE);
                break;
            case Pikachu:
                break;
            case Meowth:
                break;
            case Psyduck:
                break;
            case Machop:
                break;
            case Cubone:
                break;
            case Eevee:
                break;
            case Chikorita:
                break;
            case Cyndaquil:
                break;
            case Totodile:
                break;
            case Treecko:
                break;
            case Torchic:
                break;
            case Mudkip:
                break;
            case Skitty:
                break;
            default:
                subcolors.add(Enums.COLOR_GRAY);
        }
        switch(partner)
        {
            case Bulbasaur:
                subcolors.add(Enums.BULBASAUR_GREEN);
                break;
            case Charmander:
                subcolors.add(Enums.CHARMANDER_RED);
                break;
            case Squirtle:
                subcolors.add(Enums.SQUIRTLE_BLUE);
                break;
            case Pikachu:
                break;
            case Meowth:
                break;
            case Psyduck:
                break;
            case Machop:
                break;
            case Cubone:
                break;
            case Eevee:
                break;
            case Chikorita:
                break;
            case Cyndaquil:
                break;
            case Totodile:
                break;
            case Treecko:
                break;
            case Torchic:
                break;
            case Mudkip:
                break;
            case Skitty:
                break;
            default:
                subcolors.add(Enums.COLOR_GRAY);
        }
        return subcolors;
    }

    public AbstractRelic natureRelatedRelic()
    {
        switch(nature)
        {
            case Brave:
                return new BraveExplorerRelic();
            case Calm:
                return new CalmExplorerRelic();
            case Docile:
                return new DocileExplorerRelic();
            case Hardy:
                return new HardyExplorerRelic();
            case Hasty:
                return new CalmExplorerRelic();
            case Impish:
                return new ImpishExplorerRelic();
            case Jolly:
                return new JollyExplorerRelic();
            case Lonely:
                return new CalmExplorerRelic();
            case Naive:
                return new CalmExplorerRelic();
            case Quirky:
                return new QuirkyExplorerRelic();
            case Relaxed:
                return new RelaxedExplorerRelic();
            case Sassy:
                return new CalmExplorerRelic();
            case Timid:
                return new TimidExplorerRelic();
            default:
                return new CalmExplorerRelic();
        }
    }

    public void AwardStartingRelic()
    {
        this.loseRelic(NatureRelatedRelic.ID);
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2, Settings.HEIGHT / 2, natureRelatedRelic());
    }


}
