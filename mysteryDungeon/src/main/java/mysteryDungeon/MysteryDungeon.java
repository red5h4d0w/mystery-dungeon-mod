package mysteryDungeon;

import basemod.*;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.*;
import mysteryDungeon.cards.Bulbasaur.*;
import mysteryDungeon.cards.Charmander.CharmanderScratch;
import mysteryDungeon.cards.Chikorita.ChikoritaTackle;
import mysteryDungeon.cards.Cyndaquil.CyndaquilTackle;
import mysteryDungeon.cards.Pikachu.PikachuTackle;
import mysteryDungeon.cards.Squirtle.SquirtleTackle;
import mysteryDungeon.cards.Status.StatusFreeze;
import mysteryDungeon.cards.Totodile.TotodileScratch;
import mysteryDungeon.cards.fakeCards.ExplorersDeck;
import mysteryDungeon.cards.tempCards.ColorlessAgility;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.relics.NatureRelatedRelic;
import mysteryDungeon.saveConstructs.ToSave;
import mysteryDungeon.util.LocalizationTool;
import mysteryDungeon.util.TextureLoader;
import mysteryDungeon.variables.DefaultCustomVariable;
import mysteryDungeon.variables.SecondMagicNumber;
import mysteryDungeon.variables.ThirdMagicNumber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.Properties;


// Right click the package (Open the project pane on the left. Folder with black dot on it. The name's at the very top) -> Refactor -> Rename, and name it whatever you wanna call your mod.
// Scroll down in this file. Change the ID from "Pokemon:" to "yourModName:" or whatever your heart desires (don't use spaces). Dw, you'll see it.
// In the JSON strings (resources>localization>eng>[all them files] make sure they all go "yourModName:" rather than "Pokemon", and change to "yourmodname" rather than "Pokemon".
// You can ctrl+R to replace in 1 file, or ctrl+shift+r to mass replace in specific files/directories, and press alt+c to make the replace case sensitive (Be careful.).
// Start with the DefaultCommon cards - they are the most commented cards since I don't feel it's necessary to put identical comments on every card.
// After you sorta get the hang of how to make cards, check out the card template which will make your life easier

/*
 * With that out of the way:
 * Welcome to this super over-commented Slay the Spire modding base.
 * Use it to make your own mod of any type. - If you want to add any standard in-game content (character,
 * cards, relics), this is a good starting point.
 * It features 1 character with a minimal set of things: 1 card of each type, 1 debuff, couple of relics, etc.
 * If you're new to modding, you basically *need* the BaseMod wiki for whatever you wish to add
 * https://github.com/daviscook477/BaseMod/wiki - work your way through with this base.
 * Feel free to use this in any way you like, of course. MIT licence applies. Happy modding!
 *
 * And pls. Read the comments.
 */

