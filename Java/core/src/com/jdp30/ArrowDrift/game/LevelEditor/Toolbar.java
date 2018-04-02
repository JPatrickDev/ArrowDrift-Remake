package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;

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
        Label toolbarLabel = new Label("Toolbar", skin);
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

        this.setActor(table);
    }

    public Toolbar(Actor actor) {
        super(actor);
    }
}
