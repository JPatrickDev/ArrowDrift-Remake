package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.Entity.Box;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Entity.Player;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.Level.Tile.Wall;
import com.jdp30.ArrowDrift.game.util.Util;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Jack Patrick on 02/04/2018.
 * <p>
 * Last Edit: 02/04/2018
 */
public class Toolbar extends Container<Actor> {

    private EditorMainScreen parent;
    ToolbarClickListener l;

    public Toolbar(final EditorMainScreen parent, Skin skin, int x, int y, int w, int h) {
        this.parent = parent;
        this.l = new ToolbarClickListener(this);
        setX(x);
        setY(y);
        setWidth(w);
        setHeight(h);
        Label toolbarLabel = new Label("Tiles", skin);
        Table table = new Table();
        table.setDebug(true);
        table.setX(0);
        table.setY(0);
        table.setWidth(this.getWidth());
        table.setHeight(this.getHeight());
        table.left().top();

        table.add(toolbarLabel).expandX();
        table.row();
        table.add(toolbarLabel).expandX();

        Table tiles = new Table();
        table.row();
        for (int i = 0; i <= 500; i++) {
            Tile t = null;
            if (i == 5) {
                t = Tile.fromID(i, 0, 0, AllowedMovementType.fromID(0), new String[]{"0", "0", "#FFFF00"});
            } else {
                t = Tile.fromID(i, 0, 0, AllowedMovementType.fromID(0), new String[]{"0", "0", "0", "0", "#FFFF00"});
                if (t instanceof Wall) {
                    t = Tile.fromID(i, 0, 0, AllowedMovementType.fromID(4), new String[]{"0", "0", "0"});
                }
            }
            if (t == null)
                break;
            EditorTile tile = new EditorTile(t, 0, 0);
            tile.addListener(this.l);
            tiles.add(tile);
            if (i % 2 == 0 && i != 0)
                tiles.row();
        }
        final ScrollPane scroll = new ScrollPane(tiles, skin);
        table.add(scroll).expand().fill();
        table.row();
        Label entities = new Label("Entities", skin);
        table.add(entities);
        table.row();

        Table entitiesTable = new Table();
        Player player = new Player(0, 0);
        EntityContainer playerContainer = new EntityContainer(player);
        playerContainer.addListener(l);
        entitiesTable.add(playerContainer);
        Box box = new Box(0, 0);
        EntityContainer boxContainer = new EntityContainer(box);
        boxContainer.addListener(l);
        entitiesTable.add(boxContainer);
        table.add(entitiesTable);

        table.row();
        TextButton b = new TextButton("Save Level", skin);
        b.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    parent.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        table.add(b);
        table.row();
        TextButton backToMenu = new TextButton("Back To Editor Home", skin);
        backToMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrowDriftGame.setCurrentScreen(new EditorHomeScreen());
            }
        });
        table.add(backToMenu);
        table.row();
        TextButton setMin = new TextButton("Set minimum moves", skin);
        setMin.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               // ArrowDriftGame.setCurrentScreen(new EditorHomeScreen());
                Util.input("Minimum moves:",getStage(), new Util.DialogResultListener() {
                    @Override
                    public void result(String text) {
                        parent.setMinMoves(Integer.parseInt(text));
                    }
                });
            }
        });
        table.add(setMin);
        this.setActor(table);
    }

    public void tileClicked(EditorTile tile) {
        parent.setCurrentTile(tile);
    }

    public void addEntity(Entity e) {
        parent.addEntityToLevel(e);
    }

    public void entityToolbarClicked(EntityContainer container) {
        this.parent.setCurrentEntity(container.copy());
    }
}

class ToolbarClickListener extends ClickListener {
    private Toolbar parent;

    public ToolbarClickListener(Toolbar parent) {
        this.parent = parent;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println("Clicked" + event.getListenerActor());
        if (event.getListenerActor() instanceof EditorTile)
            parent.tileClicked((EditorTile) event.getListenerActor());
        if (event.getListenerActor() instanceof EntityContainer) {
            EntityContainer container = (EntityContainer) event.getListenerActor();
            parent.entityToolbarClicked(container);

        }

        super.clicked(event, x, y);
    }
}
