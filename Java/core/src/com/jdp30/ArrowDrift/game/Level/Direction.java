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


    public static Direction fromValue(int i){
        for(Direction d : values())
            if(d.numValue == i)
                return d;
        return null;
    }
    public static Direction next(Direction inout){
        int i = inout.numValue + 1;
        if(i >= 4)
            i = 0;
        return fromValue(i);
    }

    public int getID() {
        return numValue;
    }
}
