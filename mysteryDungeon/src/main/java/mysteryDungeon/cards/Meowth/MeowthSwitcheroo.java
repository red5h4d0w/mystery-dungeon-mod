package mysteryDungeon.cards.Meowth;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.characters.Pokemon;

public class MeowthSwitcheroo extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(MeowthSwitcheroo.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("MeowthSkill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.MEOWTH_WHITE;

    private static final int COST = 1;
    private static final int BASE_MAGIC_NUMBER = 0;
    private static final int UPGRADE_MAGIC_NUMBER = 4;


    // /STAT DECLARATION/

    public MeowthSwitcheroo() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded)
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, -4), -4));
        addToBot(new SimpleAction(
            () -> {
                int pStrength = 0;
                int mStrength = 0;
                if(p.hasPower(StrengthPower.POWER_ID)) {
                    pStrength = p.getPower(StrengthPower.POWER_ID).amount;
                }
                if(m.hasPower(StrengthPower.POWER_ID)) {
                    mStrength = m.getPower(StrengthPower.POWER_ID).amount;
                }
                if(mStrength!=pStrength) {
                    addToTop(new ApplyPowerAction(p, m, new StrengthPower(p, mStrength-pStrength), mStrength-pStrength));
                    addToTop(new ApplyPowerAction(m, p, new StrengthPower(m, pStrength-mStrength), pStrength-mStrength));
                }
            }
        ));

    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
