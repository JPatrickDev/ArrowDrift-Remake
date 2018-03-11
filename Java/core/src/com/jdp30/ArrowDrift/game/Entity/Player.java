package com.jdp30.ArrowDrift.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Player extends Entity {
    public Player(int x, int y) {
        super(new Texture("player.png"), x, y);
    }

    @Override
    public void update(Level level) {
        super.update(level);
        if (!isMoving) {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                if (level.getCurrentMovementType().getUPDOWN() == 0)
                    moveTo(getX(), getY() + 1, level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                if (level.getCurrentMovementType().getUPDOWN() == 1)
                    moveTo(getX(), getY() - 1, level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (level.getCurrentMovementType().getLEFTRIGHT() == 0)
                    moveTo(getX() - 1, getY(), level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (level.getCurrentMovementType().getLEFTRIGHT() == 1)
                    moveTo(getX() + 1, getY(), level);
            }
        }
    }


}
