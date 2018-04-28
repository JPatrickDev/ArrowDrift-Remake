package com.jdp30.ArrowDrift.game.Level.Tile;

import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack on 28/04/2018.
 */
public class GoalTile extends Tile {

    public GoalTile(int x, int y, AllowedMovementType type) {
        super("goal.png", x, y, false, type);
    }

    @Override
    public Tile copy() {
        return new GoalTile(x, y, getMovementType());
    }

    @Override
    public String toStringFormat() {
        return "3" + "," + getMovementType().getID();
    }
}
