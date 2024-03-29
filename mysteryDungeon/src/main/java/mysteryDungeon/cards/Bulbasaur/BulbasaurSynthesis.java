package mysteryDungeon.cards.Bulbasaur;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;


import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class BulbasaurSynthesis extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(BulbasaurSynthesis.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(BulbasaurSynthesis.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    private static final int COST = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 2;

    // /STAT DECLARATION/

    public BulbasaurSynthesis() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 10;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        addToBot(new HealAction(p, p, magicNumber));
        addToBot(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, 2), 2));
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, 2), 2));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
