package mysteryDungeon.cards.Cyndaquil;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.characters.Pokemon;

public class CyndaquilRollout extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(CyndaquilRollout.class.getSimpleName());
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
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DMG = 1;
    private static final int BLOCK = 3;
    private static final int UPGRADE_PLUS_BLOCK = 1;

    // /STAT DECLARATION/

    public CyndaquilRollout() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
      for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
        if (c.type == AbstractCard.CardType.ATTACK)
          count++;  
        if (c.type == AbstractCard.CardType.SKILL)
          count++;
        if (c.type == AbstractCard.CardType.POWER)
          count++;  
        }
            for (int i = 0; i < count-1; i++){
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
            }
        
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
