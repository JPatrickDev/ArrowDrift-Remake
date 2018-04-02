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

    public Toolbar(Skin skin, int x, int y, int w, int h) {
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
        for (int i = 1; i <= 30; i++) {
            tiles.add(new EditorTile(Tile.fromID(1, 0, 0, AllowedMovementType.fromID(4), new String[0]), 0, 0));
            if (i % 3 == 0 && i != 0)
                tiles.row();
        }
        for(Actor a : tiles.getChildren()){
            a.addListener(new ClickListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    System.out.println("Clicked" + event.getListenerActor());
                    return true; //the inputmultiplexer will stop trying to handle this touch
                }
            });
        }
        final ScrollPane scroll = new ScrollPane(tiles, skin);
        scroll.layout();
        table.add(scroll).expand().fill();
        scroll.layout();;
        this.setActor(table);
    }

    public Toolbar(Actor actor) {
        super(actor);
    }
}
