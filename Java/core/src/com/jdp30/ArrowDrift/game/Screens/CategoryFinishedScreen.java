package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;

/**
 * Created by Jack on 29/04/2018.
 */
public class CategoryFinishedScreen implements Screen {
    private Stage stage;

    public static String category;
    private String catName = "";

    public CategoryFinishedScreen() {
        stage = new Stage();


    }

    @Override
    public void show() {

        catName = category.split("/")[1].split(" ")[1];

        Gdx.input.setInputProcessor(stage);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        Label label = new Label("You have completed the " + catName + " category.", skin);
        label.setY(Gdx.graphics.getHeight() - label.getHeight() * 3);
        label.setX(Gdx.graphics.getWidth() / 2 - label.getWidth() / 2);
        stage.addActor(label);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        final TextButton newLevel = new TextButton("Start Next Category", skin);
        newLevel.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                String next = getNext();
                if (next == null) {
                    //TODO
                } else {
                    InGameScreen.lvl = next;
                    ArrowDriftGame.setCurrentScreen(new InGameScreen());
                }
            }
        });
        table.add(newLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton back = new TextButton("Back To Category Select", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                ArrowDriftGame.setCurrentScreen(new CategorySelectScreen());
            }
        });
        table.add(back).width(newLevel.getWidth() * 3).pad(20);
        table.row();
    }

    public String getNext() {
        int currentPos = Integer.parseInt(category.split("/")[1].split(" ")[0]);
        FileHandle handle = Gdx.files.internal("levels/");
        FileHandle[] children = handle.list();
        for (FileHandle h : children) {
            int i = Integer.parseInt(h.path().split("/")[1].split(" ")[0]);
            if (i == (currentPos + 1)) {
                return h.path() + "/levels/1.txt";
            }
        }
        return null;
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
