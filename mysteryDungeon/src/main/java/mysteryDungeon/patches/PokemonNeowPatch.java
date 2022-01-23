package mysteryDungeon.patches;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.InfiniteSpeechBubble;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.cards.fakeCards.ExplorersDeck;
import mysteryDungeon.cards.fakeCards.PartnersDeck;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.pokemons.*;
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

    public static AbstractPokemon chosenPokemon;

    public static ArrayList<AbstractPokemon> possiblePartners;

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
            if(!isDone) {
                Pokemon.adventurer = null;
                Pokemon.partner = null;
                Pokemon.maxPikachuChargeCounter = 2;
                answeredQuestions = 0;
                screenNum = 0;
                InitializeTraitsScore();
                InitializeQuestions();
            }
            else {
                screenNum = 99;
            }
        }
    }

    @SpirePatch(clz = NeowEvent.class, method = "buttonEffect")
    public static class NeowEventFlow {
        @SpirePrefixPatch
        @SuppressWarnings("all")
        public static SpireReturn AdvanceAccordingToChoice(NeowEvent __instance, int buttonPressed) {
            if(!(AbstractDungeon.player instanceof Pokemon)) {
                return SpireReturn.Continue();
            }
            switch(screenNum) {
                case 0:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 1:
                    ShowNextDialog(__instance);
                    return SpireReturn.Return(null);
                case 2:
                    isTestRun = false;
                    AskQuestion(__instance, new Question(TEXT[screenNum], new String[]{TEXT[screenNum+2], TEXT[screenNum+3], TEXT[screenNum+4]}));
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
                            ((Pokemon)AbstractDungeon.player).adventurer = implementedPokemons.get(AbstractDungeon.eventRng.random(implementedPokemons.size()-1));
                            ((Pokemon)AbstractDungeon.player).DefineNature(NatureOfPokemon.get(((Pokemon)AbstractDungeon.player).adventurer.getClass().getSimpleName())[AbstractDungeon.eventRng.random(1)]);
                            ArrayList<AbstractPokemon> partnerChoices = ((ArrayList<AbstractPokemon>)implementedPokemons.clone());
                            partnerChoices.remove(((Pokemon)AbstractDungeon.player).adventurer);
                            ((Pokemon)AbstractDungeon.player).partner = partnerChoices.get(AbstractDungeon.eventRng.random(partnerChoices.size()-1));
                            CardCrawlGame.dungeon.initializeCardPools();
                            AbstractDungeon.player.masterDeck.removeCard(ExplorersDeck.ID);
                            AbstractDungeon.player.masterDeck.removeCard(PartnersDeck.ID);
                            ((Pokemon)AbstractDungeon.player).awardThingsToAward();
                            screenNum = 99;
                            return SpireReturn.Continue();
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
                        ((Pokemon)AbstractDungeon.player).adventurer = chosenPokemon;
                        possiblePartners = partnerChoices();
                        AskQuestion(__instance, new Question(chosenPokemon.name, possiblePartners.stream().map(p -> p.name).toArray(size-> new String[size]), null));
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
                        ((Pokemon)AbstractDungeon.player).partner = possiblePartners.get(buttonPressed);
                    if(isTestRun)
                    {
                        ((Pokemon)AbstractDungeon.player).partner = implementedPokemons.stream().filter(p -> p != ((Pokemon)AbstractDungeon.player).adventurer).toArray(size-> new AbstractPokemon[size])[buttonPressed];
                    }
                    //AskQuestion(__instance, new Question("I see, now brave the challenge of the tower", new String[]{"[Leave]"}));
                    //screenNum++;
                    CardCrawlGame.dungeon.initializeCardPools();
                    AbstractDungeon.player.masterDeck.removeCard(ExplorersDeck.ID);
                    AbstractDungeon.player.masterDeck.removeCard(PartnersDeck.ID);
                    ((Pokemon)AbstractDungeon.player).awardThingsToAward();
                    screenNum = 99;
                    return SpireReturn.Continue();
                case 6:
                    break;
                // Test run choose option
                case 10:
                    AskQuestion(__instance, new Question(TEXT[7], implementedPokemons.stream().map(p -> p.name).toArray(size-> new String[size])));
                    screenNum++;
                    return SpireReturn.Return(null);
                case 11:
                    ((Pokemon)AbstractDungeon.player).adventurer = implementedPokemons.get(buttonPressed);
                    String[] possibleNatures = NatureOfPokemon.get(((Pokemon)AbstractDungeon.player).adventurer.getClass().getSimpleName());
                    ((Pokemon)AbstractDungeon.player).DefineNature(possibleNatures[AbstractDungeon.eventRng.random(possibleNatures.length-1)]);
                    AskQuestion(__instance, new Question(TEXT[8], implementedPokemons.stream().filter(p -> p != ((Pokemon)AbstractDungeon.player).adventurer).map(p -> p.name).toArray(size-> new String[size])));
                    screenNum=5;
                    return SpireReturn.Return(null);
                case 99:
                    return SpireReturn.Continue();
                    
            }
            // (AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMPLETE;
            // AbstractDungeon.dungeonMapScreen.open(false);
            PokemonNeowPatch.screenNum = 99;
            return SpireReturn.Return(null);
        }
    }

    public static ArrayList<AbstractPokemon> partnerChoices()
    {
        ArrayList<AbstractPokemon> partners = new ArrayList<AbstractPokemon>()
        {{
            add(new Bulbasaur());
            add(new Charmander());
            add(new Squirtle());
            add(new Pikachu());
            //TODO: uncomment next lines when those pokémons while be added
            //add(new Chikorita());
            //add(new Cyndaquil());
            //add(new Totodile());
            //add(new Treecko());
            //add(new Torchic());
            //add(new Mudkip());
        }};
        ArrayList<AbstractPokemon> availablePartners = partners.stream().filter(p->p.color!=chosenPokemon.color).collect(Collectors.toCollection(ArrayList::new));
        for (int i=availablePartners.size(); i>1; i--)
            Collections.swap(availablePartners, i-1, AbstractDungeon.eventRng.random(i-1));
        ArrayList<AbstractPokemon> selectablePartners = new ArrayList<AbstractPokemon>();
        selectablePartners.add(availablePartners.get(0));
        for(AbstractPokemon partner : availablePartners)
        {
            boolean toAdd = false;
            for(AbstractPokemon sPartner : selectablePartners)
            {
                if(partner.color != sPartner.color)
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
        return selectablePartners;
    }


    public static void convertPokemonToImplementedPokemon() {
        // If the Pokémon determined by the quiz is implemented, do nothing
        if(implementedPokemons.stream().anyMatch(p -> p.cardColor == chosenPokemon.cardColor)) {
            return;
        }
        // Else look at the Pokémon's color and choose from existing pokémons
        if(chosenPokemon.color == Color.GREEN.cpy())
        {
            chosenPokemon = new Bulbasaur();
        }
        else if(chosenPokemon.color == Color.RED.cpy())
        {
            chosenPokemon = new Charmander();
        }
        else if(chosenPokemon.color == Color.BLUE.cpy())
        {
            chosenPokemon = new Squirtle();
        }
        else
        {
            chosenPokemon = new Pikachu();
        }
    }

    // Tables for pokémon choices

    public static Map<String, AbstractPokemon> malePokemonChoices = new HashMap<String, AbstractPokemon>()
    {{
        put("Docile", new Bulbasaur());
        put("Hardy", new Charmander());
        put("Jolly", new Squirtle());
        put("Impish", new Pikachu());
        put("Quirky", new Meowth());
        put("Relaxed", new Psyduck());
        put("Brave", new Machop());
        put("Lonely", new Cubone());
        put("Timid", new Cyndaquil());
        put("Naive", new Totodile());
        put("Sassy", new Treecko());
        put("Hasty", new Torchic());
        put("Calm", new Mudkip());
    }};

    public static Map<String, AbstractPokemon> femalePokemonChoices = new HashMap<String, AbstractPokemon>()
    {{
        put("Calm", new Bulbasaur());
        put("Brave", new Charmander());
        put("Relaxed", new Squirtle());
        put("Hardy", new Pikachu());
        put("Lonely", new Psyduck());
        put("Impish", new Cubone());
        put("Naive", new Eevee());
        put("Docile", new Chikorita());
        put("Jolly", new Totodile());
        put("Quirky", new Treecko());
        put("Sassy", new Torchic());
        put("Timid", new Mudkip());
        put("Hasty", new Skitty());
    }};

    public static Map<String,String[]> NatureOfPokemon = new HashMap<String,String[]>()
    {{
        put("Bulbasaur", new String[]{"Calm", "Docile"});
        put("Charmander", new String[]{"Hardy", "Brave"});
        put("Squirtle", new String[]{"Jolly", "Relaxed"});
        put("Pikachu", new String[]{"Impish", "Hardy"});
        put("Meowth", new String[]{"Quirky"});
        put("Chikorita", new String[]{"Docile"});
    }};

    public static ArrayList<AbstractPokemon> implementedPokemons = new ArrayList<AbstractPokemon>(){{
        add(new Bulbasaur());
        add(new Charmander());
        add(new Squirtle());
        add(new Pikachu());
        // add(new Meowth());
        // add(new Psyduck());
        // add(new Machop());
        // add(new Cubone());
        // add(new Eevee());
        add(new Chikorita());
        // add(new Cyndaquil());
        // add(new Totodile());
        // add(new Treecko());
        // add(new Torchic());
        // add(new Mudkip());
        // add(new Skitty());
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
        public Question(String QUESTION, String[] ANSWERS)
        {
            this.QUESTION = QUESTION;
            this.ANSWERS = ANSWERS;
            this.POINTS = null;
        }
    }
}
