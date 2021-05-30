package mysteryDungeon.cards.Bulbasaur;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;


import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.characters.Pokemon;

public class BulbasaurGrowth extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 7 (*) damage times the energy you currently have.
     */

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(BulbasaurGrowth.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("BulbasaurPower.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pokemon.Enums.BULBASAUR_GREEN;

    private static final int COST = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public BulbasaurGrowth() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 3;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
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
