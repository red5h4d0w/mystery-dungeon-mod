package mysteryDungeon.cards.Squirtle;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cards.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class SquirtleShellSmash extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(SquirtleShellSmash.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("SquirtleSkill.png");
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
    private static final int BASE_MAGIC_NUMBER = 4;
    private static final int BASE_SECOND_MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 3;


    // /STAT DECLARATION/

    public SquirtleShellSmash() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        baseSecondMagicNumber = BASE_SECOND_MAGIC_NUMBER;
        secondMagicNumber = baseSecondMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        List<AbstractCard> statuses = CardLibrary.getAllCards().stream().filter(card->card.type==CardType.STATUS).collect(Collectors.toList());
        for(int i=0;i<secondMagicNumber;i++)
        {

            Collections.shuffle(statuses, AbstractDungeon.cardRandomRng.random);
            addToBot(new MakeTempCardInDrawPileAction(statuses.get(0), 1, true, false, false));
        }
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
