package com.jdp30.ArrowDrift.game.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class ImgButton {
    private int x, y;
    private Texture texture;

    public ImgButton(String texture, int x, int y) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(texture);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public void setTexture(Texture texture){
        if(this.texture != null)
            this.texture.dispose();
        this.texture = texture;
    }
}
