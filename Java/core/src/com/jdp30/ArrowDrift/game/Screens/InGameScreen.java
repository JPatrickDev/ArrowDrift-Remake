package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jdp30.ArrowDrift.game.GUI.Callback;
import com.jdp30.ArrowDrift.game.GUI.ImgButton;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class InGameScreen implements Screen {

    SpriteBatch batch;
    Texture img;
    Level level = null;

    int topPadding = 10;

    Stage ui = null;

    private ImgButton upDown = null, leftRight = null;

    @Override
    public void show() {
        batch = new SpriteBatch();
        //level = new Level(7,7);
        level = Level.load("level.txt");

        float wh = Gdx.graphics.getWidth() / 2;

        upDown = new ImgButton("button/up.png", (int) (wh / 2 - 128 / 2), (int) ((Gdx.graphics.getHeight() - (level.getHeight() * Tile.TILE_SIZE)) / 2 - 128 / 2));
        upDown.setCallback(new Callback() {

            @Override
            public void callback() {
                if (!level.p.isMoving()) {
                    AllowedMovementType t = level.getCurrentMovementType();
                    if (t.getUPDOWN() == 0) {
                        level.p.movedBy(0, 1, level);
                    } else if (t.getUPDOWN() == 1) {
                        level.p.movedBy(0, -1, level);
                    }
                }
            }
        });
        leftRight = new ImgButton("button/right.png", (int) (Gdx.graphics.getWidth() / 2 + ((wh / 2 - 128 / 2))), (int) ((Gdx.graphics.getHeight() - (level.getHeight() * Tile.TILE_SIZE)) / 2 - 128 / 2));
        leftRight.setCallback(new Callback() {

            @Override
            public void callback() {
                if (!level.p.isMoving()) {
                    AllowedMovementType t = level.getCurrentMovementType();
                    if (t.getLEFTRIGHT() == 0) {
                        level.p.movedBy(-1, 0, level);
                    } else if (t.getLEFTRIGHT() == 1) {
                        level.p.movedBy(1, 0, level);
                    }
                }
            }
        });


    }

    @Override
    public void render(float delta) {
        System.out.println(level.p.isMoving());

        update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        level.render(batch, Gdx.graphics.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, (Gdx.graphics.getHeight() - level.getHeight() * Tile.TILE_SIZE) - topPadding);
        upDown.draw(batch);
        leftRight.draw(batch);
        batch.end();

    }

    public void update() {
        AllowedMovementType t = level.getCurrentMovementType();
        if (t.getUPDOWN() == 0) {
            upDown.setTexture(new Texture("button/up.png"));
        } else if (t.getUPDOWN() == 1) {
            upDown.setTexture(new Texture("button/down.png"));
        }

        if (t.getLEFTRIGHT() == 0) {
            leftRight.setTexture(new Texture("button/left.png"));
        } else if (t.getLEFTRIGHT() == 1) {
            leftRight.setTexture(new Texture("button/right.png"));
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
}
