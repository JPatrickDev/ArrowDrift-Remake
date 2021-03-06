package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Level;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p/>
 * Last Edit: 05/03/2018
 */
public abstract class Tile implements Disposable {

    public int x;
    public int y;
    private boolean solid;
    private String texture;
    public static int TILE_SIZE = 32;
    private Texture textureObj;


    public static Color target = Color.valueOf("#FF00AE");
    public static HashMap<AllowedMovementType, Texture> movementTextures = new HashMap<AllowedMovementType, Texture>();

    static {
        movementTextures.put(AllowedMovementType.DOWN_LEFT, new Texture("downLeft.png"));
        movementTextures.put(AllowedMovementType.UP_LEFT, new Texture("upLeft.png"));
        movementTextures.put(AllowedMovementType.DOWN_RIGHT, new Texture("downRight.png"));
        movementTextures.put(AllowedMovementType.UP_RIGHT, new Texture("upRight.png"));
    }

    private AllowedMovementType movement = null;

    public Tile(String texture, int x, int y, boolean solid, AllowedMovementType type) {
        this.x = x;
        this.y = y;
        this.texture = texture;
        textureObj = new Texture(texture);
        this.solid = solid;
        this.movement = type;
    }

    public void draw(SpriteBatch batch, int xo, int yo) {
        batch.draw(textureObj, x + xo, y + yo, Tile.TILE_SIZE, Tile.TILE_SIZE);
        if (movement != AllowedMovementType.NONE) {
            Texture t = movementTextures.get(movement);
            batch.draw(t, x + xo, y + yo, Tile.TILE_SIZE, Tile.TILE_SIZE);
        }
    }

    public void steppedOn(Entity e, Level level) {
        System.out.println("Stepped on");
        //  e.fling(Direction.values()[new Random().nextInt(4)], level);
    }

    public void walkedInTo(Entity e, Direction from) {
        System.out.println("Walked in to : " + from);
    }

    public boolean isSolid() {
        return solid;
    }

    @Override
    public void dispose() {
        textureObj.dispose();
    }

    public AllowedMovementType getMovementType() {
        return movement;
    }

    //5 - TP Target
    //4 - TP Tile
    //3 = Goal
    //1 = WALL
    //2 = Belt
    //0 = FLOOR
    public static Tile fromID(int ID, int x, int y, AllowedMovementType allowedMovementType, String[] tileData) {
        if (ID == 1) {
            return new Wall(x, y, allowedMovementType);
        } else if (ID == 0) {
            return new Floor(x, y, allowedMovementType);
        } else if (ID == 2) {
            int dir = Integer.parseInt(tileData[2]);
            Direction d = null;
            if (dir == 0)
                d = Direction.UP;
            if (dir == 1)
                d = Direction.RIGHT;
            if (dir == 2)
                d = Direction.DOWN;
            if (dir == 3)
                d = Direction.LEFT;
            return new BeltTile(x, y, d);
        } else if (ID == 3) {
            return new GoalTile(x, y);
        } else if (ID == 4) {
            int tX = 0, tY = 0;
            Color c = Color.RED;
            try {
                tX = Integer.parseInt(tileData[2]);
                tY = Integer.parseInt(tileData[3]);
                c = Color.valueOf(tileData[4]);
            }catch (Exception e){

            }
            return new TPTile(x, y, allowedMovementType, c, tX, tY);
        } else if (ID == 5) {
            Color c = Color.RED;
            try {
                c = Color.valueOf(tileData[2]);
            }catch (Exception e){}
            return new TPTargetTile(x, y, allowedMovementType, c);
        }
        return null;
    }

    public Texture getTextureObj() {
        return textureObj;
    }

    public void init(Level level){}

    public abstract Tile copy();

    public abstract String toStringFormat();

    public void setType(AllowedMovementType type) {
        this.movement = type;
    }

    public void setTextureObj(Texture textureObj) {
        this.textureObj = textureObj;
    }
}
