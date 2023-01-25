package mysteryDungeon.cards.Charmander;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.powers.BlazePower;

public class CharmanderBlaze extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(CharmanderBlaze.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(CharmanderBlaze.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pokemon.Enums.CHARMANDER_RED;

    private static final int COST = 0;
    private static final int BASE_MAGIC_NUMBER = 1;
    private static final int UPGRADE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public CharmanderBlaze() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, 2, false), 2));
        addToBot(new ApplyPowerAction(p, p, new BlazePower(p, magicNumber), magicNumber));
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
