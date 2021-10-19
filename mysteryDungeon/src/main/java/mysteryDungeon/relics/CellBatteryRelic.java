package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.AbstractPokemonRelic;
import mysteryDungeon.actions.SetPikaMeterAction;
import mysteryDungeon.characters.Pokemon;
import mysteryDungeon.pokemons.Pikachu;
import mysteryDungeon.ui.PikachuMeter;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.RelicStrings;

public class CellBatteryRelic extends AbstractPokemonRelic { // You must implement things you want to use from StSlib
    /*
     * https://github.com/daviscook477/BaseMod/wiki/Custom-Relics
     * StSLib for Clickable Relics
     *
     * Usable once per turn. Right click: Evoke your rightmost orb.
     */

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(CellBatteryRelic.class.getSimpleName());

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("cell-battery.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("cell-battery.png"));

    public CellBatteryRelic() {
        super(ID, IMG, OUTLINE, RelicTier.BOSS, LandingSound.CLINK);

        color = Pikachu.COLOR;
        tips.clear();
        tips.add(new PowerTip(name, description));
    }

    @Override
    public void onEquip() {
        if(AbstractDungeon.player instanceof Pokemon) {
            ((Pokemon)AbstractDungeon.player).maxPikachuChargeCounter = 2;
            PikachuMeter.maxCounterPosition = 2;
        }
    }

    @Override
    public void onUnequip() {
        if(AbstractDungeon.player instanceof Pokemon) {
            ((Pokemon)AbstractDungeon.player).maxPikachuChargeCounter = 3;
            PikachuMeter.maxCounterPosition = 3;
        }
    }

    @Override
    public void onPlayerEndTurn() {
        addToBot(new SetPikaMeterAction(0));
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