@SpireInitializer
public class MysteryDungeon implements
        AddAudioSubscriber,
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    // Make sure to implement the subscribers *you* are using (read basemod wiki). Editing cards? EditCardsSubscriber.
    // Making relics? EditRelicsSubscriber. etc., etc., for a full list and how to make your own, visit the basemod wiki.
    public static final Logger logger = LogManager.getLogger(MysteryDungeon.class.getName());
    private static String modID;

    // Developper Settings
    public static final boolean TOGGLE_ON_SHINY = false;
    public static final boolean ENFORCE_LOGS = false;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties mysteryDungeonDefaultSettings = new Properties();
    public static final String SEND_RUN_DATA = "FALSE";
    public static boolean sendRunData = false; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "Mystery Dungeon";
    private static final String AUTHOR = "red5h4d0w, Ivel54"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64.0f, 70.0f, 70.0f);
    
    // Potion Colors in RGB
    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209.0f, 53.0f, 18.0f); // Orange-ish Red
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255.0f, 230.0f, 230.0f); // Near White
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100.0f, 25.0f, 10.0f); // Super Dark Red/Brown
  
    // Card backgrounds - The actual rectangular card.
    private static final String ATTACK_DEFAULT_GRAY = "mysteryDungeonResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "mysteryDungeonResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "mysteryDungeonResources/images/512/bg_power_default_gray.png";
    
    private static final String ENERGY_ORB_DEFAULT_GRAY = "mysteryDungeonResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "mysteryDungeonResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "mysteryDungeonResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "mysteryDungeonResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "mysteryDungeonResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "mysteryDungeonResources/images/1024/card_default_gray_orb.png";
    
    // Character assets
    private static final String THE_DEFAULT_BUTTON = "mysteryDungeonResources/images/charSelect/PokemonCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "mysteryDungeonResources/images/charSelect/mysteryDungeonCover.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "mysteryDungeonResources/images/char/pokemon/Bulbasaur_back.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "mysteryDungeonResources/images/char/pokemon/Charmander_back.png";
    public static final String THE_DEFAULT_CORPSE = "mysteryDungeonResources/images/char/pokemon/corpse.png";
    
    //Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "mysteryDungeonResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String THE_DEFAULT_SKELETON_ATLAS = "mysteryDungeonResources/images/char/pokemon/Bulbasaur/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "mysteryDungeonResources/images/char/pokemon/Bulbasaur/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeAudioPath(String resourcePath) {
        return getModID() + "Resources/sound/" + resourcePath;
    }

    public static String makeBackSpritePath(String resourcePath) {
        return getModID() + "Resources/images/backSprites/" + resourcePath + ".png";
    }

    public static String makeBackSpritePath(String resourcePath, boolean shiny) {
        return makeBackSpritePath(resourcePath + (shiny?"_S":""));
    }

    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }

    public static String makeSkeletonPath(String pokemonName) {
        return getModID() + "Resources/images/char/pokemon/" + pokemonName + "/skeleton.json";
    }

    public static String makeSkeletonPath(String pokemonName, boolean shiny) {
        return makeSkeletonPath(pokemonName + (shiny?"_S":""));
    }

    public static String makeAtlasPath(String pokemonName) {
        return getModID() + "Resources/images/char/pokemon/" + pokemonName + "/skeleton.atlas";
    }

    public static String makeAtlasPath(String pokemonName, boolean shiny) {
        return makeAtlasPath(pokemonName + (shiny?"_S":""));
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePotionPath(String resourcePath) {
        return getModID() + "Resources/images/potions/" + resourcePath;
    }

    public static String makePotionOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/potions/outline/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    
    
    // =============== /MAKE IMAGE PATHS/ =================
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    
    // =============== SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE =================
    
    public MysteryDungeon() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
      
        setModID("mysteryDungeon");
        
        logger.info("Done subscribing");
        
        logger.info("Creating colors...");
        
        BaseMod.addColor(Pokemon.Enums.COLOR_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
            DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY, DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
    
        BaseMod.addColor(Pokemon.Enums.BULBASAUR_GREEN, Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(),
            Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Pokemon.Enums.CHARMANDER_RED, Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(),
            Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Pokemon.Enums.SQUIRTLE_BLUE, Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(),
            Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        BaseMod.addColor(Pokemon.Enums.PIKACHU_YELLOW, Color.YELLOW.cpy(), Color.YELLOW.cpy(), Color.YELLOW.cpy(),
            Color.YELLOW.cpy(), Color.YELLOW.cpy(), Color.YELLOW.cpy(), Color.YELLOW.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Pokemon.Enums.MEOWTH_WHITE, Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(),
            Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(), Color.WHITE.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Pokemon.Enums.CHIKORITA_GREEN, Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(),
            Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(), Color.GREEN.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        
        BaseMod.addColor(Pokemon.Enums.CYNDAQUIL_RED, Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(),
            Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(), Color.RED.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);

        BaseMod.addColor(Pokemon.Enums.TOTODILE_BLUE, Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(),
            Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(), Color.BLUE.cpy(),
            ATTACK_DEFAULT_GRAY, SKILL_DEFAULT_GRAY, POWER_DEFAULT_GRAY, ENERGY_ORB_DEFAULT_GRAY,
            ATTACK_DEFAULT_GRAY_PORTRAIT, SKILL_DEFAULT_GRAY_PORTRAIT, POWER_DEFAULT_GRAY_PORTRAIT,
            ENERGY_ORB_DEFAULT_GRAY_PORTRAIT, CARD_ENERGY_ORB);
        logger.info("Done creating the colors");
        
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        mysteryDungeonDefaultSettings.setProperty(SEND_RUN_DATA, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("mysteryDungeon", "mysteryDungeonConfig", mysteryDungeonDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            sendRunData = config.getBool(SEND_RUN_DATA);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
    }
    
    public static void setModID(String ID) { 
        modID = ID; 
        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
    }
    
    public static String getModID() {
        return modID;
    }
    
    // ====== YOU CAN EDIT AGAIN ======
    
    
    public static void initialize() {
        logger.info("========================= Initializing Mystery Dungeon. Hi. =========================");
        @SuppressWarnings("unused")
        MysteryDungeon mysteryDungeon = new MysteryDungeon(); 
        logger.info("========================= Mystery Dungeon Initialized. Hello World./ =========================");
    }
    
    // ============== /SUBSCRIBE, CREATE THE COLOR_GRAY, INITIALIZE/ =================
    
    
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + Pokemon.Enums.THE_POKEMON.toString());
        
        BaseMod.addCharacter(new Pokemon("the Pokémon", Pokemon.Enums.THE_POKEMON),
                THE_DEFAULT_BUTTON, THE_DEFAULT_PORTRAIT, Pokemon.Enums.THE_POKEMON);
        
        receiveEditPotions();
        logger.info("Added " + Pokemon.Enums.THE_POKEMON.toString());
    }
    
    // =============== /LOAD THE CHARACTER/ =================
    
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("Check this to send data on your runs to the developpers of the mod.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                sendRunData, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            sendRunData = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("mysteryDungeon", "mysteryDungeonConfig", mysteryDungeonDefaultSettings);
                config.setBool(SEND_RUN_DATA, sendRunData);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        BaseMod.addSaveField("adventurerAndPartner", (CustomSavable<ToSave>)new Pokemon("the Pokémon", Pokemon.Enums.THE_POKEMON));

        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        
        // Add the event
       

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
    }
    
    // =============== / POST-INITIALIZE/ =================
    
    @Override
    public void receiveAddAudio() {
        logger.info("Beginning to edit audio");
        BaseMod.addAudio("MYSTERY_DUNGEON_RELIC_GET", makeAudioPath("landingSound.ogg"));
    }


    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        new AutoAdd("MysteryDungeon")
            .packageFilter("mysteryDungeon.potions")
            .any(AbstractPotion.class, (info, potion) -> {
                BaseMod.addPotion(potion.getClass(), potion.liquidColor, potion.hybridColor, potion.spotsColor, potion.ID, Pokemon.Enums.THE_POKEMON);
            });
        
        logger.info("Done editing potions");
    }
    
    // ================ /ADD POTIONS/ ===================
    
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // This adds every relic in NatureRelatedRelic's folder of the Pokémon's pool and mark those as seen.
        new AutoAdd("MysteryDungeon")
                .packageFilter(NatureRelatedRelic.class)
                .any(AbstractRelic.class, (info, relic) -> {
                    BaseMod.addRelicToCustomPool(relic, Pokemon.Enums.COLOR_GRAY);
                    UnlockTracker.markRelicAsSeen(relic.relicId);
                });
        
        logger.info("Done adding relics!");
    }
    
    // ================ /ADD RELICS/ ===================
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables...");

        // Add the Custom Dynamic variables
        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new ThirdMagicNumber());

        logger.info("Done adding variables!");
        
        logger.info("Adding cards...");

        // Add Cards from the folowing cards folder and mark those as seen
        new AutoAdd("MysteryDungeon")
            .packageFilter(BulbasaurTackle.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(CharmanderScratch.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(SquirtleTackle.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(PikachuTackle.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(ChikoritaTackle.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(CyndaquilTackle.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(TotodileScratch.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(ColorlessAgility.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(StatusFreeze.class)
            .setDefaultSeen(true)
            .cards();
        new AutoAdd("MysteryDungeon")
            .packageFilter(ExplorersDeck.class)
            .setDefaultSeen(true)
            .cards();

        logger.info("Done adding cards!");
    }
    
    // ================ /ADD CARDS/ ===================
    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID() + " ...");
        
        String localizationPath = LocalizationTool.LocalizationPath();

        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class, localizationPath + "MysteryDungeon-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class, localizationPath + "MysteryDungeon-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class, localizationPath + "MysteryDungeon-Relic-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class, localizationPath + "MysteryDungeon-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class, localizationPath + "MysteryDungeon-Character-Strings.json");
        
        // OrbStrings
        BaseMod.loadCustomStringsFile(OrbStrings.class, localizationPath + "MysteryDungeon-Orb-Strings.json");
        
        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class, localizationPath + "ui.json");

        // StancesStrings
        BaseMod.loadCustomStringsFile(StanceStrings.class, localizationPath + "MysteryDungeon-Stance-Strings.json");

        logger.info("Done editing strings!");
    }
    
    // ================ /LOAD THE TEXT/ ===================
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(LocalizationTool.LocalizationPath() + "MysteryDungeon-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }
    
    // ================ /LOAD THE KEYWORDS/ ===================    
    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    // in order to avoid conflicts if any other mod uses the same ID.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
