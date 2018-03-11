package com.jdp30.ArrowDrift.game.Level;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public enum Direction {

    UP(0), RIGHT(1), DOWN(2), LEFT(3);

    int numValue;

    Direction(int numValue) {
        this.numValue = numValue;
    }
}
