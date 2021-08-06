package mysteryDungeon.patches;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.cards.fakeCards.ExplorersDeck;
import mysteryDungeon.cards.fakeCards.PartnersDeck;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.util.LocalizationTool;

public class PokemonNeowPatch {

    public static final Logger logger = LogManager.getLogger(PokemonNeowPatch.class.getName());

    public static int answeredQuestions = 0;

    public static int screenNum = 0;

    public static HashMap<String,Integer> traits = new HashMap<String, Integer>();

    public static final String[] TEXT = CardCrawlGame.languagePack.getCharacterString("Neow").TEXT;

    public static ArrayList<Question> questions = new ArrayList<Question>();

    public static Question followUpQuestion;
    
    public static Question lastQuestion;

    public static String chosenPokemon;

    public static String[] possiblePartners;

    public static boolean alienInvasion = false;

    public static boolean isTestRun = false;

    public static void InitializeTraitsScore()
    {
        logger.info(traits);
        traits.clear();
        traits.put("Brave",0);
        traits.put("Calm",0);
        traits.put("Docile",0);
        traits.put("Hardy",0);
        traits.put("Hasty",0);
        traits.put("Impish",0);
        traits.put("Jolly",0);
        traits.put("Lonely",0);
        traits.put("Naive",0);
        traits.put("Quirky",0);
        traits.put("Relaxed",0);
        traits.put("Sassy",0);
        traits.put("Timid",0);
    }

    public static void InitializeQuestions()
    {
        questions.clear();
        String json = Gdx.files.internal(LocalizationTool.LocalizationPath() + "Questions.json").readString(String.valueOf(StandardCharsets.UTF_8));
        ArrayList<Question> fullListOfQuestions = new Gson().fromJson(json, new TypeToken<ArrayList<Question>>() {}.getType());
        lastQuestion = fullListOfQuestions.remove(fullListOfQuestions.size()-1);
        followUpQuestion = fullListOfQuestions.remove(fullListOfQuestions.size()-1);
        for (int i=fullListOfQuestions.size(); i>1; i--)
            Collections.swap(fullListOfQuestions, i-1, AbstractDungeon.eventRng.random(i-1));
        questions = new ArrayList<Question>(fullListOfQuestions.subList(0, 7));
    }

    public static void dismissBubble() {
        for (AbstractGameEffect e : AbstractDungeon.effectList) {
          if (e instanceof InfiniteSpeechBubble)
            ((InfiniteSpeechBubble)e).dismiss(); 
        } 
    }

    public static void talk(String msg) {
        AbstractDungeon.effectList.add(new InfiniteSpeechBubble(1100.0F * Settings.xScale, AbstractDungeon.floorY + 60.0F * Settings.yScale, msg));
    }

    public static void ShowNextDialog(NeowEvent __instance)
    {
        dismissBubble();
        talk(TEXT[screenNum]);
        __instance.roomEventText.clear();
        __instance.roomEventText.addDialogOption("[ ... ]");
        screenNum++;
    }

    public static void DeterminePokemon(int buttonPressed)
    {
        ArrayList<String> bestCorrespondingTraits = new ArrayList<String>();
        int maxValue = 0;
        for (Map.Entry<String, Integer> trait : traits.entrySet())
        {
            if((trait.getValue()==maxValue)||(bestCorrespondingTraits.isEmpty()))
            {
                bestCorrespondingTraits.add(trait.getKey());
                maxValue = trait.getValue();
            }
            else if(trait.getValue()>maxValue)
            {
                bestCorrespondingTraits.clear();
                bestCorrespondingTraits.add(trait.getKey());
                maxValue = trait.getValue();
            }
        }
        String nature = bestCorrespondingTraits.get(AbstractDungeon.eventRng.random(bestCorrespondingTraits.size()-1));
        ((Pokemon)AbstractDungeon.player).DefineNature(nature);
        switch(buttonPressed)
        {
            // Player chose Boy
            case 0:
                chosenPokemon = malePokemonChoices.get(nature);
                return;
            // Player chose Boy
            case 1:
                chosenPokemon = femalePokemonChoices.get(nature);
                return;
            // Player chose other
            case 2:
            {
                switch(AbstractDungeon.eventRng.random(1))
                {
                    case 0:
                    {
                        chosenPokemon = malePokemonChoices.get(nature);
                        return;
                    }
                    case 1:
                        chosenPokemon = femalePokemonChoices.get(nature);
                        return;
                }
            }   
        }
    }

