package com.jdp30.ArrowDrift.game.Level.Tile;

import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class BeltTile extends AnimatedTile{
    private Direction dir;

    public BeltTile(int x, int y, Direction dir) {
        super("beltAnim.png", x, y, false,AllowedMovementType.NONE);
        this.dir = dir;
    }

    @Override
    public void steppedOn(Entity e, Level level) {
        e.fling(dir,level);
    }
}
