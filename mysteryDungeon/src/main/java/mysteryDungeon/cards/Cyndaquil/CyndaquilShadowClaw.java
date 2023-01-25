package mysteryDungeon.cards.Cyndaquil;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.interfaces.ClawCardInterface;

public class CyndaquilShadowClaw extends PokemonCard implements ClawCardInterface {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(CyndaquilShadowClaw.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("CyndaquilAttack");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.CYNDAQUIL_RED;

    private static final int COST = 0;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    // /STAT DECLARATION/

    public CyndaquilShadowClaw() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        this.cardsToPreview = (AbstractCard)new Dazed();
    }
    

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
    }

    @Override
    public void clawUpgrade(int amount) {
        baseDamage += amount;
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
