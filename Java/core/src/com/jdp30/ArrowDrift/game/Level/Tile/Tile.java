package com.jdp30.ArrowDrift.game.Level.Tile;

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
 * <p>
 * Last Edit: 05/03/2018
 */
public class Tile implements Disposable {

    public int x;
    public int y;
    private boolean solid;
    public String texture;
    public static int TILE_SIZE = 64;
    public Texture textureObj;

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
        batch.draw(textureObj, x + xo, y + yo);
        if (movement != AllowedMovementType.NONE) {
            Texture t = movementTextures.get(movement);
            batch.draw(t, x + xo, y + yo);
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

    //1 = WALL
    //2 = Belt
    //0 = FLOOR
    public static Tile fromID(int ID, int x, int y, AllowedMovementType allowedMovementType) {
        if (ID == 1) {
            return new Wall(x, y, allowedMovementType);
        } else if (ID == 0) {
            return new Floor(x, y, allowedMovementType);
        } else if (ID == 2) {
            return new BeltTile(x, y,Direction.UP);
        }
        return null;
    }
}
