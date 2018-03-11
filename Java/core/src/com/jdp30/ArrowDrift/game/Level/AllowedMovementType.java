package com.jdp30.ArrowDrift.game.Level;

/**
 * Created by Jack Patrick on 06/03/2018.
 *
 * Last Edit: 06/03/2018
 */
public enum AllowedMovementType {
    UP_LEFT(0, 0, 0), UP_RIGHT(0, 1, 1), DOWN_LEFT(1, 0, 2), DOWN_RIGHT(1, 1, 3),NONE(0,0,4);

    private int UPDOWN, LEFTRIGHT, id;

    AllowedMovementType(int updown, int leftright, int id) {
        this.UPDOWN = updown;
        this.LEFTRIGHT = leftright;
        this.id = id;
    }

    public int getUPDOWN() {
        return UPDOWN;
    }

    public int getLEFTRIGHT() {
        return LEFTRIGHT;
    }

    public int getID() {
        return id;
    }

    public static AllowedMovementType fromID(int i) {
        for(AllowedMovementType t : values()){
            if(t.getID() == i)
                return t;
        }
        return null;
    }
}