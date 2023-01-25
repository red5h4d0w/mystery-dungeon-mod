package mysteryDungeon.cards.Pikachu;

import static mysteryDungeon.MysteryDungeon.makeCardPath;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonCard;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.characters.Pokemon;

public class PikachuElectricTerrain extends PokemonCard {

    // TEXT DECLARATION

    public static final String ID = MysteryDungeon.makeID(PikachuElectricTerrain.class.getSimpleName());
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String IMG = makeCardPath(PikachuElectricTerrain.class.getSimpleName());
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Pokemon.Enums.PIKACHU_YELLOW;
    

    private static final int COST = 0;


    // /STAT DECLARATION/

    public PikachuElectricTerrain() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        exhaust = true;
        inert = true;
        
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m){
        addToBot(new SetPikaMeterAction(3));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            selfRetain = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
