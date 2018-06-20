package com.jdp30.ArrowDrift.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.LevelEditor.EditorHomeScreen;
import com.jdp30.ArrowDrift.game.util.Util;
import storage.StorageSystem;

import java.io.IOException;

/**
 * Created by Jack Patrick on 03/04/2018.
 * <p/>
 * Last Edit: 03/04/2018
 */
public class SettingsScreen implements Screen {

    private Stage stage;

    public SettingsScreen() {
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        final Label title = new Label("Settings", skin);
        title.setPosition(Gdx.graphics.getWidth() / 2 - title.getWidth() / 2, Gdx.graphics.getHeight() - title.getHeight());
        stage.addActor(title);

        final TextButton newLevel = new TextButton("Set Resolution", skin);
        newLevel.getLabelCell().padBottom(5f).padTop(5f);
        newLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Util.dropdown("Select New Resolution", ArrowDriftGame.resolutions, stage, new Util.DialogResultListener() {
                    @Override
                    public void result(String text) {
                        System.out.println(text);
                        ArrowDriftGame.setCurrentResolution(text);
                        ArrowDriftGame.setCurrentScreen(new SettingsScreen());
                    }
                },ArrowDriftGame.getCurrentResolution()[0] + "x" + ArrowDriftGame.getCurrentResolution()[1]);
            }
        });
        table.add(newLevel).width(Gdx.graphics.getWidth() / 2).pad(20);
        table.row();

        final TextButton loadLevel = new TextButton("Clear User Data", skin);
        loadLevel.getLabelCell().padBottom(5f).padTop(5f);
        loadLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
            }
        });
        table.add(loadLevel).width(Gdx.graphics.getWidth() / 2).pad(20);
        table.row();
        table.row();
        table.row();
        table.row();
        final TextButton back = new TextButton("Back To Main Menu", skin);
        back.getLabelCell().padBottom(5f).padTop(5f);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrowDriftGame.setCurrentScreen(new MainMenuScreen());
            }
        });
        table.add(back).width(Gdx.graphics.getWidth() / 2).pad(20);


    }

    @Override
    public void show() {

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
        stage.getViewport().setScreenSize(width, height);
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

    }
}
