package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.GUI.LevelSelectionActor;
import com.jdp30.ArrowDrift.game.GUI.LevelTypeSelectionActor;
import storage.Node;
import storage.StorageSystem;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jack Patrick on 03/04/2018.
 * <p>
 * Last Edit: 03/04/2018
 */
public class CategorySelectScreen implements Screen {
    private Stage stage;

    public CategorySelectScreen() {
        stage = new Stage();


    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Label label = new Label("Select A Level Category", skin);
        label.setY(Gdx.graphics.getHeight() - label.getHeight() * 3);
        label.setX(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2);
        stage.addActor(label);

        try {
            StorageSystem mappack = StorageSystem.fromFile("Arrow Drift Data/Levels/DEFAULT");
            ArrowDriftGame.setCurrentPack(mappack);
        int x = 0;
        float w = (800.0f) / ((float) (mappack.getRoot().getChildren().size()));
        for (final Node n  : mappack.getRoot().getChildren()) {
            if(n.getName().equals("metadata"))continue;
            LevelTypeSelectionActor a = new LevelTypeSelectionActor(n.getName());
            a.setWidth(w);
            a.setHeight(w);
            a.setX(x);
            a.setY(Gdx.graphics.getHeight() / 2 - w / 2);
            a.init();
            stage.addActor(a);
            a.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Node selectedCat = ArrowDriftGame.getCurrentPack().getRoot().getChild(n.getName());
                    ArrowDriftGame.setCurrentScreen(new LevelSelectScreen(selectedCat));
                }
            });
            x += a.getWidth();
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextButton button = new TextButton("Back To Main Menu",skin);

        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event,x,y);
                ArrowDriftGame.setCurrentScreen(new MainMenuScreen());
            }
        });
        button.setY(label.getHeight() * 3);
        button.setX(Gdx.graphics.getWidth()/2 - button.getWidth()/2);

        stage.addActor(button);

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
        stage.getViewport().setScreenSize(width,height);
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
