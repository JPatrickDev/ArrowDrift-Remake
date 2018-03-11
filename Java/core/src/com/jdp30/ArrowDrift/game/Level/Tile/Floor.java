package com.jdp30.ArrowDrift.game.Level.Tile;

import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Floor extends Tile {

    public Floor(int x, int y, AllowedMovementType type) {
        super("floor.png", x, y, false, type);
    }

}
