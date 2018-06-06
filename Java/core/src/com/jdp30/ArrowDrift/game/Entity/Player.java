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
                moveUp(level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                moveDown(level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                moveLeft(level);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                moveRight(level);
            }
        }
    }

    public void moveUp(Level level) {
        if (level.getCurrentMovementType().getUPDOWN() == 0)
            moveTo(getX(), getY() + 1, level);
        if (isMoving) {
            level.moves++;
        }
    }

    public void moveDown(Level level) {
        if (level.getCurrentMovementType().getUPDOWN() == 1)
            moveTo(getX(), getY() - 1, level);
        if (isMoving) {
            level.moves++;
        }
    }

    public void moveLeft(Level level) {
        if (level.getCurrentMovementType().getLEFTRIGHT() == 0)
            moveTo(getX() - 1, getY(), level);
        if (isMoving) {
            level.moves++;
        }
    }

    public void moveRight(Level level) {
        if (level.getCurrentMovementType().getLEFTRIGHT() == 1)
            moveTo(getX() + 1, getY(), level);
        if (isMoving) {
            level.moves++;
        }
    }

    @Override
    public Entity copy() {
        return new Player(getX(), getY());
    }


}
