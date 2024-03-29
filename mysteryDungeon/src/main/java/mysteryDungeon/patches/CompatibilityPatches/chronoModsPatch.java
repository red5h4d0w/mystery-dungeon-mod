package mysteryDungeon.patches.CompatibilityPatches;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.neow.NeowEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chronoMods.coop.CoopNeowEvent;
import chronoMods.coop.CoopNeowReward;
import chronoMods.network.NetworkHelper;
import chronoMods.network.RemotePlayer;
import chronoMods.utilities.AntiConsolePrintingPatches;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.patches.PokemonNeowPatch;

public class chronoModsPatch {
    public static Logger logger = LogManager.getLogger(chronoModsPatch.class);

    public static boolean ready = false;

    @SpirePatch(clz = CoopNeowEvent.ControlNeowEvent.class, method = "blessing", requiredModId = "chronoMods")
    public static class SkipBlessingPatch {

        @SpirePrefixPatch
        public static SpireReturn<Void> removeBlessing(NeowEvent __instance) {
            if(AbstractDungeon.player instanceof Pokemon && !ready)
            {
                PokemonNeowPatch.needsCoopBleesing = true;
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }


    @SpirePatch(clz = CoopNeowEvent.ControlNeowEvent.class, method = "Prefix", requiredModId = "chronoMods")
    public static class ChronoNeowPatch {

        @SpireInsertPatch(rloc = 183-180)
        public static SpireReturn<SpireReturn<Void>> bringToPokemonsNeow(NeowEvent __instance, int buttonPressed) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return(SpireReturn.Continue());
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = CoopNeowEvent.NeowUpdateAdditions.class, method = "Postfix", requiredModId = "chronoMods")
    public static class DoNotUpdateAdditions {

        @SpirePrefixPatch
        public static SpireReturn<Void> skip(NeowEvent __instance) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }

    }

    @SpirePatch(clz = CoopNeowEvent.NeowLinkRender.class, method = "Postfix", requiredModId = "chronoMods")
    public static class removeLinkIcon {

        @SpirePrefixPatch()
        public static SpireReturn<Void> removeIfStillChoosingPokemon(RoomEventDialog __instance, SpriteBatch sb) {
            if(!(AbstractDungeon.player instanceof Pokemon))
            {
                return SpireReturn.Continue();
            }
            if (PokemonNeowPatch.screenNum != 99) {
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = NetworkHelper.class, method = "parseData", requiredModId = "chronoMods")
    public static class AddPokemonCards {
        @SpireInsertPatch(rloc = 882 - 127, localvars = {"anyCard"})
        public static void addCards(ByteBuffer data, RemotePlayer playerInfo, CardGroup anyCard) {
            if(playerInfo.character instanceof Pokemon) {
                AbstractCard[] pokemonCards = CardLibrary.cards.values().stream()
                    .filter(c -> c instanceof PokemonCard)
                    .filter(c -> c.type != CardType.STATUS && c.type != CardType.CURSE)
                    .filter(c -> c.color != CardColor.COLORLESS)
                    .toArray(AbstractCard[]::new);
                for(AbstractCard card : pokemonCards) {
                    anyCard.addToBottom(card);
                }
            }
        }
    }

    @SpirePatch(clz = CoopNeowReward.class, method = "getOtherCardPool", requiredModId = "chronoMods")
    public static class ChangeLinkEffect {
        @SpireInsertPatch(rloc = 975-972, localvars = "pool")
        public static void updatePool(CoopNeowReward __instance, RemotePlayer otherPlayer, ArrayList<AbstractCard> pool) {
            logger.info(pool.size());
            if (pool.size() == 0) {
                logger.info("alloha");
                if(otherPlayer.character instanceof Pokemon) {
                    Pokemon otherPokemon = (Pokemon)otherPlayer.character;
                    logger.info(otherPokemon.partner.name);
                    pool = CardLibrary.cards.values().stream()
                        .filter(c -> c instanceof PokemonCard)
                        .filter(c -> c.type != CardType.STATUS && c.type != CardType.CURSE)
                        .filter(c -> c.color == otherPokemon.adventurer.cardColor || c.color == otherPokemon.partner.cardColor)
                        .collect(Collectors.toCollection(ArrayList::new));
                }
            }
        }

    }
    
    @SpirePatch(clz = AntiConsolePrintingPatches.RemoveLogging.class, method = "patch", requiredModId = "chronoMods")
    public static class AntiAntiConsolePrintingPatch {
        @SpirePrefixPatch
        public static SpireReturn<Logger> returnRealLogger(Logger __result, String s) {
            return MysteryDungeon.ENFORCE_LOGS ? SpireReturn.Return(__result) : SpireReturn.Continue();
        }
    }
}
