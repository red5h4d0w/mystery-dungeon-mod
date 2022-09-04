package mysteryDungeon.patches;

import java.util.ArrayList;
import java.util.HashMap;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.SeedHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.rooms.VictoryRoom;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.GameOverScreen;
import com.megacrit.cardcrawl.screens.VictoryScreen;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.sendData.CardDetails;
import mysteryDungeon.sendData.RunDetails;
import mysteryDungeon.sendData.SendData;


public class MetricsPatch {
    public static Logger logger = LogManager.getLogger(MetricsPatch.class);

    @SpirePatch(clz=DeathScreen.class, method="<ctor>", paramtypez = {MonsterGroup.class})
    public static class DeathScreenPatch {
        @SpirePostfixPatch
        @SuppressWarnings("all")
        public static void Postfix() {
            if(!(AbstractDungeon.player instanceof Pokemon))
                return;
            if(!MysteryDungeon.sendRunData)
                return;
            RunDetails runDetails = generateRunDetails();
            runDetails.win = AbstractDungeon.getCurrRoom() instanceof VictoryRoom || CardCrawlGame.dungeon instanceof TheEnding;
            logger.info("======= Sending data =======");
            SendData.sendData(runDetails);
            logger.info("======= data sent =======");
        }
    }
    
    @SpirePatch(clz=VictoryScreen.class, method="<ctor>", paramtypez = {MonsterGroup.class})
    public static class VictoryScreenPatch {
        @SpirePostfixPatch
        @SuppressWarnings("all")
        public static void Postfix() {
            if(!(AbstractDungeon.player instanceof Pokemon))
                return;
            if(!MysteryDungeon.sendRunData)
                return;
            RunDetails runDetails = generateRunDetails();
            runDetails.win = true;
            logger.info("======= Sending data =======");
            SendData.sendData(runDetails);
            logger.info("======= data sent =======");
        }
    }

    @SuppressWarnings("all")
    public static RunDetails generateRunDetails() {
        RunDetails runDetails = new RunDetails();
        runDetails.version = "failed";
        for(ModInfo modInfo : Loader.MODINFOS) {
            logger.info(modInfo.ID);
            if(modInfo.ID.toLowerCase().contains("dungeon")) {
                logger.info(modInfo.ModVersion.toString());
                runDetails.version = modInfo.ModVersion.toString();
            }
        }
        runDetails.ascensionLevel = AbstractDungeon.ascensionLevel;
        runDetails.maxFloor = AbstractDungeon.floorNum;
        runDetails.win = true;
        runDetails.elapsedTime = CardCrawlGame.playtime;
        runDetails.score = GameOverScreen.calcScore(runDetails.win);
        runDetails.seed = SeedHelper.getString(Settings.seed);
        if(AbstractDungeon.player instanceof Pokemon) {

            if(((Pokemon)AbstractDungeon.player).adventurer!=null)
                runDetails.pokemon1 = ((Pokemon)AbstractDungeon.player).adventurer.name;
            if(((Pokemon)AbstractDungeon.player).partner!=null)
                runDetails.pokemon2 = ((Pokemon)AbstractDungeon.player).partner.name;
        }
        
        ArrayList<CardDetails> cardDetails = new ArrayList<>();
        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            CardDetails cardDetail = new CardDetails();
            cardDetail.name = card.cardID;
            cardDetail.upgrade = card.timesUpgraded;
            cardDetails.add(cardDetail);
        }
        
        for (HashMap cardChoice : CardCrawlGame.metricData.card_choices) {
            // Handle picked card logic
            CardDetails pickedCardInfo = new CardDetails();
            Object pickedCard = cardChoice.get("picked");
            String cardNameWithUpgrade = (String)pickedCard;
            
            String[] nameAndUpgrade = cardNameWithUpgrade.split("\\+");
            pickedCardInfo.name = nameAndUpgrade[0];
            if(nameAndUpgrade.length==2) {
                pickedCardInfo.upgrade = Integer.parseInt( nameAndUpgrade[1]);
            }
            else {
                pickedCardInfo.upgrade = 0;
            }
            runDetails.chosenCards.add(pickedCardInfo);
            // Handle not picked card logic
            ArrayList<String> notPickedCards = (ArrayList<String>)cardChoice.get("not_picked");
            
            for (String notPickedCard : notPickedCards) {
                CardDetails notPickedCardInfo = new CardDetails();
                String[] nameAndUpgrade2 = notPickedCard.split("\\+");
                notPickedCardInfo.name = nameAndUpgrade2[0];
                if(nameAndUpgrade2.length==2) {
                    notPickedCardInfo.upgrade = Integer.parseInt( nameAndUpgrade2[1]);
                }
                else {
                    notPickedCardInfo.upgrade = 0;
                }
                runDetails.notChosenCards.add(notPickedCardInfo);
            }
        }
        runDetails.cardDetails = cardDetails;
        return runDetails;
    }
}
