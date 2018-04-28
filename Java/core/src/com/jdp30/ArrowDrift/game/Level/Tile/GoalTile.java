package com.jdp30.ArrowDrift.game.Level.Tile;

import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack on 28/04/2018.
 */
public class GoalTile extends Tile {

    public GoalTile(int x, int y) {
        super("goal.png", x, y, false, AllowedMovementType.NONE);
    }

    @Override
    public Tile copy() {
        return new GoalTile(x, y);
    }

    @Override
    public String toStringFormat() {
        return "3" + "," + getMovementType().getID();
    }
}
