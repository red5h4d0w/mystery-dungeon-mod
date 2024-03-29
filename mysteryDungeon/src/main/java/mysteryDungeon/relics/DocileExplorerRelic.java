package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class DocileExplorerRelic extends PokemonRelic implements OnReceivePowerRelic { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(DocileExplorerRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("band14.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("band14.png"));


    public DocileExplorerRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.CLINK);
    }

    public boolean onReceivePower(AbstractPower power, AbstractCreature source)
    {
        if(power.type == PowerType.DEBUFF && !AbstractDungeon.player.hasPower(ArtifactPower.POWER_ID))
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new NextTurnBlockPower(AbstractDungeon.player, 2)));
        return true;
    } 

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
