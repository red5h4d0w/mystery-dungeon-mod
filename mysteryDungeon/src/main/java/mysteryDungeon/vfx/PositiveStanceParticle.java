package mysteryDungeon.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import mysteryDungeon.util.TextureLoader;

public class PositiveStanceParticle extends AbstractGameEffect {
    private float dur_div2 = this.duration / 2.0F;
  
    private float vX = MathUtils.random(-300.0F, -50.0F) * Settings.scale;
    
    private float vY = MathUtils.random(-200.0F, -100.0F) * Settings.scale;
    
    private float x = AbstractDungeon.player.hb.cX + MathUtils.random(100.0F, 160.0F) * Settings.scale - 32.0F;
    
    private float y = AbstractDungeon.player.hb.cY + MathUtils.random(-50.0F, 220.0F) * Settings.scale - 32.0F;
    
    private float dvx = 400.0F * Settings.scale * this.scale;
    
    private float dvy = 100.0F * Settings.scale;

    public Texture plusSign = TextureLoader.getTexture("mysteryDungeonResources/images/ui/plus.png");
    
    public PositiveStanceParticle() {
        this.color = Color.RED;
    }

    public void update() {
        x += vX * Gdx.graphics.getDeltaTime();
        y += vY * Gdx.graphics.getDeltaTime();
        vY += Gdx.graphics.getDeltaTime() * this.dvy;
        vX -= Gdx.graphics.getDeltaTime() * this.dvx;
        rotation = -(57.295776F * MathUtils.atan2(this.vX, this.vY)) - 0.0F;
        if(duration > dur_div2) {
            color.a = Interpolation.fade.apply(1.0F, 0.0F, (this.duration - this.dur_div2) / this.dur_div2);
        } 
        else {
            color.a = Interpolation.fade.apply(0.0F, 1.0F, this.duration / this.dur_div2);
        } 
        duration -= Gdx.graphics.getDeltaTime();
        if(duration < 0.0F) {
            isDone = true;
        }
    }
    
    public void render(SpriteBatch sb) {
        sb.setColor(color);
        sb.setBlendFunction(770, 1);
        sb.draw(plusSign, x, y, 32.0F, 32.0F, 25.0F, 128.0F, scale, scale + (this.dur_div2 * 0.4F - duration) * Settings.scale, rotation, 0, 0, 64, 64, false, false);
        sb.setBlendFunction(770, 771);
    }
    
    public void dispose() {}
}
