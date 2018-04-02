package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 02/04/2018.
 * <p>
 * Last Edit: 02/04/2018
 */
public class Toolbar extends Container<Actor> {

    private EditorMainScreen parent;
    ToolbarClickListener l;

    public Toolbar(EditorMainScreen parent, Skin skin, int x, int y, int w, int h) {
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
            Tile t = Tile.fromID(i, 0, 0, AllowedMovementType.fromID(4), new String[]{"0", "0", "0"});
            if (t == null)
                break;
            EditorTile tile = new EditorTile(t, 0, 0);
            tile.addListener(this.l);
            tiles.add(tile);
            if (i % 3 == 0 && i != 0)
                tiles.row();
        }
        final ScrollPane scroll = new ScrollPane(tiles, skin);
        table.add(scroll).expand().fill();
        this.setActor(table);
    }

    public void tileClicked(EditorTile tile) {
        parent.setCurrentTile(tile);
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
        parent.tileClicked((EditorTile) event.getListenerActor());
        super.clicked(event, x, y);
    }
}
