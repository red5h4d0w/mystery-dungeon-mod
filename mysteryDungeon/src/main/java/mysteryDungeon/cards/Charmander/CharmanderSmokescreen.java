package mysteryDungeon.cards.Charmander;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import basemod.abstracts.CustomCard;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.characters.Pokemon;

public class CharmanderSmokescreen extends CustomCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Special Strike: Deal 7 (*) damage times the energy you currently have.
     */

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(CharmanderSmokescreen.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("CharmanderSkill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.CHARMANDER_RED;

    private static final int COST = 3;
    private static final int UPGRADE_COST = 2;
    private static final int BASE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public CharmanderSmokescreen() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = BASE_MAGIC_NUMBER;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new SmokeBombEffect(p.hb.cX, p.hb.cY)));
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, magicNumber), magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }
}
