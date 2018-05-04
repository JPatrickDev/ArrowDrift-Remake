package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.util.LevelUtil;

import java.util.Random;

/**
 * Created by Jack on 15/04/2018.
 */
public class LevelSelectionActor extends Actor {

    private int stars;
    private String path;
    private String name;

    private BitmapFont font, smallerFont;
    private int nameWidth, nameHeight, scoreHeight,scoreWidth;


    private ShapeRenderer renderer;
    private Texture enabledStar, disabledStar;
    float r, g, b;
    float padding;
    int j = 0;
    Random rnd = new Random();

    int moves, min;

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
        padding = getWidth() / 16.0f;
        String[] s = path.split("/");
        int i = LevelUtil.getMovesTaken(s[s.length - 1], s[1]);
        if (i != -1) {
            int diff = i - LevelUtil.getMinMoves(s[s.length - 1], s[1]);
            if (diff == 0) {
                stars = 3;
            } else {
                if (diff < 0) {
                    //Looks like our minMoves is wrong?
                    stars = 3;
                } else {
                    if (diff <= 5)
                        stars = 2;
                    else
                        stars = 1;
                }
            }
        } else {
            this.stars = 0;
        }

        this.moves = LevelUtil.getMovesTaken(s[s.length - 1], s[1]);
        this.min = LevelUtil.getMinMoves(s[s.length - 1], s[1]);
    }

    float s = 0.1f;
    float dS = 0.1f;

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
            if (height < (getHeight() - enabledStar.getHeight()) - padding * 2 && layout.width < getWidth() - padding * 2) {
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
        if (smallerFont == null && font != null) {
            smallerFont = new BitmapFont(Gdx.files.internal("fonts/cg12.fnt"), Gdx.files.internal("fonts/cg12.png"), false);
            smallerFont.getData().setScale(dS);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(smallerFont, moves + "");
            float height = layout.height;
            System.out.println((getWidth() - this.nameWidth) + ":" + layout.width);
            if (layout.height < disabledStar.getHeight()) {
                dS += 0.1;
                smallerFont = null;
                draw(batch, parentAlpha);
            } else {
                dS -= 1;
                smallerFont.getData().setScale(dS);
                layout.setText(smallerFont, moves + "");
                this.scoreHeight = (int) layout.height;
                this.scoreWidth = (int) layout.width;
            }
        }
        String[] s = path.split("/");
        batch.setColor(1, 1, 1, 1);
        font.draw(batch, name, getX() + padding / 2 + ((getWidth() - padding) / 2 - nameWidth / 2), getY() + padding);
        if(moves != -1)
        smallerFont.draw(batch, moves + "", (float) ((getX() + padding / 2 + (getWidth() - padding) / 2 - (enabledStar.getWidth() * 3) / 2) - (scoreWidth * 1.5)), (getY() + padding) - nameHeight - enabledStar.getHeight() + scoreHeight);
        else
            smallerFont.draw(batch,"-", (float) ((getX() + padding / 2 + (getWidth() - padding) / 2 - (enabledStar.getWidth() * 3) / 2) - (scoreWidth * 0.75)), (getY() + padding) - nameHeight - enabledStar.getHeight() + scoreHeight);

        smallerFont.draw(batch, min + "", (float) ((float) ((getX() + padding / 2 + (getWidth() - padding) / 2 - (enabledStar.getWidth() * 3) / 2)) + disabledStar.getWidth() * 3 ), (getY() + padding) - nameHeight - enabledStar.getHeight() + scoreHeight);
        for (int i = 0; i != 3; i++) {
            batch.draw(disabledStar, (getX() + padding / 2 + (getWidth() - padding) / 2 - (enabledStar.getWidth() * 3) / 2) + disabledStar.getWidth() * i, (getY() + padding) - nameHeight - enabledStar.getHeight());
        }
        int p = 0;
        for (int i = stars; i != 0; i--) {
            batch.draw(enabledStar, (getX() + padding / 2 + (getWidth() - padding) / 2 - (enabledStar.getWidth() * 3) / 2) + disabledStar.getWidth() * p, (getY() + padding) - nameHeight - enabledStar.getHeight());
            p++;
        }
    }

    public void recaulcuateFont() {
        font = null;
        smallerFont = null;
        s = 0.5f;
        dS = 0.5f;
    }


}
