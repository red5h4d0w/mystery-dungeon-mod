package mysteryDungeon.patches;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.Gdx;
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

import mysteryDungeon.MysteryDungeon;
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

    public static boolean alienInvasion = false;

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
        String path = MysteryDungeon.getModID() + "Resources" + File.separator + LocalizationTool.LocalizationPath() + File.separator + "Questions.json";
        String json = Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
        ArrayList<Question> fullListOfQuestions = new Gson().fromJson(json, new TypeToken<ArrayList<Question>>() {}.getType());
        lastQuestion = fullListOfQuestions.remove(fullListOfQuestions.size()-1);
        followUpQuestion = fullListOfQuestions.remove(fullListOfQuestions.size()-1);
        Collections.shuffle(fullListOfQuestions);
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
        switch(buttonPressed)
        {
            // Player chose Boy
            case 0:
                chosenPokemon = malePokemonChoices.get(bestCorrespondingTraits.get((new Random()).nextInt(bestCorrespondingTraits.size())));
                return;
            // Player chose Boy
            case 1:
                chosenPokemon = femalePokemonChoices.get(bestCorrespondingTraits.get((new Random()).nextInt(bestCorrespondingTraits.size())));
                return;
            case 2:
                int choice = (new Random()).nextInt(2);
                if(choice==0)
                {
                    chosenPokemon = malePokemonChoices.get(bestCorrespondingTraits.get((new Random()).nextInt(bestCorrespondingTraits.size())));
                    return;
                }
                else
                {
                    chosenPokemon = femalePokemonChoices.get(bestCorrespondingTraits.get((new Random()).nextInt(bestCorrespondingTraits.size())));
                    return;
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
        public static SpireReturn AdvanceAccordingToChoice(NeowEvent __instance, int buttonPressed)
        {
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
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 3:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 4:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 5:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 6:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                // Personality Quiz
                case 7:
                    // First Question
                    if(answeredQuestions==0)
                    {
                        AskQuestion(__instance);
                    }
                    // Boy or Girl Question
                    else if(answeredQuestions==7 && alienInvasion)
                    {
                        UpdatePoints(__instance, buttonPressed, followUpQuestion);
                        AskQuestion(__instance, lastQuestion);
                        alienInvasion = false;
                    }
                    else if(answeredQuestions==7)
                    {
                        if (alienInvasion)
                        {
                            AskQuestion(__instance, followUpQuestion);
                            answeredQuestions--;
                        }
                        UpdatePoints(__instance, buttonPressed);
                        AskQuestion(__instance, lastQuestion);
                    }
                    else if(alienInvasion)
                    {
                        UpdatePoints(__instance, buttonPressed, followUpQuestion);
                        answeredQuestions--;
                        AskQuestion(__instance);
                        alienInvasion = false;
                    }
                    // Results
                    else if(answeredQuestions==8)
                    {
                        DeterminePokemon(buttonPressed);
                        AskQuestion(__instance, new Question(chosenPokemon, new String[]{"ok"}, null));
                        screenNum++;
                        return SpireReturn.Return(null);
                    }
                    else
                    {
                        UpdatePoints(__instance, buttonPressed);
                        if (alienInvasion) 
                        {
                            AskQuestion(__instance, followUpQuestion);
                            answeredQuestions--;
                        }
                        else AskQuestion(__instance);
                    }
                    answeredQuestions++;
                    return SpireReturn.Return(null);
                // Quit Neow
                case 8:
                    break;
                    
            }
            (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            AbstractDungeon.dungeonMapScreen.open(false);
            PokemonNeowPatch.screenNum = 99;
            return SpireReturn.Return(null);
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
    }
}
