package mysteryDungeon.cards.Pikachu;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.actions.ThunderboltAction;
import mysteryDungeon.cards.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class PikachuThunderbolt extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(PikachuThunderbolt.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("PikachuAttack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    

    private static final int COST = -1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int BASE_MAGIC_NUMBER = 4;


    // /STAT DECLARATION/

    public PikachuThunderbolt() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        // Create an int which equals to your current energy.
        if (energyOnUse < EnergyPanel.totalCount)
        {
            energyOnUse = EnergyPanel.totalCount; 
        }
        if (p.hasRelic("Chemical X")) {
            this.energyOnUse += 2;
            p.getRelic("Chemical X").flash();
        } 
        for (int i = 0; i < this.energyOnUse; i++) 
        {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.LIGHTNING));
        }
        if (!this.freeToPlayOnce)
        {
            p.energy.use(EnergyPanel.totalCount);
        }
        addToBot(new ThunderboltAction());
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
