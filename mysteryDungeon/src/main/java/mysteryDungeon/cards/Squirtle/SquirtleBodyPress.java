package mysteryDungeon.cards.Squirtle;
import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.cards.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class SquirtleBodyPress extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(SquirtleBodyPress.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("SquirtleAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION[0];

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.SQUIRTLE_BLUE;

    private static final int COST = 3;
    private static final int BASE_DAMAGE = 24;
    private static final int UPGRADE_PLUS_DMG = 8;
    private static final int BASE_MAGIC_NUMBER = 2;
    private static final int UPGRADE_MAGIC_NUMBER = 1;
    private static final int BASE_BLOCK = 10;



    // /STAT DECLARATION/

    public SquirtleBodyPress() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = BASE_DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        baseBlock = BASE_BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn)));
        {
            if (m != null)
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
            addToBot(new GainBlockAction(p, p, baseBlock));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
