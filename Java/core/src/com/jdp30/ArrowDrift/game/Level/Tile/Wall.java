package com.jdp30.ArrowDrift.game.Level.Tile;

import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

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
    public Tile copy() {
        return new Wall(x, y, getMovementType());
    }
}
