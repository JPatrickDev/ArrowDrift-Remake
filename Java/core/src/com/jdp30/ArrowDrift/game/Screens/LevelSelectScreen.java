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

/**
 * Created by Jack on 20/04/2018.
 */
public class LevelSelectScreen implements Screen {
    private Stage stage;
    private String folderPath;

    public LevelSelectScreen(String folderPath) {
        stage = new Stage();
        this.folderPath = folderPath;
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Label label = new Label("Select A Level", skin);
        label.setY(Gdx.graphics.getHeight() - label.getHeight() * 3);
        label.setX(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2);
        stage.addActor(label);
        FileHandle handle = Gdx.files.internal(folderPath);
        FileHandle[] children = handle.list();
        int x = 0;
        int y = 0;
        int xP = 0;
        float w = (Gdx.graphics.getWidth()) / 3.0f;
        float h = w;
        y = (int) h;
        for (final FileHandle f : children) {
            LevelSelectionActor a = new LevelSelectionActor(0, f.path(), f.path().split("/")[f.path().split("/").length - 1].replace(".txt",""));
            a.setWidth(w);
            a.setHeight(w);
            a.setX(x);
            a.setY(y);
            a.init();
            stage.addActor(a);
            a.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    super.clicked(event, x, y);
                   // ((LevelSelectionActor)event.getListenerActor()).recaulcuateFont();
                    InGameScreen.lvl = f.path();
                    ArrowDriftGame.setCurrentScreen(new InGameScreen());
                }
            });
            x += a.getWidth();
            xP++;
            if (xP >= 4) {
                xP = 0;
                x = 0;
                y += h;
            }
        }

        TextButton button = new TextButton("Back To Main Menu", skin);

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
