package mysteryDungeon.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import mysteryDungeon.util.TextureLoader;

public class NegativeStanceParticle extends AbstractGameEffect {

    // Set speed to be random but non-zero
    private float speed = MathUtils.random(1.00F, 40.0F) * Settings.scale;

    // Store the travel direction of the particle
    private float direction;

    // initial x coordinate of the particle
    private float xi;

    // x coordinate of the particle
    private float x;

    // y coordinate of the particle
    private float y;

    public Texture plusSign = TextureLoader.getTexture("mysteryDungeonResources/images/ui/sub.png");
    
    public NegativeStanceParticle() {
        scale = MathUtils.random(1.0F, 1.5F);
        startingDuration = scale + 0.8F;
        duration = this.startingDuration;
        scale *= Settings.scale;
        color = Color.ROYAL.cpy();
        renderBehind = MathUtils.randomBoolean();
        this.x = MathUtils.random(-AbstractDungeon.player.hb.width / 2.0F - 50.0F * Settings.scale, AbstractDungeon.player.hb.width / 2.0F + 50.0F * Settings.scale);
        this.y = MathUtils.random(-AbstractDungeon.player.hb.height / 2.0F + 10.0F * Settings.scale, AbstractDungeon.player.hb.height / 2.0F - 20.0F * Settings.scale);
        direction = MathUtils.atan2(-y, -x);
        this.x += AbstractDungeon.player.hb.cX;
        this.y += AbstractDungeon.player.hb.cY;
        this.xi = x;
    }

    public void update() {
        x += speed * MathUtils.cos(direction) * Gdx.graphics.getDeltaTime();
        y += speed * MathUtils.sin(direction) * Gdx.graphics.getDeltaTime();
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F || x*xi < 0.0F) {
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
