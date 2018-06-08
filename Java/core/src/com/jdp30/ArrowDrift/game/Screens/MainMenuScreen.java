package com.jdp30.ArrowDrift.game.Screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.LevelEditor.EditorHomeScreen;
import storage.StorageSystem;

import java.io.IOException;

/**
 * Created by Jack Patrick on 03/04/2018.
 * <p>
 * Last Edit: 03/04/2018
 */
public class MainMenuScreen implements Screen {

    private Stage stage;
    private boolean firstRun = true;
    public MainMenuScreen() {
        this.stage = new Stage();

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        final TextButton newLevel = new TextButton("Play", skin);
        newLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrowDriftGame.setCurrentScreen(new CategorySelectScreen());
            }
        });
        table.add(newLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton loadLevel = new TextButton("Level Editor", skin);
        loadLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrowDriftGame.setCurrentScreen(new EditorHomeScreen());
            }
        });
        table.add(loadLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton settings = new TextButton("Settings", skin);
        table.add(settings).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton about = new TextButton("About", skin);
        table.add(about).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final Label packLabel = new Label("Current Pack:" + ArrowDriftGame.getCurrentPack().getRoot().getName(), skin);

        String[] packs =  ArrowDriftGame.getPacks();

        final SelectBox<String> mappacks = new SelectBox<String>(skin);

        mappacks.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(firstRun){
                    firstRun = false;
                    return;
                }
                String item = mappacks.getSelected();
                try {
                    StorageSystem s = StorageSystem.fromFile("Arrow Drift Data/Levels/" + item);
                    ArrowDriftGame.setCurrentPack(s);
                    packLabel.setText("Current Pack:" + s.getRoot().getName());
                    packLabel.setX(Gdx.graphics.getWidth() - packLabel.getWidth() - 20);
                    mappacks.setX(packLabel.getX() - mappacks.getWidth());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        packLabel.setX(Gdx.graphics.getWidth() - packLabel.getWidth() - 20);
        packLabel.setY(mappacks.getHeight() / 2 - packLabel.getHeight() / 2);
        mappacks.setItems(packs);
        mappacks.setX(packLabel.getX() - mappacks.getWidth());
        mappacks.setSelected(ArrowDriftGame.getCurrentPack().getRoot().getName());
        stage.addActor(packLabel);
        stage.addActor(mappacks);
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
