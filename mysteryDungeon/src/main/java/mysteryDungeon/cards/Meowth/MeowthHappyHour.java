package mysteryDungeon.cards.Meowth;

import static mysteryDungeon.MysteryDungeon.makeCardPath;

import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SimpleAction;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.interfaces.onLoadCardMiscInterface;

public class MeowthHappyHour extends PokemonCard implements onLoadCardMiscInterface {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(MeowthHappyHour.class);
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(MeowthHappyHour.class);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.MEOWTH_WHITE;

    private static final int COST = 0;
    private static final int BASE_MAGIC_NUMBER = 10;
    private static final int BASE_SECOND_MAGIC_NUMBER = 1;
    private static final int UPGRADE_SECOND_MAGIC_NUMBER = 1;


    // /STAT DECLARATION/

    public MeowthHappyHour() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        misc = BASE_MAGIC_NUMBER;
        baseMagicNumber = misc;
        magicNumber = baseMagicNumber;
        baseSecondMagicNumber = BASE_SECOND_MAGIC_NUMBER;
        secondMagicNumber = baseSecondMagicNumber;
        exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SimpleAction(() -> {
            AbstractDungeon.player.masterDeck.group.stream()
                .filter(c -> c.uuid == uuid)
                .forEach(c -> {
                    c.misc += secondMagicNumber;
                    c.applyPowers();
                    c.baseMagicNumber = c.misc;
                    c.magicNumber = c.misc;
                });
            GetAllInBattleInstances.get(uuid).stream().forEach(c -> {
                c.misc += secondMagicNumber;
                c.applyPowers();
                c.baseMagicNumber = c.misc;
                c.magicNumber = c.misc;
            });
        }));
        addToBot(new GainGoldAction(magicNumber));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeSecondMagicNumber(UPGRADE_SECOND_MAGIC_NUMBER);
            initializeDescription();
        }
    }

    @Override
    public void onLoadCardMisc(int miscAmount) {
        baseMagicNumber = miscAmount;
        magicNumber = miscAmount;
        initializeDescription();
    }
}