    public static void AskQuestion(NeowEvent __instance)
    {
        Question questionToAsk = questions.get(answeredQuestions);
        AskQuestion(__instance, questionToAsk);
    }

    public static void AskQuestion(NeowEvent __instance, Question questionToAsk)
    {
        dismissBubble();
        talk(questionToAsk.QUESTION);
        __instance.roomEventText.clear();
        for(String answer : questionToAsk.ANSWERS)
        {
            __instance.roomEventText.addDialogOption(answer);
        }
    }

    public static void UpdatePoints(NeowEvent __instance, int buttonPressed)
    {
        Question questionAnswered = questions.get(answeredQuestions-1);
        UpdatePoints(__instance, buttonPressed, questionAnswered);
    }

    public static void UpdatePoints(NeowEvent __instance, int buttonPressed, Question questionAnswered)
    {
        String[] points = questionAnswered.POINTS[buttonPressed];
        for(String trait : points)
        {
            logger.info(trait);
            logger.info(trait=="SPECIAL");
            if(trait.equals("SPECIAL"))
            {
                AskQuestion(__instance, followUpQuestion);
                alienInvasion = true;
                return;
            }
            String traitName = trait.split(":")[0];
            int amount = Integer.parseInt(trait.split(":")[1]);
            traits.replace(traitName, traits.get(traitName)+amount);
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "<ctor>", paramtypez = {boolean.class})
    public static class BeginNeowEvent{
        @SpirePrefixPatch
        public static void ChangeEventIfPokemon(NeowEvent __instance, boolean isDone) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return;
            }
            answeredQuestions = 0;
            screenNum = 0;
            InitializeTraitsScore();
            InitializeQuestions();
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect")
    public static class NeowEventFlow {
        @SpirePrefixPatch
        @SuppressWarnings("all")
        public static SpireReturn AdvanceAccordingToChoice(NeowEvent __instance, int buttonPressed)
        {
            logger.info(screenNum);
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            switch(screenNum)
            {
                case 0:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 1:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 2:
                    AskQuestion(__instance, new Question(TEXT[screenNum], new String[]{"Yes", "TEST RUN PLZ", "I'd like the quicker option"}));
                    screenNum++;
                    return SpireReturn.Return(null);
                case 3:
                    switch(buttonPressed)
                    {
                        case 0:
                            break;
                        case 1:
                            screenNum=10;
                            isTestRun = true;
                            break;
                        case 2:
                            break;
                    }
                    logger.info(isTestRun);
                    if(!isTestRun)
                    {
                        logger.info("will show next dialog");
                        ShowNextDialog(__instance);
                        return SpireReturn.Return(null);
                    }
                    AdvanceAccordingToChoice(__instance, buttonPressed);
                    return SpireReturn.Return(null);
                // Personality Quiz
                case 4:
                    // First Question
                    if(answeredQuestions==0)
                    {
                        logger.info("asking first question");
                        AskQuestion(__instance);
                    }
                    // Boy or Girl Question
                    else if(answeredQuestions==7)
                    {
                        UpdatePoints(__instance, buttonPressed);
                        AskQuestion(__instance, lastQuestion);
                    }
                    else if(alienInvasion)
                    {
                        UpdatePoints(__instance, buttonPressed, followUpQuestion);
                        AskQuestion(__instance);
                        alienInvasion = false;
                    }
                    // Results
                    else if(answeredQuestions==8)
                    {
                        DeterminePokemon(buttonPressed);
                        //TODO: remove/change when more pokémon are implemented
                        convertPokemonToImplementedPokemon(); 
                        ((Pokemon)AbstractDungeon.player).setAdventurer(chosenPokemon);
                        possiblePartners = partnerChoices();
                        AskQuestion(__instance, new Question(chosenPokemon, possiblePartners, null));
                        screenNum++;
                        return SpireReturn.Return(null);
                    }
                    else
                    {
                        UpdatePoints(__instance, buttonPressed);
                        if (alienInvasion) 
                        {
                            AskQuestion(__instance, followUpQuestion);
                        }
                        else AskQuestion(__instance);
                    }
                    if (alienInvasion) 
                    {
                        answeredQuestions--;
                    }
                    answeredQuestions++;
                    return SpireReturn.Return(null);
                // Neow's last word + Partner attribution
                case 5:
                    if(!isTestRun)
                        ((Pokemon)AbstractDungeon.player).setPartner(possiblePartners[buttonPressed]);
                    if(isTestRun)
                    {
                        ((Pokemon)AbstractDungeon.player).setPartner(implementedPokemons[buttonPressed]);
                    }
                    AskQuestion(__instance, new Question("I see, now brave the challenge of the tower", new String[]{"[Leave]"}));
                    screenNum++;
                    CardCrawlGame.dungeon.initializeCardPools();
                    AbstractDungeon.player.masterDeck.removeCard(ExplorersDeck.ID);
                    AbstractDungeon.player.masterDeck.removeCard(PartnersDeck.ID);
                    ((Pokemon)AbstractDungeon.player).AwardStartingRelic();
                    return SpireReturn.Return(null);
                case 6:
                    break;
                // Test run choose option
                case 10:
                    AskQuestion(__instance, new Question("Choose your adventurer", implementedPokemons));
                    screenNum++;
                    return SpireReturn.Return(null);
                case 11:
                    ((Pokemon)AbstractDungeon.player).setAdventurer(implementedPokemons[buttonPressed]);
                    ((Pokemon)AbstractDungeon.player).DefineNature(NatureOfPokemon.get(implementedPokemons[buttonPressed])[AbstractDungeon.eventRng.random(1)]);
                    AskQuestion(__instance, new Question("Choose your partner", implementedPokemons));
                    screenNum=5;
                    return SpireReturn.Return(null);
                    
            }
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.dungeonMapScreen.open(false);
            PokemonNeowPatch.screenNum = 99;
            return SpireReturn.Return(null);
        }
    }

    public static String[] partnerChoices()
    {
        ArrayList<String> partners = new ArrayList<String>()
        {{
            add("Bulbasaur");
            add("Charmander");
            add("Squirtle");
            add("Pikachu");
            //TODO: uncomment next lines when those pokémons while be added
            //add("Chikorita");
            //add("Cyndaquil");
            //add("Totodile");
            //add("Treecko");
            //add("Torchic");
            //add("Mudkip");
        }};
        switch(chosenPokemon)
        {
            case "Bulbasaur":
                partners.remove("Bulbasaur");
                partners.remove("Chikorita");
                partners.remove("Treecko");
                break;
            case "Chikorita":
                partners.remove("Bulbasaur");
                partners.remove("Chikorita");
                partners.remove("Treecko");
                break;
            case "Treecko":
                partners.remove("Bulbasaur");
                partners.remove("Chikorita");
                partners.remove("Treecko");
                break;
            case "Charmander":
                partners.remove("Charmander");
                partners.remove("Cyndaquil");
                partners.remove("Torchic");
                break;
            case "Cyndaquil":
                partners.remove("Charmander");
                partners.remove("Cyndaquil");
                partners.remove("Torchic");
                break;
            case "Torchic":
                partners.remove("Charmander");
                partners.remove("Cyndaquil");
                partners.remove("Torchic");
                break;
            case "Squirtle":
                partners.remove("Squirtle");
                partners.remove("Totodile");
                partners.remove("Mudkip");
                break;
            case "Psyduck" :
                partners.remove("Squirtle");
                partners.remove("Totodile");
                partners.remove("Mudkip");
                break;
            case "Totodile":
                partners.remove("Squirtle");
                partners.remove("Totodile");
                partners.remove("Mudkip");
                break;
            case "Mudkip":
                partners.remove("Squirtle");
                partners.remove("Totodile");
                partners.remove("Mudkip");
                break;
            case "Pikachu":
                partners.remove("Pikachu");
        }
        for (int i=partners.size(); i>1; i--)
            Collections.swap(partners, i-1, AbstractDungeon.eventRng.random(i-1));
        ArrayList<String> selectablePartners = new ArrayList<String>();
        selectablePartners.add(partners.get(0));
        for(String partner : partners)
        {
            boolean toAdd = false;
            for(String sPartner : selectablePartners)
            {
                if(Pokemon.adventurersColor(partner) != Pokemon.adventurersColor(sPartner))
                {
                    toAdd = true;
                }
                else
                {
                    toAdd = false;
                    break;
                }
            }
            if(toAdd)
            {
                selectablePartners.add(partner);
            }
        }
        return selectablePartners.toArray(new String[selectablePartners.size()]);
    }


    public static void convertPokemonToImplementedPokemon()
    {
        Color pokemonsType = Pokemon.adventurersColor(chosenPokemon);
        if(pokemonsType == Pokemon.adventurersColor("Bulbasaur"))
        {
            chosenPokemon = "Bulbasaur";
        }
        else if(pokemonsType == Pokemon.adventurersColor("Charmander"))
        {
            chosenPokemon = "Charmander";
        }
        else if(pokemonsType == Pokemon.adventurersColor("Squirtle"))
        {
            chosenPokemon = "Squirtle";
        }
        else
        {
            chosenPokemon = "Pikachu";
        }
    }

    // Tables for pokémon choices

    public static Map<String,String> malePokemonChoices = new HashMap<String,String>()
    {{
        put("Docile", "Bulbasaur");
        put("Hardy", "Charmander");
        put("Jolly", "Squirtle");
        put("Impish", "Pikachu");
        put("Quirky", "Meowth");
        put("Relaxed", "Psyduck");
        put("Brave", "Machop");
        put("Lonely", "Cubone");
        put("Timid", "Cyndaquil");
        put("Naive", "Totodile");
        put("Sassy", "Treecko");
        put("Hasty", "Torchic");
        put("Calm", "Mudkip");
    }};

    public static Map<String,String> femalePokemonChoices = new HashMap<String,String>()
    {{
        put("Calm", "Bulbasaur");
        put("Brave", "Charmander");
        put("Relaxed", "Squirtle");
        put("Hardy", "Pikachu");
        put("Lonely", "Psyduck");
        put("Impish", "Cubone");
        put("Naive", "Eevee");
        put("Docile", "Chikorita");
        put("Jolly", "Totodile");
        put("Quirky", "Treecko");
        put("Sassy", "Torchic");
        put("Timid", "Mudkip");
        put("Hasty", "Skitty");
    }};

    public static Map<String,String[]> NatureOfPokemon = new HashMap<String,String[]>()
    {{
        put("Bulbasaur", new String[]{"Calm", "Docile"});
        put("Charmander", new String[]{"Hardy", "Brave"});
        put("Squirtle", new String[]{"Jolly", "Relaxed"});
    }};

    public static String[] implementedPokemons = new String[]{"Bulbasaur", "Charmander", "Squirtle"};

    // Data structure to which the .json is fed
    public static class Question{
        public String QUESTION;
        public String[] ANSWERS;
        public String[][] POINTS;

        public Question(String QUESTION, String[] ANSWERS, String[][] POINTS)
        {
            this.QUESTION = QUESTION;
            this.ANSWERS = ANSWERS;
            this.POINTS = POINTS;
        }
        public Question(String QUESTION, String[] ANSWERS)
        {
            this.QUESTION = QUESTION;
            this.ANSWERS = ANSWERS;
            this.POINTS = null;
        }
    }
}
