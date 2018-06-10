package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Wall extends Tile {
    public Wall(int x, int y, AllowedMovementType type) {
        super("wall.png", x, y, true, type);
    }

    @Override
    public void draw(SpriteBatch batch, int xo, int yo) {
        super.draw(batch, xo, yo);
    }

    @Override
    public void init(Level level) {
        super.init(level);
        Tile above = level.getTile((this.x / Tile.TILE_SIZE), (this.y / TILE_SIZE) + 1);
        Tile below = level.getTile((this.x / Tile.TILE_SIZE), (this.y / TILE_SIZE) - 1);
        Tile left = level.getTile((this.x / Tile.TILE_SIZE) - 1, (this.y / TILE_SIZE));
        Tile right = level.getTile((this.x / Tile.TILE_SIZE) + 1, (this.y / TILE_SIZE));
        if (above == null && left == null && right instanceof Wall && below instanceof Wall) {
            setTextureObj(new Texture("walls/topLeft.png"));
        }
        if(above == null && left instanceof Wall && right instanceof Wall){
            setTextureObj(new Texture("walls/top.png"));
        }
        if (above == null && right == null && left instanceof Wall && below instanceof Wall) {
            setTextureObj(new Texture("walls/topRight.png"));
        }
        if(below == null && left instanceof Wall && right instanceof Wall){
            setTextureObj(new Texture("walls/bottom.png"));
        }
        if(left == null && below == null && above instanceof  Wall && right instanceof Wall ){
            setTextureObj(new Texture("walls/bottomLeft.png"));
        }
        if(right == null && below == null && left instanceof Wall && above instanceof Wall){
            setTextureObj(new Texture("walls/bottomRight.png"));
        }
        if(left == null && above instanceof Wall && below instanceof Wall){
            setTextureObj(new Texture("walls/left.png"));
        }
        if(right == null && above instanceof Wall && below instanceof Wall){
            setTextureObj(new Texture("walls/right.png"));
        }
    }

    @Override
    public Tile copy() {
        return new Wall(x, y, getMovementType());
    }

    @Override
    public String toStringFormat() {
        return "1" + "," + getMovementType().getID();
    }
}
