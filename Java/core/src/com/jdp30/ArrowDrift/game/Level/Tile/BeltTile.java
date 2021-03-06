package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class BeltTile extends AnimatedTile {
    private Direction dir;

    public BeltTile(int x, int y, Direction dir) {
        super("beltAnim.png", x, y, false, AllowedMovementType.NONE);
        this.dir = dir;
    }

    @Override
    public void steppedOn(Entity e, Level level) {
        e.fling(dir, level);
    }

    @Override
    public Tile copy() {
        return new BeltTile(x, y, dir);
    }


    @Override
    public void draw(SpriteBatch batch, int xo, int yo) {
        int angle = getAngle();
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion t = animation.getKeyFrame(stateTime, true);
        if (t != null)
            batch.draw(t, x + xo, y + yo, Tile.TILE_SIZE/2, TILE_SIZE/2, TILE_SIZE, TILE_SIZE, 1, 1, angle);
    }

    public int getAngle() {
        int angle = 0;
        switch (dir) {
            case LEFT:
                angle = 90;
                break;
            case DOWN:
                angle = 180;
                break;
            case RIGHT:
                angle = 270;
                break;
        }
        return angle;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    @Override
    public String toStringFormat() {
        return "2" + "," + getMovementType().getID() + "," + dir.getID();
    }
}
