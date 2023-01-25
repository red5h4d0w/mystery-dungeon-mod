package mysteryDungeon.cards.Totodile;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.powers.NextTurnVigorPower;

public class TotodileRage extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(TotodileRage.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("TotodileSkill");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.TOTODILE_BLUE;

    private static final int COST = -1;
    private static final int BASE_MAGIC_NUMBER = 7;

    // /STAT DECLARATION/

    public TotodileRage() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        exhaust = true;
    }

   // Actions the card should do.
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount; 
        }
        if (p.hasRelic("Chemical X")) {
            energyOnUse += 2;
            p.getRelic("Chemical X").flash();
        }
        if(upgraded)
            energyOnUse++; 
        if(energyOnUse>0)
            addToBot(new ApplyPowerAction(p, p, new NextTurnVigorPower(p, magicNumber, energyOnUse), magicNumber));
        if (!freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);
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
