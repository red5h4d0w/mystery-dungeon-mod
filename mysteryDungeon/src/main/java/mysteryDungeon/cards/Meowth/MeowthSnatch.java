package mysteryDungeon.cards.Meowth;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.cards.tempCards.MeowthAttract;
import mysteryDungeon.characters.Pokemon;

public class MeowthSnatch extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(MeowthSnatch.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("MeowthAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.MEOWTH_WHITE;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BASE_MAGIC_NUMBER = 5;
    private boolean noHover = false;

    // /STAT DECLARATION/

    public MeowthSnatch() {
        this(false);
    }

    public MeowthSnatch(boolean noHover) {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        exhaust = true;
        this.noHover = noHover;
        if(!this.noHover) {
            cardsToPreview = (AbstractCard)new MeowthAttract(true);
            if(upgraded) {
                AbstractCard upgradedAttract = new MeowthAttract(true);
                upgradedAttract.upgrade();
                cardsToPreview = upgradedAttract;
            }
        } 
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainGoldAction(magicNumber));
        if(!upgraded)
            addToBot(new MakeTempCardInHandAction(new MeowthAttract(), 1, false));
        else {
            AbstractCard upgradedAttract = new MeowthAttract();
            upgradedAttract.upgrade();
            addToBot(new MakeTempCardInHandAction(upgradedAttract, 1, false));
        }
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
        AbstractCard upgradedAttract = new MeowthAttract(true);
        upgradedAttract.upgrade();
        cardsToPreview = upgradedAttract;
    }
}
