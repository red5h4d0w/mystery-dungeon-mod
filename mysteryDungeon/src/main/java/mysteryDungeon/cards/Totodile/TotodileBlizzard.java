package mysteryDungeon.cards.Totodile;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class TotodileBlizzard extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(TotodileBlizzard.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("TotodileAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.TOTODILE_BLUE;

    private static final int COST = 3;
    private static final int BASE_MAGIC_NUMBER = 6;
    private static final int UPGRADE_MAGIC_NUMBER = 2;


    // /STAT DECLARATION/

    public TotodileBlizzard() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = 0;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int orbCount = (int)AbstractDungeon.actionManager.orbsChanneledThisCombat.stream().filter(o -> !(o instanceof EmptyOrbSlot)).count();
        baseDamage = magicNumber * orbCount;
        if (Settings.FAST_MODE) {
            addToBot(new VFXAction(new BlizzardEffect(orbCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
        } else {
            addToBot(new VFXAction(new BlizzardEffect(orbCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
        } 
        addToBot( new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.SMASH));
    }


    @Override
    public void applyPowers() {
        super.applyPowers();
        int orbCount = (int)AbstractDungeon.actionManager.orbsChanneledThisCombat.stream().filter(o -> !(o instanceof EmptyOrbSlot)).count();
        baseDamage = magicNumber * orbCount;
        super.applyPowers();
        rawDescription = String.format(cardStrings.EXTENDED_DESCRIPTION[0], baseDamage);
        initializeDescription();
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
