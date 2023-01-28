package mysteryDungeon.cards.Squirtle;
import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.cards.tempCards.SquirtleFlinch;
import mysteryDungeon.characters.Pokemon;

public class SquirtleBodyPress extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(SquirtleBodyPress.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(SquirtleBodyPress.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.SQUIRTLE_BLUE;

    private static final int COST = 3;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private static final int BASE_BLOCK = 12;



    // /STAT DECLARATION/

    public SquirtleBodyPress() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        baseBlock = BASE_BLOCK;
        cardsToPreview = new SquirtleFlinch();
        if(upgraded)
        {
            AbstractCard upgradedFlinch = new SquirtleFlinch();
            upgradedFlinch.upgrade();
            cardsToPreview = upgradedFlinch;
        }
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        AbstractCard c = new SquirtleFlinch();
        c.setCostForTurn(0);
        if(!upgraded)
            addToBot(new MakeTempCardInHandAction(c, magicNumber, false));
        else
        {
            AbstractCard upgradedFlinch = new SquirtleFlinch();
            upgradedFlinch.upgrade();
            upgradedFlinch.setCostForTurn(0);
            addToBot(new MakeTempCardInHandAction(upgradedFlinch, magicNumber, false));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            AbstractCard upgradedFlinch = new SquirtleFlinch();
            upgradedFlinch.upgrade();
            cardsToPreview = upgradedFlinch;
            initializeDescription();
        }
    }
}
