package mysteryDungeon.cards.Pikachu;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.cards.tempCards.ChooseNegative;
import mysteryDungeon.cards.tempCards.ChoosePositive;
import mysteryDungeon.characters.Pokemon;

public class PikachuVoltSwitch extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(PikachuVoltSwitch.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(PikachuVoltSwitch.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    

    private static final int COST = 1;


    // /STAT DECLARATION/

    public PikachuVoltSwitch() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        selfRetain = true;
        inert = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
            InputHelper.moveCursorToNeutralPosition();
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new ChooseNegative());
            stanceChoices.add(new ChoosePositive());
            addToBot(new ChooseOneAction(stanceChoices));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            exhaust = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
