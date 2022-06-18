package mysteryDungeon.cards.Chikorita;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;


import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.abstracts.PokemonTwoAmountPower;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.powers.RecoverPower;

public class ChikoritaSynthesis extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(ChikoritaSynthesis.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("ChikoritaSkill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.CHIKORITA_GREEN;

    private static final int COST = -2;
    private int timesActivatedThisCombat = 0;
    private static final int  BASE_SECOND_MAGIC_NUMBER = 2;
    private static final int  UPGRADE_SECOND_MAGIC_NUMBER = 1;

    // /STAT DECLARATION/

    public ChikoritaSynthesis() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = 7;
        magicNumber = baseMagicNumber;
        baseSecondMagicNumber = BASE_SECOND_MAGIC_NUMBER;
        secondMagicNumber = baseSecondMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (timesActivatedThisCombat<secondMagicNumber){
            if(p.hasPower(RecoverPower.POWER_ID)) {
                addToBot(new SimpleAction(() -> {
                    ((PokemonTwoAmountPower)p.getPower(RecoverPower.POWER_ID)).amount2 += magicNumber;
                    p.getPower(RecoverPower.POWER_ID).updateDescription();
                }));
                timesActivatedThisCombat++;
            }
        }
        if(timesActivatedThisCombat>=secondMagicNumber){
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    public void triggerWhenDrawn() {
        {
            if (timesActivatedThisCombat<secondMagicNumber){
                addToBot(new HealAction(AbstractDungeon.player, AbstractDungeon.player, magicNumber));
                timesActivatedThisCombat++;
            }
        }
        if(timesActivatedThisCombat>=secondMagicNumber){
            addToBot(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand, true));
            rawDescription = cardStrings.EXTENDED_DESCRIPTION[1];
            initializeDescription();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_SECOND_MAGIC_NUMBER);
            initializeDescription();
        }
    }
}
