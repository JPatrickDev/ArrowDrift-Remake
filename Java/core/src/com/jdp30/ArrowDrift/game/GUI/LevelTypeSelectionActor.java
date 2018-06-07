package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import storage.Node;

/**
 * Created by Jack Patrick on 03/04/2018.
 * <p>
 * Last Edit: 03/04/2018
 */
public class LevelTypeSelectionActor extends Actor {

    private String path, name;
    private Texture previewTexture;
    private int nameWidth;
    private float prevWidth, prevHeight, padding;

    private BitmapFont font;

    private Node parent;
    public LevelTypeSelectionActor(String path, Node node) {
        this.path = path;
        this.name = path;
        this.parent = node;
    }


    public void init() {
        float padding = getWidth() / 8;
        float width = getWidth() - (padding * 2);
        float height = getHeight() - (padding * 2);
        this.previewTexture = parent.getTexture("preview");
        this.prevWidth = height;
        this.prevHeight = height;
        this.padding = padding;
        setDebug(true);
    }

    float s = 1.1f;

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (font == null) {
            font = new BitmapFont(Gdx.files.internal("fonts/cg40.fnt"), Gdx.files.internal("fonts/cg40.png"), false);
            font.getData().setScale(s);
            GlyphLayout layout = new GlyphLayout();
            layout.setText(font, name);
            float height = layout.height;
            if (height < padding - 10) {
                s += 0.1;
                font = null;
                draw(batch, parentAlpha);
            } else {
                s -= 0.5;
                font.getData().setScale(s);
                this.font = font;
                layout.setText(font, name);
                this.nameWidth = (int) layout.width;
            }
        }
        super.draw(batch, parentAlpha);
        batch.draw(previewTexture, getX() + (getWidth()/2 - prevWidth/2), getY() + padding, prevWidth, prevHeight);
        font.draw(batch, name, getX() + (getWidth() / 2 - this.nameWidth/2), getY() + padding - 10);
    }
}
