package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
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

    TextButton button;

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Label label = new Label("Select A Level Category", skin);
        label.setY(Gdx.graphics.getHeight() - label.getHeight());
        label.setX(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2);
        stage.addActor(label);


        button = new TextButton("Back To Main Menu", skin);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrowDriftGame.setCurrentScreen(new MainMenuScreen());
            }
        });
        button.setY(0);
        button.setX(Gdx.graphics.getWidth() / 2 - button.getWidth() / 2);

        stage.addActor(button);



        try {
            StorageSystem mappack = StorageSystem.fromFile("Arrow Drift Data/Levels/DEFAULT");
            ArrowDriftGame.setCurrentPack(mappack);
            portrait(mappack);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void portrait(StorageSystem mappack) {
        Rectangle area = new Rectangle(0, button.getY() + button.getHeight(), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - button.getHeight() * 2);
        int y = (int) area.getY();
        for (final Node n : mappack.getRoot().getChildren()) {
            if (n.getName().equals("metadata")) continue;
            LevelTypeSelectionActor a = new LevelTypeSelectionActor(n.getName(), n);
            a.setWidth(Gdx.graphics.getWidth() - 20);
            a.setHeight(area.getHeight() / (mappack.getRoot().getChildren().size()-1));
            a.setX(Gdx.graphics.getWidth() / 2 - a.getWidth() / 2);
            a.setY(y);
            a.init();
            stage.addActor(a);
            a.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                    Node selectedCat = ArrowDriftGame.getCurrentPack().getRoot().getChild(n.getName());
                    ArrowDriftGame.setCurrentScreen(new LevelSelectScreen(selectedCat));
                }
            });
            y += a.getHeight();
        }
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
