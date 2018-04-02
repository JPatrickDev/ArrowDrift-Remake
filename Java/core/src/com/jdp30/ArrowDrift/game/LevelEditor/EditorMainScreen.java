package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.Level;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorMainScreen implements Screen {

    private Stage stage;
    private EditorLevel level;
    private Toolbar bar;
    private Actor currentInHand;

    public EditorMainScreen(Level level) {
        stage = new Stage();
        this.level = EditorLevel.fromLevel(this, stage, level);
        Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));
        bar = new Toolbar(this, skin, (int) (Gdx.graphics.getWidth() - Gdx.graphics.getWidth() / 3), 0, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight());
        stage.addActor(bar);
        Gdx.input.setInputProcessor(stage);
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
        if (currentInHand != null) {
            SpriteBatch batch = new SpriteBatch();
            batch.begin();
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            currentInHand.setX(x);
            currentInHand.setY(y);
            currentInHand.draw(batch, 1f);
            batch.end();
        }
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

    public void setCurrentTile(EditorTile currentTile) {
        try {
            this.currentInHand = currentTile.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void setCurrentEntity(EntityContainer container){
        this.currentInHand = container;
    }

    public Actor getCurrentInHand() {
        return currentInHand;
    }

    public void addEntityToLevel(Entity e) {
        level.addEntity(e);
    }
}
