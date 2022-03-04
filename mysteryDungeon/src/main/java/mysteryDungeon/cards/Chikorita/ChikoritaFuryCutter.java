package mysteryDungeon.cards.Chikorita;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.evacipated.cardcrawl.mod.stslib.actions.common.MoveCardsAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class ChikoritaFuryCutter extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(ChikoritaFuryCutter.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.CHIKORITA_GREEN;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int BASE_MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 4;

    public boolean goBack;

    // /STAT DECLARATION/

    public ChikoritaFuryCutter() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        isEthereal = true;
        exhaust = true;
        goBack = false;
    }

    // Actions the card should do.
    @Override
    @SuppressWarnings("all") // Removes a warning for the type cast
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SLASH_DIAGONAL));
        addToBot(new ModifyDamageAction(uuid, magicNumber));
        AbstractDungeon.actionManager.addToTurnStart(new MoveCardsAction(AbstractDungeon.player.hand, AbstractDungeon.player.exhaustPile,
        card -> card == this));
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
