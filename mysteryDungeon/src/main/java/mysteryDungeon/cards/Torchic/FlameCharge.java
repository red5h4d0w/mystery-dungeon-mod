package mysteryDungeon.cards.Torchic;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.AddComboAction;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.powers.BurnPower;
import mysteryDungeon.util.TorchicComboManager.Combo;
import mysteryDungeon.util.TorchicComboManager.Move;

public class FlameCharge extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(FlameCharge.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(FlameCharge.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = Pokemon.Enums.TORCHIC_RED;

    private static final int COST = 0;

    // /STAT DECLARATION/

    public FlameCharge() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AddComboAction(new Combo(new SimpleAction(() -> {
            // Stuff to do in the finisher
            // IMPORTANT: addToTop in Finisher, because finisher is itself an action
            if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                for (AbstractMonster monster : (AbstractDungeon.getMonsters()).monsters) {
                    if (!monster.isDead && !monster.isDying) {
                        addToTop(new ApplyPowerAction(monster, p, new BurnPower(monster, 12), 12));
                    }
                }
            }
        }), Move.SKILL, Move.SKILL, Move.ATTACK)));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
