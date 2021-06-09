package mysteryDungeon.cards.Squirtle;
import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
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
    private static final int BLOCK = 0;
    private static final int UPGRADE_BLOCK = 10;


    // /STAT DECLARATION/

    public SquirtleBodyPress() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new VFXAction((AbstractGameEffect)new WeightyImpactEffect(m.hb.cX, m.hb.cY))); 
        baseDamage = AbstractDungeon.player.currentBlock;
        if(p.hasPower(DexterityPower.POWER_ID)) {
            if(p.getPower(DexterityPower.POWER_ID).amount>0)
                baseDamage = AbstractDungeon.player.currentBlock*AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        calculateCardDamage(m);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        
        rawDescription = DESCRIPTION;
        if(upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
        }
        initializeDescription(); 
    }
      
    @Override
    public void applyPowers() {
        baseDamage = AbstractDungeon.player.currentBlock;
        if(AbstractDungeon.player.hasPower(DexterityPower.POWER_ID)) {
            if(AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount>0)
                baseDamage = AbstractDungeon.player.currentBlock*AbstractDungeon.player.getPower(DexterityPower.POWER_ID).amount;
        }
        super.applyPowers();
        rawDescription = cardStrings.DESCRIPTION;
        if(upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
        }
        rawDescription += EXTENDED_DESCRIPTION;
        initializeDescription(); 
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster m) { 
       
        super.calculateCardDamage(m);
        rawDescription = cardStrings.DESCRIPTION;
        if(upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
        }
        rawDescription += EXTENDED_DESCRIPTION;
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        rawDescription = cardStrings.DESCRIPTION;
        if(upgraded) {
            rawDescription = UPGRADE_DESCRIPTION;
        }
        initializeDescription();
      }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
