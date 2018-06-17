package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;

import java.awt.*;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class ImgButton extends Actor {
    private Texture texture;

    public ImgButton(String texture, int x, int y, int width, int height) {
        this.texture = new Texture(texture);
        setBounds(x, y, width, height);
        setTouchable(Touchable.enabled);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    public void setTexture(Texture texture) {
        if (this.texture != null)
            this.texture.dispose();
        this.texture = texture;
    }


}
