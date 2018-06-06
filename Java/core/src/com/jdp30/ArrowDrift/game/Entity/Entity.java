package com.jdp30.ArrowDrift.game.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public abstract class Entity implements Disposable {

    //Tile coords
    private int x, y;
    private int xOff, yOff;
    private Texture texture;
    protected boolean isMoving = false;

    private int tX = -1, tY = -1;

    private int moveSpeed = Tile.TILE_SIZE/8;

    private boolean isFlung = false;
    private Direction flingDir = null;
    private boolean flung;

    public Entity(Texture texture, int x, int y) {
        this.texture = texture;
        this.x = x;
        this.y = y;
        this.xOff = 0;
        this.yOff = 0;
    }

    public void update(Level level) {
        if (isMoving) {
            if (tX != -1) {
                if (tX > x) {
                    xOff += moveSpeed;
                } else if (tX < x) {
                    xOff -= moveSpeed;
                }
            }
            if (tY != -1) {
                if (tY > y) {
                    yOff += moveSpeed;
                } else if (tY < y) {
                    yOff -= moveSpeed;
                }
            }
            if (Math.abs(xOff) >= Tile.TILE_SIZE && tX != -1) {
                if (tX > x) {
                    x++;
                } else if (tX < x) {
                    x--;
                }
            }
            if (Math.abs(yOff) >= Tile.TILE_SIZE && tY != -1) {
                if (tY > y) {
                    y++;
                } else if (tY < y) {
                    y--;
                }
            }

            if (tX == x && tY == y) {
                tX = -1;
                tY = -1;
                yOff = 0;
                xOff = 0;
                isMoving = false;
                level.getTile(x, y).steppedOn(this, level);
                if (isFlung) {
                    tX = getX();
                    tY = getY();
                    switch (flingDir) {
                        case UP:
                            tY = getY() + 1;
                            break;
                        case DOWN:
                            tY = getY() - 1;
                            break;
                        case LEFT:
                            tX = getX() - 1;
                            break;
                        case RIGHT:
                            tX = getX() + 1;
                            break;
                    }
                    moveTo(tX, tY, level);
                    if (!isMoving) {
                        isFlung = false;
                        flingDir = null;
                        tX = -1;
                        tY = -1;
                    }
                }
            }
        }
    }

    public void draw(SpriteBatch batch, int xo, int yo) {
        batch.draw(texture, (x * Tile.TILE_SIZE) + xOff + xo, (y * Tile.TILE_SIZE) + yOff + yo,Tile.TILE_SIZE,Tile.TILE_SIZE);
    }

    public void moveTo(int tX, int tY, Level level) {
        if (!(tX == x || tY == y))
            return;

        if (!level.canMoveTo(tX, tY)) {
            Direction from = null;
            if (tX > x) {
                from = Direction.RIGHT;
            }
            if (tX < x) {
                from = Direction.LEFT;
            }
            if (tY > y) {
                from = Direction.UP;
            }
            if (tY < y)
                from = Direction.DOWN;
            level.walkedInTo(tX, tY, from, this);
            return;
        }
        this.tX = tX;
        this.tY = tY;
        isMoving = true;
    }

    public void fling(Direction direction, Level level) {
        isFlung = true;
        flingDir = direction;
        isMoving = false;

        tX = getX();
        tY = getY();
        switch (direction) {
            case UP:
                tY = getY() + 1;
                break;
            case DOWN:
                tY = getY() - 1;
                break;
            case LEFT:
                tX = getX() - 1;
                break;
            case RIGHT:
                tX = getX() + 1;
                break;
        }
        moveTo(tX, tY, level);
        if (!isMoving) {
            isFlung = false;
            flingDir = null;
            tX = -1;
            tY = -1;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public boolean isMoving() {
        return isMoving;
    }

    public boolean isFlung() {
        return flung;
    }

    public void walkedInTo(Direction from, Level level) {

    }

    public void movedBy(int dX, int dY, Level level) {
        moveTo(getX() + dX, getY() + dY, level);
    }

    public Texture getTexture() {
        return texture;
    }

    public abstract Entity copy();

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
