package mysteryDungeon.relics;

import mysteryDungeon.MysteryDungeon;
import mysteryDungeon.abstracts.PokemonRelic;
import mysteryDungeon.pokemons.Bulbasaur;
import mysteryDungeon.util.TextureLoader;

import static mysteryDungeon.MysteryDungeon.makeRelicOutlinePath;
import static mysteryDungeon.MysteryDungeon.makeRelicPath;

import java.util.HashSet;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerToRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import basemod.interfaces.CloneablePowerInterface;

public class BlackSludgeRelic extends PokemonRelic {

    // ID, images, text.
    public static final String ID = MysteryDungeon.makeID(BlackSludgeRelic.class);

    private static final RelicStrings relicStrings = CardCrawlGame.languagePack.getRelicStrings(ID);
    public static final String NAME = relicStrings.NAME;
    public static final String[] DESCRIPTIONS = relicStrings.DESCRIPTIONS;
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public BlackSludgeRelic() {
        super(ID, IMG, OUTLINE, RelicTier.UNCOMMON, LandingSound.CLINK);

        cardColors = new HashSet<CardColor>() {
            {
                add(Bulbasaur.CARD_COLOR);
            }
        };

    }

    public void onMonsterDeath(AbstractMonster m) {
      if (m.powers.stream().filter(p->p.type == PowerType.DEBUFF).count() >= 3 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
        m.powers.stream().filter(p -> p.type == PowerType.DEBUFF).forEach(p -> {
          if (p.amount > 1 && p instanceof CloneablePowerInterface) {
            flash();
            addToBot(new RelicAboveCreatureAction(m, this));
            AbstractPower powerToApply = ((CloneablePowerInterface) p).makeCopy();
            powerToApply.amount /= 2;
            addToBot(new ApplyPowerToRandomEnemyAction(AbstractDungeon.player, powerToApply, powerToApply.amount, false, AbstractGameAction.AttackEffect.POISON));
          }
        });
      }
    }

    // Description
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
