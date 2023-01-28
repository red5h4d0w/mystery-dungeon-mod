package mysteryDungeon.cards.Squirtle;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class SquirtleDive extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(SquirtleDive.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(SquirtleDive.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.SQUIRTLE_BLUE;

    private static final int COST = 3;
    private static final int BASE_MAGIC_NUMBER = 4;
    private static final int UPGRADE_MAGIC_NUMBER = 2;


    // /STAT DECLARATION/

    public SquirtleDive() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        cardsToPreview = new SquirtleHaze();
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> drawPileCardsToRemove = new ArrayList<AbstractCard>();
        for(AbstractCard c : p.drawPile.group)
        {
            if(c.type == CardType.STATUS)
            {
                drawPileCardsToRemove.add(c);
            }
        }
        for(AbstractCard c : drawPileCardsToRemove)
        {
            p.drawPile.moveToExhaustPile(c);
        }
        ArrayList<AbstractCard> discardPileCardsToRemove = new ArrayList<AbstractCard>();
        for(AbstractCard c : p.discardPile.group)
        {
            if(c.type == CardType.STATUS)
            {
                discardPileCardsToRemove.add(c);
            }
        }
        for(AbstractCard c : discardPileCardsToRemove)
        {
            p.discardPile.moveToExhaustPile(c);
        }
        ArrayList<AbstractCard> handCardsToRemove = new ArrayList<AbstractCard>();
        for(AbstractCard c : p.hand.group)
        {
            if(c.type == CardType.STATUS)
            {
                handCardsToRemove.add(c);
            }
        }
        for(AbstractCard c : handCardsToRemove)
        {
            p.hand.moveToExhaustPile(c);
        }
        for(int i=0;i<magicNumber;i++)
        {
            switch(AbstractDungeon.cardRandomRng.random(1))
            {
                case(0):
                    p.drawPile.addToRandomSpot(new SquirtleHaze());
                    break;
                case(1):
                    p.discardPile.addToRandomSpot(new SquirtleHaze());
                    break;
            }
            
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
