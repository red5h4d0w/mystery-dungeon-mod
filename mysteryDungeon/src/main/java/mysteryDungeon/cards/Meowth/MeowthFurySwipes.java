package mysteryDungeon.cards.Meowth;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.actions.SpendGoldAction;
import mysteryDungeon.characters.Pokemon;

public class MeowthFurySwipes extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(MeowthFurySwipes.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("MeowthAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.MEOWTH_WHITE;

    private static final int COST = 2;
    private static final int DAMAGE = 10;
    private static final int BASE_MAGIC_NUMBER = 20;
    private static final int UPGRADE_MAGIC_NUMBER = -5;

    private static final int BASE_SECOND_MAGIC_NUMBER = 5;

    // /STAT DECLARATION/

    public MeowthFurySwipes() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        misc = DAMAGE;
        baseDamage = misc;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        baseSecondMagicNumber = BASE_SECOND_MAGIC_NUMBER;
        secondMagicNumber = baseSecondMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        if(canSpend(magicNumber)) {
            addToBot(new SpendGoldAction(magicNumber));
            addToBot(new SimpleAction(() -> {
                for (AbstractCard c : p.masterDeck.group) {
                    if (!c.uuid.equals(uuid))
                      continue; 
                    c.misc += secondMagicNumber;
                    c.applyPowers();
                    c.baseDamage = c.misc;
                    c.isDamageModified = false;
                  } 
                  for (AbstractCard c : GetAllInBattleInstances.get(uuid)) {
                    c.misc += secondMagicNumber;
                    c.applyPowers();
                    c.baseDamage = c.misc;
                  }
            }));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return super.canUse(p, m) && canSpend(magicNumber);
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
