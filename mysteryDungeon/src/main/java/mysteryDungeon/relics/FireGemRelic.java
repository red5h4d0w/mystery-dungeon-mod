package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Cyndaquil;
import mysteryDungeon.powers.BurnPower;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FireGemRelic extends PokemonRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(FireGemRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("wave-incense.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("wave-incense.png"));

    public FireGemRelic() {
        super(ID, IMG, OUTLINE, RelicTier.COMMON, LandingSound.CLINK);
        cardColor = Cyndaquil.CARD_COLOR;
    }

    @Override
    public void atPreBattle()
    {
        for(AbstractMonster m: AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, 10)));
            }
        }
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
