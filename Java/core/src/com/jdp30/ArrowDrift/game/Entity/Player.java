package com.jdp30.ArrowDrift.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Player extends Entity {
    private AllowedMovementType current = null;
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
        current = level.getCurrentMovementType();
    }

    @Override
    public void draw(SpriteBatch batch, int xo, int yo) {
        super.draw(batch, xo, yo);
        if(current != null && current != AllowedMovementType.NONE){
            batch.draw(Tile.movementTextures.get(current), (getX() * Tile.TILE_SIZE) + getxOff() + xo, (getY() * Tile.TILE_SIZE) + getyOff() + yo, Tile.TILE_SIZE, Tile.TILE_SIZE);
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
