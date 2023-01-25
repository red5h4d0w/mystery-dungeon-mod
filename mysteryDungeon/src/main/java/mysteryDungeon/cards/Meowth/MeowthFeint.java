package mysteryDungeon.cards.Meowth;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class MeowthFeint extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(MeowthFeint.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("MeowthAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.MEOWTH_WHITE;

    private static final int COST = 0;
    private static final int DAMAGE = 0;
    private static final int UPGRADE_PLUS_DMG = 4;

    // /STAT DECLARATION/

    public MeowthFeint() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RemoveAllBlockAction(m, p));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        if (!upgraded) {
            rawDescription = cardStrings.DESCRIPTION;
          } else {
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
          } 
          initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster m) {
        baseDamage+=m.currentBlock;
        super.calculateCardDamage(m);
        baseDamage-=m.currentBlock;
    }

    public void applyPowers(AbstractMonster m) {
        baseDamage = m.currentBlock;
        if (upgraded)
          baseDamage += 4; 
        super.applyPowers();
        if (!upgraded) {
          rawDescription = cardStrings.DESCRIPTION;
        } else {
          rawDescription = cardStrings.UPGRADE_DESCRIPTION;
        } 
        rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
        initializeDescription();
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
