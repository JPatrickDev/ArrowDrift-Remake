package com.jdp30.ArrowDrift.game.Level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.IndexArray;
import com.badlogic.gdx.utils.Disposable;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Entity.Player;
import com.jdp30.ArrowDrift.game.Level.Tile.GoalTile;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.util.Util;
import storage.Node;
import storage.StorageSystem;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Jack Patrick on 05/03/2018.
 * <p/>
 * Last Edit: 05/03/2018
 */
public class Level implements Disposable {
    private int w, h;
    private Tile[][] tiles;

    private int minMoves = 0;
    public Player p;
    private ArrayList<Entity> entities = new ArrayList<Entity>();

    private int endX, endY;

    private boolean won = false;
    public int moves = 0;

    public Level(int w, int h) {
        this.w = w;
        this.h = h;
        this.tiles = new Tile[w][h];
        won = false;
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

        if (p.getX() == endX && p.getY() == endY) {
            won = true;
        }
    }

    public int getMinMoves() {
        return minMoves;
    }

    public void setMinMoves(int minMoves) {
        this.minMoves = minMoves;
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
        //TODO Check for player, unless the player has called the function
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


    public static Level fromNode(Node node) {
        int width = Integer.parseInt(node.getValue("width"));
        int height = Integer.parseInt(node.getValue("height"));

        Level level = new Level(width, height);

        String levelName = node.getValue("name");

        int minMoves = Integer.parseInt(node.getValue("minMoves"));
        String tileInfo = node.getValue("tiles");
        String entityData = node.getValue("entities");

        level.setMinMoves(minMoves);
        String[] tileRows = tileInfo.split("#");
        tileRows = Util.reverse(tileRows);
        int x = 0;
        int y = 0;
        for (String s : tileRows) {
            for (String t : s.split(":")) {
                String[] tileData = t.split(",");
                Tile tile  = Tile.fromID(Integer.parseInt(tileData[0]), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, AllowedMovementType.fromID(Integer.parseInt(tileData[1])), tileData);
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
        if (node.getValue("spawn") != null) {
            String[] spawnData = node.getValue("spawn").split(" ");
            level.addEntity(new Player(Integer.parseInt(spawnData[0]), Integer.parseInt(spawnData[1])));
        }
        if (entityData.equals(""))
            return level;

        for (String row : entityData.split("#")) {
            String[] rowData = row.split(":");
            String[] coords = rowData[1].split(",");
            System.out.println(row);
            try {
                Class<? extends Entity> e = Class.forName("com.jdp30.ArrowDrift.game.Entity." + rowData[0]).asSubclass(Entity.class);
                Entity en = e.getConstructor(int.class, int.class).newInstance(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
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
        return level;
    }

    public Node toNode(String name) {
        Node rootNode = new Node(name);
        rootNode.addValue("width", this.w + "");
        rootNode.addValue("height", this.h + "");
        rootNode.addValue("name", name);
        if (p != null)
            rootNode.addValue("spawn", p.getX() + " " + p.getY());
        rootNode.addValue("minMoves", getMinMoves() + "");
        String tileData = "";
        for (int y = getHeight() - 1; y >= 0; y--) {
            String line = "";
            for (int x = 0; x != getWidth(); x++) {
                line += ":" + tiles[x][y].toStringFormat();
            }
            line = line.replaceFirst(":", "");
            tileData += "#" + line;
        }
        tileData = tileData.replaceFirst("#", "");
        rootNode.addValue("tiles", tileData);

        String entityData = "";
        for (Entity e : entities) {
            entityData += "#" + e.getClass().getSimpleName() + ":" + e.getX() + "," + e.getY();
        }
        entityData = entityData.replaceFirst("#", "");
        rootNode.addValue("entities", entityData);
        return rootNode;
    }


    public static Level blank(int width, int height) {
        Level level = new Level(width, height);
        for (int y = 0; y != height; y++) {
            for (int x = 0; x != width; x++) {
                Tile tile = Tile.fromID(1, x * Tile.TILE_SIZE, y * Tile.TILE_SIZE, AllowedMovementType.fromID(4), new String[0]);
                if (tile != null) {
                    level.setTileAt(x, y, tile);
                } else {
                    System.out.println("Invalid tile ID");
                }
            }
        }
        return level;
    }

    public void setTileAt(int x, int y, Tile tile) {
        if (tile instanceof GoalTile) {
            this.endX = x;
            this.endY = y;
        }
        tiles[x][y] = tile;
    }

    public void addEntity(Entity entity) {
        if (entity instanceof Player)
            p = (Player) entity;
        else
            entities.add(entity);
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean isOver() {
        return won;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }
}
