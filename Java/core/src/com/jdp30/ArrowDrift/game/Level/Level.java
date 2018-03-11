package com.jdp30.ArrowDrift.game.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.utils.Disposable;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Entity.Player;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p>
 * Last Edit: 05/03/2018
 */
public class Level implements Disposable {
    private int w, h;
    private Tile[][] tiles;

    public Player p;
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        this.tiles = new Tile[w][h];
     /*   for (int x = 0; x != w; x++) {
            for (int y = 0; y != h; y++) {
                tiles[x][y] = new Floor(x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
            }
        }

        for (int x = 0; x != w; x++) {
            tiles[x][0] = new Wall(x * Tile.TILE_SIZE, 0);
            tiles[x][h - 1] = new Wall(x * Tile.TILE_SIZE, (h - 1) * Tile.TILE_SIZE);
        }

        for (int y = 0; y != h; y++) {
            tiles[0][y] = new Wall(0, y * Tile.TILE_SIZE);
            tiles[w - 1][y] = new Wall((w - 1) * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
        }
*/
        //p = new Player(1, 1);
    }


    public void render(SpriteBatch batch, int xo, int yo) {
        for (Tile[] tt : tiles)
            for (Tile t : tt) {
                if (t != null)
                    t.draw(batch, xo, yo);
            }

        for (Entity e : entities) {
            e.update(this);
        }
        p.update(this);
        for (Entity e : entities) {
            e.draw(batch, xo, yo);
        }
        p.draw(batch, xo, yo);
    }

    public int getWidth() {
        return w;
    }

    public int getHeight() {
        return h;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public boolean canMoveTo(int x, int y) {
        if (getTile(x, y).isSolid())
            return false;
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y)
                return false;
        }
        return true;
    }

    @Override
    public void dispose() {
        for (Tile[] t : tiles) {
            for (Tile tt : t)
                tt.dispose();
        }
        p.dispose();
    }

    public void walkedInTo(int x, int y, Direction from, Entity entity) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                e.walkedInTo(from, this);
                return;
            }
        }
        getTile(x, y).walkedInTo(entity, from);
    }

    public AllowedMovementType getCurrentMovementType() {
        return getTile(p.getX(), p.getY()).getMovementType();
    }

    public static Level load(String file) {
        FileHandle handle = Gdx.files.internal(file);
        String text = handle.readString();
        String wordsArray[] = text.split("\\r?\\n");
        int width = Integer.parseInt(wordsArray[0]);
        int height = Integer.parseInt(wordsArray[1]);
        Level level = new Level(width, height);
        String[] spawnInfo = wordsArray[2].split(" ");

        int spawnX = Integer.parseInt(spawnInfo[0]);
        int spawnY = Integer.parseInt(spawnInfo[1]);
        level.addEntity(new Player(spawnX, spawnY));

        int x = 0;
        int y = 0;
        for (int i = 2 + height; i != 2; i--) {
            String row = wordsArray[i];
            String[] tiles = row.split(":");
            for (String t : tiles) {
                String[] tileData = t.split(",");
                Tile tile = Tile.fromID(Integer.parseInt(tileData[0]), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, AllowedMovementType.fromID(Integer.parseInt(tileData[1])),tileData);
                if (tile != null) {
                    level.setTileAt(x, y, tile);
                } else {
                    System.out.println(t);
                    System.out.println("Invalid tile ID");
                }
                x++;
            }
            x = 0;
            y++;
        }
        if(wordsArray.length > (3 + height)){
            System.out.println("More data found");
            for(int i = 3 + height; i != (wordsArray.length);i++){
                String row = wordsArray[i];
                String[] rowData = row.split(":");
                String[] coords = rowData[1].split(",");
                System.out.println(row);
                try {
                    Class<? extends Entity> e = Class.forName("com.jdp30.ArrowDrift.game.Entity." + rowData[0]).asSubclass(Entity.class);
                    Entity en = e.getConstructor(int.class,int.class).newInstance(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
                    level.addEntity(en);
                } catch (ClassNotFoundException e1) {
                    e1.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return level;
    }

    private void setTileAt(int x, int y, Tile tile) {
        tiles[x][y] = tile;
    }

    private void addEntity(Entity entity) {
        if (entity instanceof Player)
            p = (Player) entity;
        else
            entities.add(entity);
    }
}
