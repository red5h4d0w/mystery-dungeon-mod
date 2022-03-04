package mysteryDungeon.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.powers.RecoverPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

public class BigRootRelic extends PokemonRelic {
    public static final String ID = MysteryDungeon.makeID(BigRootRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("big-root.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("big-root.png"));

    public BigRootRelic() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth < p.maxHealth * 0.8f) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new RecoverPower(AbstractDungeon.player, 0), 0));
        }
    }

    @Override
    public void onPlayerEndTurn() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.currentHealth >= p.maxHealth * 0.8f)
            addToBot(new RemoveSpecificPowerAction(p, p, new RecoverPower(p, 0)));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
