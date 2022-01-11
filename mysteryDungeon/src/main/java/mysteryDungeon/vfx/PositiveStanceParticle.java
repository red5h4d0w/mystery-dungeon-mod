package mysteryDungeon.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import mysteryDungeon.util.TextureLoader;

public class PositiveStanceParticle extends AbstractGameEffect {

    public Logger logger = LogManager.getLogger(PositiveStanceParticle.class);

    private float speed = MathUtils.random(0.01F, 50.0F) * Settings.scale;

    private float direction;

    private float x;

    private float y;

    public Texture plusSign = TextureLoader.getTexture("mysteryDungeonResources/images/ui/add.png");
    
    public PositiveStanceParticle() {
        scale = MathUtils.random(1.0F, 1.5F);
        startingDuration = scale + 0.8F;
        duration = this.startingDuration;
        scale *= Settings.scale;
        color = Color.RED;
        renderBehind = MathUtils.randomBoolean();
        this.x = AbstractDungeon.player.hb.cX + MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 50.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 50.0F * Settings.scale);
        this.y = AbstractDungeon.player.hb.cY + MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F + 10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 20.0F * Settings.scale);
        direction = MathUtils.atan2(y, x);
        logger.info(direction);
    }

    public void update() {
        x += speed * MathUtils.cos(direction) * Gdx.graphics.getDeltaTime();
        y += speed * MathUtils.sin(direction) * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F) {
            isDone = true;
        }
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        // sb.setBlendFunction(770, 1);
        sb.draw(plusSign, x, y, 32.0F, 32.0F, 32.0F, 32.0F, scale, scale, rotation, 0, 0, 64, 64, false, false);
        // sb.setBlendFunction(770, 771);
    }
    
    public void dispose() {}
}
