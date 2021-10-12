package mysteryDungeon.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
        public static void Postfix() {
            if(!MysteryDungeon.sendRunData)
                return;
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
            runDetails.win = AbstractDungeon.getCurrRoom() instanceof VictoryRoom;
            runDetails.elapsedTime = CardCrawlGame.playtime;
            runDetails.score = GameOverScreen.calcScore(runDetails.win);
            runDetails.seed = SeedHelper.getString(Settings.seed);
            runDetails.pokemon1 = Pokemon.adventurer.name;
            runDetails.pokemon2 = Pokemon.partner.name;
            ArrayList<CardDetails> cardDetails = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                CardDetails cardDetail = new CardDetails();
                cardDetail.name = card.cardID;
                cardDetail.upgrade = card.timesUpgraded;
                cardDetails.add(cardDetail);
            }
            runDetails.cardDetails = cardDetails;
            logger.info("======= Sending data =======");
            SendData.sendData(runDetails);
            logger.info("======= data sent =======");
        }
    }
    
    @SpirePatch(clz=VictoryScreen.class, method="<ctor>", paramtypez = {MonsterGroup.class})
    public static class VictoryScreenPatch {
        @SpirePostfixPatch
        public static void Postfix() {
            if(!MysteryDungeon.sendRunData)
                return;
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
            runDetails.pokemon1 = Pokemon.adventurer.name;
            runDetails.pokemon2 = Pokemon.partner.name;
            ArrayList<CardDetails> cardDetails = new ArrayList<>();
            for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                CardDetails cardDetail = new CardDetails();
                cardDetail.name = card.cardID;
                cardDetail.upgrade = card.timesUpgraded;
                cardDetails.add(cardDetail);
            }
            runDetails.cardDetails = cardDetails;
            logger.info("======= Sending data =======");
            SendData.sendData(runDetails);
            logger.info("======= data sent =======");
        }
    }
}
