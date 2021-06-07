package mysteryDungeon.cards.Charmander;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cards.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class CharmanderDragonPulse extends PokemonCard {

    public Logger log = LogManager.getLogger(CharmanderDragonPulse.class);
    public static final String ID = MysteryDungeon.makeID(CharmanderDragonPulse.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("CharmanderSkill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.CHARMANDER_RED;

    private static final int COST = -2;
    private static final int BASE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public CharmanderDragonPulse() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard card = null;
        if(AbstractDungeon.player.exhaustPile.group.size()>0)
            card = AbstractDungeon.player.exhaustPile.getRandomCard(AbstractDungeon.cardRng);
        if(card!=null)
        {
            AbstractCard cardToAdd = card.makeStatEquivalentCopy();
            AbstractDungeon.player.exhaustPile.removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(cardToAdd, 1, false, false, false));
        }
        if(upgraded)
        {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber), magicNumber));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerWhenDrawn() {
        AbstractCard card = null;
        if(AbstractDungeon.player.exhaustPile.group.size()>0)
            card = AbstractDungeon.player.exhaustPile.getRandomCard(AbstractDungeon.cardRng);
        if(card!=null)
        {
            AbstractCard cardToAdd = card.makeCopy();
            AbstractDungeon.player.exhaustPile.removeCard(card);
            addToBot(new MakeTempCardInDrawPileAction(cardToAdd, 1, false, false, false));
        }
        if(upgraded)
        {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, magicNumber), magicNumber));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
