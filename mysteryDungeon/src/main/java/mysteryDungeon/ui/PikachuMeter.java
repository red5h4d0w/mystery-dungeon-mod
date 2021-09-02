package mysteryDungeon.ui;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class PikachuMeter {
    
    private static final float TEXT_OFFSET_X = 22.0F * Settings.scale;
  
    private static final float HEADER_OFFSET_Y = 12.0F * Settings.scale;
    
    private static final float BODY_OFFSET_Y = -20.0F * Settings.scale;
    
    private static final float BODY_TEXT_WIDTH = 300.0F * Settings.scale;
    
    private static final float TIP_DESC_LINE_SPACING = 26.0F * Settings.scale;
    
    private static final Color BASE_COLOR = new Color(1.0F, 0.9725F, 0.8745F, 1.0F);
    
    private static final float SHADOW_DIST_Y = 14.0F * Settings.scale;
    
    private static final float SHADOW_DIST_X = 9.0F * Settings.scale;
    
    private static final float BOX_EDGE_H = 32.0F * Settings.scale;
    
    private static final float BOX_BODY_H = 64.0F * Settings.scale;
    
    private static final float BOX_W = 325.0F * Settings.scale;
    private static final float METER_HEIGHT = 50.0f * Settings.scale;

    private static int counterPosition = 0;

    public PikachuMeter() { }
    
    public void update(AbstractPlayer player) {  }
    
    public void render(SpriteBatch sb, AbstractPlayer player) {
        if (AbstractDungeon.getCurrMapNode() != null && 
            AbstractDungeon.getCurrRoom() != null && 
            (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !player.isDead) {
            float x = 0f;
            float y = Settings.HEIGHT-165.0f*Settings.scale;
            //TODO: Localization
            String title = "Charge Meter";
            String description = "When you play a Skill, move to the left on this meter. NL When you play an Attack, move to the right on this meter. NL When you reach certain points on the track, enter the corresponding stances.";
            float h = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, description, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING) - 7.0F * Settings.scale;
            sb.setColor(Settings.TOP_PANEL_SHADOW_COLOR);
            sb.draw(ImageMaster.KEYWORD_TOP, x + SHADOW_DIST_X, y - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
            sb.draw(ImageMaster.KEYWORD_BODY, x + SHADOW_DIST_X, y - h - METER_HEIGHT - BOX_EDGE_H - SHADOW_DIST_Y, BOX_W, METER_HEIGHT + h + BOX_EDGE_H);
            sb.draw(ImageMaster.KEYWORD_BOT, x + SHADOW_DIST_X, y - h - METER_HEIGHT - BOX_BODY_H - SHADOW_DIST_Y, BOX_W, BOX_EDGE_H);
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.KEYWORD_TOP, x, y, BOX_W, BOX_EDGE_H);
            sb.draw(ImageMaster.KEYWORD_BODY, x, y - h - METER_HEIGHT - BOX_EDGE_H, BOX_W, METER_HEIGHT + h + BOX_EDGE_H);
            sb.draw(ImageMaster.KEYWORD_BOT, x, y - h - METER_HEIGHT - BOX_BODY_H, BOX_W, BOX_EDGE_H);
            sb.draw(ImageMaster.OPTION_SLIDER_BG, x + BOX_W/2 - 125f, y - h - BOX_BODY_H - METER_HEIGHT/2 + 12f, 125f, 12f, 250.0f, 24.0f, Settings.scale, Settings.scale, 0.0F, 0, 0, 250, 24, false, false);
            sb.draw(ImageMaster.OPTION_SLIDER, x + BOX_W/2 - 22f + counterPosition* 35f, y - h - BOX_BODY_H - METER_HEIGHT/2, 22f, 22.0F, 44.0F, 44.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 44, 44, false, false);
            FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, "-", x + BOX_W/2 - 120f, y - h - BOX_BODY_H - METER_HEIGHT/2, BASE_COLOR);
            
            float plusSymbolWidth = FontHelper.getSmartWidth(FontHelper.tipHeaderFont, "+", BOX_W, TIP_DESC_LINE_SPACING);
            FontHelper.renderFontLeft(sb, FontHelper.tipHeaderFont, "+", x + BOX_W/2 + 120f - plusSymbolWidth/2, y - h - BOX_BODY_H - METER_HEIGHT/2, BASE_COLOR);
            
            
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.tipHeaderFont, title, x + TEXT_OFFSET_X, y + HEADER_OFFSET_Y, Settings.GOLD_COLOR);
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, description, x + TEXT_OFFSET_X, y + BODY_OFFSET_Y, BODY_TEXT_WIDTH, TIP_DESC_LINE_SPACING, BASE_COLOR);
        } 
    }
    public void setCounterPosition(int newCounterPosition) {
        counterPosition = newCounterPosition;
    }
}
