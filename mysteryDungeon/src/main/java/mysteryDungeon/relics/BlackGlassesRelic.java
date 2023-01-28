package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Meowth;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class BlackGlassesRelic extends PokemonRelic  { 

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(BlackGlassesRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("miracle-seed.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("miracle-seed.png"));

    public BlackGlassesRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

        cardColor = Meowth.CARD_COLOR;
        
    }

    @Override
    public void atTurnStart() {
        for(AbstractCreature monster: AbstractDungeon.getMonsters().monsters){
            if (monster.hasPower(StrengthPower.POWER_ID)){
                int strengthStacks = monster.getPower(StrengthPower.POWER_ID).amount;   
                if(strengthStacks > 0)
                    addToTop(new DamageAction(monster, new DamageInfo(AbstractDungeon.player, strengthStacks, DamageType.THORNS), AttackEffect.SLASH_VERTICAL));
            }
        }
    }

    
    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
