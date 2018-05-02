package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jdp30.ArrowDrift.game.util.LevelUtil;

import java.util.Random;

/**
 * Created by Jack on 15/04/2018.
 */
public class LevelSelectionActor extends Actor {

    private int stars;
    private String path;
    private String name;

    private BitmapFont font;
    private int nameWidth = 0, nameHeight;

    private ShapeRenderer renderer;
    private Texture enabledStar, disabledStar;
    float r, g, b;
    float padding;
    int j = 0;
    Random rnd = new Random();

    public LevelSelectionActor(int stars, String path, String name) {
        this.stars = stars;
        this.path = path;
        this.name = name;
        enabledStar = new Texture("ui/enabled_star.png");
        disabledStar = new Texture("ui/disabled_star.png");

        r = rnd.nextFloat();
        g = rnd.nextFloat();
        b = rnd.nextFloat();
       // this.stars = rnd.nextInt(4);
    }

    public void init() {
        padding = getWidth() / 4.0f;
        String[] s = path.split("/");
        int i = LevelUtil.getMovesTaken(s[s.length - 1], s[1]);
        if(i != -1){
            int diff = i - LevelUtil.getMinMoves(s[s.length - 1], s[1]);
            System.out.println(diff);
            if(diff == 0){
                stars = 3;
            }else{
                if(diff < 0){
                    //Looks like our minMoves is wrong?
                    stars = 3;
                }else{
                    if(diff <= 5)
                        stars = 2;
                    else
                        stars = 1;
                }
            }
        }else{
            this.stars = 0;
        }
    }

    float s = 0.5f;

    @Override
    public void draw(Batch batch, float parentAlpha) {

        super.draw(batch, parentAlpha);
        if (renderer == null) {
            renderer = new ShapeRenderer();

        }
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("fonts/cg40.fnt"), Gdx.files.internal("fonts/cg40.png"), false);
            font.getData().setScale(s);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, name);
            float height = layout.height;
            if (height < (getHeight() - enabledStar.getHeight()) - padding*2 && layout.width < getWidth() - padding * 2) {
                s += 0.1;
                font = null;
                draw(batch, parentAlpha);
            } else {
                s -= 1;
                font.getData().setScale(s);
                layout.setText(font, name);
                this.nameWidth = (int) layout.width;
                this.nameHeight = (int) layout.height;
            }
        }
        batch.setColor(1, 1, 1, 1);
        font.draw(batch, name, getX() + padding/2 + ((getWidth() - padding) / 2 - nameWidth / 2), getY() + padding);
        for (int i = 0; i != 3; i++) {
            batch.draw(disabledStar, (getX() + padding/2 + (getWidth()-padding)/2 - (enabledStar.getWidth()*3)/2) + disabledStar.getWidth() * i, (getY() + padding) - nameHeight - enabledStar.getHeight());
        }
        int p = 0;
        for (int i = stars; i != 0; i--) {
            batch.draw(enabledStar, (getX() + padding/2 + (getWidth()-padding)/2 - (enabledStar.getWidth()*3)/2) + disabledStar.getWidth() * p, (getY() + padding) - nameHeight - enabledStar.getHeight());
            p++;
        }
    }


}
