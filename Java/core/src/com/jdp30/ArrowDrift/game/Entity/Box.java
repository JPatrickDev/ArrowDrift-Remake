package com.jdp30.ArrowDrift.game.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Box extends Entity {
    public Box(int x, int y) {
        super(new Texture("box.png"),x,y);
    }


    @Override
    public void walkedInTo(Direction from, Level level) {
        super.walkedInTo(from, level);
        fling(from, level,1);
    }

    @Override
    public Entity copy() {
        return new Box(getX(),getY());
    }

    @Override
    public void teleportTo(Level level, int tX, int tY) {
        return;
    }
}
