package mysteryDungeon.cards.Cyndaquil;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.RandomBurnAction;
import mysteryDungeon.characters.Pokemon;

public class CyndaquilFlamethrower extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(CyndaquilFlamethrower.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("Attack.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Pokemon.Enums.CYNDAQUIL_RED;

    private static final int COST = 1;
    private static final int DAMAGE = 1;
    private static final int BASE_MAGIC_NUMBER = 3;
    private static final int UPGRADE_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public CyndaquilFlamethrower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = BASE_MAGIC_NUMBER;
        magicNumber = baseMagicNumber;
        cardsToPreview = (AbstractCard)new Burn();
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Create an int which equals to your current energy.
        addToBot(new MakeTempCardInDrawPileAction(new Burn(), 1, true, false, false));
        for (int i = 0; i < this.magicNumber; i++)
            addToBot((AbstractGameAction)new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.FIRE));
        AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (randomMonster != null)
            addToBot(new RandomBurnAction(randomMonster, 9, magicNumber));            
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
