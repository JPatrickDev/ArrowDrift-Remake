package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.TPTargetTile;
import com.jdp30.ArrowDrift.game.Level.Tile.TPTile;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.util.Util;


import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorLevel extends ClickListener {

    private int width, height;
    private EditorTile[][] tiles = null;
    Stage stage;
    private EditorMainScreen parent;
    private RightListener listener;
    public ArrayList<EntityContainer> entities = new ArrayList<EntityContainer>();

    public EditorLevel(EditorMainScreen parent, Stage stage, int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new EditorTile[width][height];
        this.stage = stage;
        this.parent = parent;
        this.listener = new RightListener(parent);
    }

    public void setTiles(EditorTile[][] tiles) {
        this.tiles = tiles;
        for (EditorTile[] tt : tiles) {
            for (EditorTile t : tt) {
                t.addListener(this);
                t.addListener(listener);
                stage.addActor(t);
            }
        }
    }

    public static EditorLevel fromLevel(EditorMainScreen main, Stage stage, Level level) {
        EditorTile[][] tiles = new EditorTile[level.getWidth()][level.getHeight()];
        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                tiles[x][y] = new EditorTile(level.getTile(x, y), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
            }
        }
        EditorLevel l = new EditorLevel(main, stage, level.getWidth(), level.getHeight());

        l.setTiles(tiles);

        for (Entity e : level.getEntities()) {
            l.addEntity(e);
        }
        if(level.p != null){
            l.addEntity(level.p);
        }



        return l;
    }

    boolean waitingForTPTargetClick = false;
    EditorTile tpPos = null;
    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (event.getListenerActor() instanceof EntityContainer) {
            removeEntityAt((int) event.getListenerActor().getX(), (int) event.getListenerActor().getY());
            return;
        }
        EditorTile t = (EditorTile) event.getListenerActor();
        if (parent.getCurrentInHand() != null) {
            System.out.println(event.getButton());
            if (event.getButton() == 0) {
                removeEntityAt((int) t.getX(), (int) t.getY());
                if (parent.getCurrentInHand() instanceof EditorTile) {
                    t.setTile(((EditorTile) parent.getCurrentInHand()).getParentTile());
                    if(((EditorTile) parent.getCurrentInHand()).getParentTile() instanceof TPTile) {
                        waitingForTPTargetClick = true;
                        this.tpPos = t;
                        parent.setCurrentTile(null);
                    }
                }
                if (parent.getCurrentInHand() instanceof EntityContainer) {
                    Entity e = ((EntityContainer) parent.getCurrentInHand()).getEntity();
                    e.setPos((int) (t.getX() / Tile.TILE_SIZE), (int) (t.getY() / Tile.TILE_SIZE));
                    addEntity(e);
                }
            }
        }else{
            if(waitingForTPTargetClick){
                String hex = Util.randHex();
                Color c = Color.valueOf(hex);
                TPTargetTile targetTile = new TPTargetTile((int) (t.getX() / Tile.TILE_SIZE), (int) (t.getY() / Tile.TILE_SIZE), AllowedMovementType.UP_RIGHT,c);
                TPTile tpTile = new TPTile((int) (tpPos.getX() / Tile.TILE_SIZE), (int) (tpPos.getY() / Tile.TILE_SIZE),AllowedMovementType.NONE,c,targetTile.x,targetTile.y);
                tpPos.setTile(tpTile);
                t.setTile(targetTile);
                waitingForTPTargetClick = false;
                return;
            }
        }
    }

    public void addEntity(Entity e) {
        EntityContainer c = new EntityContainer(e);
        c.addListener(listener);
        stage.addActor(c);
        entities.add(c);
        System.out.println("Adding entity");
    }

    public void removeEntityAt(int x, int y) {
        EntityContainer remove = null;
        for (EntityContainer c : entities) {
            if (c.getX() == x && c.getY() == y) {
                remove = c;
                break;
            }
        }
        if (remove != null) {
            entities.remove(remove);
            remove.remove();
        }
    }

    public Level toLevel() {
        Level level = new Level(width, height);
        for (int x = 0; x != width; x++) {
            for (int y = 0; y != height; y++) {
                EditorTile tile = tiles[x][y];
                level.setTileAt(x, y, tile.getParentTile().copy());
            }
        }
        for (EntityContainer c : entities) {
            Entity e = c.getEntity().copy();
            e.setPos(((int) c.getX() / Tile.TILE_SIZE), ((int) c.getY() / Tile.TILE_SIZE));
            level.addEntity(e);
        }
        return level;
    }

    public void setEntities(ArrayList<EntityContainer> entities) {
        this.entities = entities;
    }
}

class RightListener extends ClickListener {
    private EditorMainScreen parent;

    public RightListener(EditorMainScreen parent) {
        super(Input.Buttons.RIGHT);
        this.parent = parent;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        if (event.getListenerActor() instanceof EntityContainer) {
            parent.getLevel().removeEntityAt((int) event.getListenerActor().getX(), (int) event.getListenerActor().getY());
            return;
        }
        EditorTile t = (EditorTile) event.getListenerActor();
        System.out.println(event.getButton());
        t.rightClicked();

    }
}
