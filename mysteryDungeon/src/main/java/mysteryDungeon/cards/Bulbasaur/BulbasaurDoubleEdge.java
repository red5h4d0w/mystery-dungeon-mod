package mysteryDungeon.cards.Bulbasaur;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.characters.Pokemon;

public class BulbasaurDoubleEdge extends CustomCard {

    

    public static final String ID = MysteryDungeon.makeID(BulbasaurDoubleEdge.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("BulbasaurAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.COLOR_GRAY;

    private static final int COST = 3;
    private static final int BASE_DAMAGE = 15;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public BulbasaurDoubleEdge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        AbstractPower strength = p.getPower("Strength");
        int bonusDamage = 0;
        int recoil = 0;
        if(strength!=null)
        {
            bonusDamage = magicNumber * (strength.amount>0?strength.amount:0);
            recoil = strength.amount>0?(int)Math.ceil((float)strength.amount/2.0f):0;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage + bonusDamage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new DamageAction(p, new DamageInfo(p, recoil, DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
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
