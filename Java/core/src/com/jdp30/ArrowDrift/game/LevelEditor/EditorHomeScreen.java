package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;
import javax.swing.*;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorHomeScreen implements Screen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin(Gdx.files.internal("ui/skin.json"));


        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        final TextButton newLevel = new TextButton("New Level", skin);

        newLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newLevel();
            }
        });

        table.add(newLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton loadLevel = new TextButton("Load Level", skin);
        table.add(loadLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton back = new TextButton("Back To Main Menu", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ArrowDriftGame.setCurrentScreen(new MainMenuScreen());
            }
        });
        table.add(back).width(newLevel.getWidth() * 3).pad(20);
        table.row();
    }

    public void newLevel() {
        int width = Integer.parseInt(JOptionPane.showInputDialog(null, "Width"));
        int height = Integer.parseInt(JOptionPane.showInputDialog(null, "Height"));
        startEditor(Level.blank(width, height));
    }

    public void startEditor(Level level) {
        System.out.println("starting editor");
        ArrowDriftGame.setCurrentScreen(new EditorMainScreen(level));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}
