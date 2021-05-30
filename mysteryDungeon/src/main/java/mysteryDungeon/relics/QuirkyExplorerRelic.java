package mysteryDungeon.relics;

import basemod.abstracts.CustomRelic;
import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.interfaces.GoldBonusInterface;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class QuirkyExplorerRelic extends CustomRelic implements GoldBonusInterface{ // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID("QuirkyExplorerRelic");

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("default_clickable_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("default_clickable_relic.png"));


    public QuirkyExplorerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);

        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public float goldBonusPercentage()
    {
        return 0.25f;
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
