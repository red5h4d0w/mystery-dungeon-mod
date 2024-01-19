package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Bulbasaur;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.HashSet;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class MiracleSeedRelic extends PokemonRelic implements OnApplyPowerRelic {

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(MiracleSeedRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("miracle-seed.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("miracle-seed.png"));

    public MiracleSeedRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

        cardColors = new HashSet<CardColor>() {
            {
                add(Bulbasaur.CARD_COLOR);
            }
        };

    }

    @Override
    public void atTurnStart() {
        counter = 0;
    }

    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.type == PowerType.DEBUFF && !target.hasPower(ArtifactPower.POWER_ID)) {
            counter++;
            if (counter % 3 == 0) {
                flash();
                counter = 0;
                addToBot(new DrawCardAction(AbstractDungeon.player, 1));
            }
        }
        return true;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
