package mysteryDungeon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.actions.TriggerFireSpinAction;
import mysteryDungeon.powers.FireSpinPower;

public class TorchicComboManager {

    public static Logger logger = LogManager.getFormatterLogger(TorchicComboManager.class);

    // TODO: list all possible moves
    public static enum Move {
        ATTACK,
        SKILL,
        POWER,
        STATUS,
        EXHAUST,
        RETAIN
    }

    // TODO: add all possible convertors
    public static ArrayList<Move> movesFromCard(AbstractCard card) {

        ArrayList<Move> moves = new ArrayList<Move>();

        // Based on type
        if (card.type == CardType.ATTACK) {
            moves.add(Move.ATTACK);
        } else if (card.type == CardType.SKILL) {
            moves.add(Move.SKILL);
        } else if (card.type == CardType.POWER) {
            moves.add(Move.ATTACK);
        } else if (card.type == CardType.STATUS) {
            moves.add(Move.ATTACK);
        }

        // Based on keywords
        if (card.exhaust) {
            moves.add(Move.EXHAUST);
        }
        if (card.selfRetain) {
            moves.add(Move.RETAIN);
        }

        return moves;
    }

    public static class Combo {
        public ArrayList<Move> moveList;
        public AbstractGameAction finisher;

        public Combo(ArrayList<Move> moveList, AbstractGameAction finisher) {
            this.moveList = moveList;
            this.finisher = finisher;
        }

        public Combo(Move[] moveList, AbstractGameAction finisher) {
            this.moveList = new ArrayList<Move>(Arrays.asList(moveList));
            this.finisher = finisher;
        }

        public Combo(AbstractGameAction finisher, Move... moveList) {
            this.moveList = new ArrayList<Move>(Arrays.asList(moveList));
            this.finisher = finisher;
        }

        public String toString() {
            return moveList.stream().map(move -> move.name().charAt(0)).collect(Collector.of(
                    StringBuilder::new,
                    StringBuilder::append,
                    StringBuilder::append,
                    StringBuilder::toString));
        }
    }

    public static class ComboWithState extends Combo {
        public int state = 0;

        public ComboWithState(ArrayList<Move> moveList, AbstractGameAction finisher) {
            super(moveList, finisher);
        }

        public ComboWithState(Combo combo) {
            super(combo.moveList, combo.finisher);
        }

        public boolean canReceiveMove(Move move) {
            return this.moveList.get(state) == move;
        }

        @Override
        public String toString() {
            String comboString = super.toString();
            return new StringBuilder(comboString).insert(state, "|").toString();
        }
    }

    private ArrayList<Combo> comboDeck = new ArrayList<Combo>();

    private ArrayList<Combo> comboDiscard = new ArrayList<Combo>();

    private ComboWithState currentCombo;

    public TorchicComboManager() {
        logger.info("Weclome to the combo Manager console edition");
        // TODO: modify default combo to comboDeck
        comboDeck.add(new Combo(new ArrayList<Move>(Arrays.asList(Move.SKILL, Move.ATTACK, Move.ATTACK)),
                new SimpleAction(() -> {
                    if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                        for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                            if (!monster.isDead && !monster.isDying) {
                                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monster, AbstractDungeon.player, new FireSpinPower(monster, 4), 4));
                                AbstractDungeon.actionManager.addToTop(new TriggerFireSpinAction());
                } 
            } 
        }
                })));
        drawNewCombo();
    }

    public void addCombo(Combo combo) {
        comboDeck.add(combo);
        logger.info(String.format("-> Added new Combo (%s)", combo.toString()));
    }

    public void drawNewCombo() {
        // Shuffle deck if necessary
        if (comboDeck.size() == 0) {
            for (Combo combo : comboDiscard) {
                comboDeck.add(combo);
            }
            comboDiscard.clear();
        }
        // Choose next combo and discard it
        Combo nextCombo = comboDeck.get(AbstractDungeon.cardRng.random(comboDeck.size() - 1));
        currentCombo = new ComboWithState(nextCombo);
        comboDeck.remove(nextCombo);
        comboDiscard.add(nextCombo);
        currentCombo.finisher.isDone = false;
        logger.info(String.format("New Combo is (%s)", currentCombo.toString()));
    }

    public void feed(AbstractCard card) {
        // If it finds a match, it increments the combo
        if (movesFromCard(card).stream().anyMatch(move -> currentCombo.canReceiveMove(move))) {
            currentCombo.state++;
            logger.info(String.format("Combo is now %s", currentCombo.toString()));
            if (currentCombo.state == currentCombo.moveList.size()) {
                AbstractDungeon.actionManager.addToBottom(currentCombo.finisher);
                AbstractDungeon.actionManager.addToBottom(new SimpleAction(() -> {
                    drawNewCombo();
                }));
            }
        } else {
            logger.info("Combo Broken...");
            AbstractDungeon.actionManager.addToBottom(new SimpleAction(() -> {
                drawNewCombo();
            }));
        }
    }

}
