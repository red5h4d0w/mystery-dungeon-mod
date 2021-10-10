package mysteryDungeon.cards.Pikachu;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.cards.PokemonCard;
import mysteryDungeon.cards.tempCards.ChooseAttack;
import mysteryDungeon.cards.tempCards.ChooseNegative;
import mysteryDungeon.cards.tempCards.ChoosePositive;
import mysteryDungeon.cards.tempCards.ChooseSkill;
import mysteryDungeon.characters.Pokemon;

public class PikachuHiddenPower extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(PikachuHiddenPower.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath("PikachuSkill.png");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    

    private static final int COST = 1;


    // /STAT DECLARATION/

    public PikachuHiddenPower() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        inert = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
       
        if (upgraded) {
            ArrayList<AbstractCard> stanceChoices = new ArrayList<>();
            stanceChoices.add(new ChooseAttack());
            stanceChoices.add(new ChooseSkill());
            addToBot(new ChooseOneAction(stanceChoices));  
        }
        else
        {
            switch(AbstractDungeon.cardRandomRng.random(1))
            {
                case(0):     
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.ATTACK).makeCopy();
                c.setCostForTurn(0);
                addToBot((AbstractGameAction)new MakeTempCardInHandAction(c, true));
                    break;
                case(1):
                AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.SKILL).makeCopy();
                c.setCostForTurn(-99);
                addToBot((AbstractGameAction)new MakeTempCardInHandAction(c, true));
                    break;
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
