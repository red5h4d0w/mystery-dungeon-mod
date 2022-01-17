package mysteryDungeon.cards.Charmander;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.MoveRandomCardsAction;
import mysteryDungeon.characters.Pokemon;

public class CharmanderDragonPulse extends PokemonCard {

    public Logger log = LogManager.getLogger(CharmanderDragonPulse.class);
    public static final String ID = MysteryDungeon.makeID(CharmanderDragonPulse.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(CharmanderDragonPulse.class.getSimpleName()+".png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.CHARMANDER_RED;

    private static final int COST = -2;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private int timesActivatedThisCombat = 0;

    // /STAT DECLARATION/

    public CharmanderDragonPulse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (timesActivatedThisCombat<magicNumber){
        addToBot(new MoveRandomCardsAction(p.drawPile, p.exhaustPile, 1));
        timesActivatedThisCombat++;
        }
        if(timesActivatedThisCombat>=magicNumber){
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerWhenDrawn() {
        AbstractCard card = null;
        if(AbstractDungeon.player.exhaustPile.group.size()>0)
            card = AbstractDungeon.player.exhaustPile.getRandomCard(AbstractDungeon.cardRng);
        if(card!=null)
        {
            if (timesActivatedThisCombat<magicNumber){
                addToBot(new MoveRandomCardsAction(AbstractDungeon.player.drawPile, AbstractDungeon.player.exhaustPile, 1));
                timesActivatedThisCombat++;
            }
        }
        if(timesActivatedThisCombat>=magicNumber){
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
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
