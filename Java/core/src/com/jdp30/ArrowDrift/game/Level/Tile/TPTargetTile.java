package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class TPTargetTile extends Tile {

    private Color color;
    public TPTargetTile(int x, int y, AllowedMovementType type, Color color) {
        super("tpTarget.png", x, y, false, type);
        this.color = color;
        getTextureObj().getTextureData().prepare();
        Pixmap m = getTextureObj().getTextureData().consumePixmap();
        for(int xx = 0; xx != getTextureObj().getWidth(); xx++){
            for(int yy = 0; yy != getTextureObj().getHeight();yy++){
                if(new Color(m.getPixel(xx,yy)).toIntBits() == target.toIntBits()) {
                    m.setColor(color);
                    m.drawRectangle(xx, yy, 1, 1);
                }
            }
        }
        this.setTextureObj(new Texture(m));
    }

    @Override
    public Tile copy() {
        return new TPTargetTile(x, y, getMovementType(),color);
    }

    @Override
    public String toStringFormat() {
        return "5" + "," + getMovementType().getID() + "," + color.toString();
    }
}
