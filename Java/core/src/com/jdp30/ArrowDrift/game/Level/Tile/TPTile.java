package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;

import static com.badlogic.gdx.graphics.g3d.particles.ParticleChannels.Color;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class TPTile extends AnimatedTile {

    private Color color;
    private int tX, tY;

    public TPTile(int x, int y, AllowedMovementType type, Color color, int tx, int ty) {
        super("tpTileAnim.png", x, y, false, type);
        this.color = color;
        this.tX = tx;
        this.tY = ty;
        getTextureObj().getTextureData().prepare();
        Pixmap m = getTextureObj().getTextureData().consumePixmap();
        for (int xx = 0; xx != getTextureObj().getWidth(); xx++) {
            for (int yy = 0; yy != getTextureObj().getHeight(); yy++) {
                if (new Color(m.getPixel(xx, yy)).toIntBits() == target.toIntBits()) {
                    m.setColor(color);
                    m.drawRectangle(xx, yy, 1, 1);
                }
            }
        }
        this.setTextureObj(new Texture(m));
        createAnimation();
    }


    @Override
    public void steppedOn(Entity e, Level level) {
        super.steppedOn(e, level);
        e.teleportTo(level,tX,tY);
    }

    @Override
    public Tile copy() {
        return new TPTile(x, y, getMovementType(), color, tX, tY);
    }

    @Override
    public String toStringFormat() {
        return "4" + "," + getMovementType().getID() + "," + tX + "," + tY + "," + color.toString();
    }

}
