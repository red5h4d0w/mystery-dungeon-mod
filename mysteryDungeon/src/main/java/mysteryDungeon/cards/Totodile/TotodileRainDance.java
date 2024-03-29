package mysteryDungeon.cards.Totodile;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.interfaces.onCardScriedInterface;

public class TotodileRainDance extends PokemonCard implements onCardScriedInterface {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(TotodileRainDance.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(TotodileRainDance.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.TOTODILE_BLUE;

    private static final int COST = -2;

    private static final int BASE_MAGIC_NUMBER = 5;

    // /STAT DECLARATION/

    public TotodileRainDance() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnManualDiscard() {
        super.triggerOnManualDiscard();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                new VigorPower(AbstractDungeon.player, magicNumber)));
    }

    @Override
    public void onCardScried(AbstractCard card) {
        if (card == this) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new VigorPower(AbstractDungeon.player, magicNumber)));
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            scoopUp = true;
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
