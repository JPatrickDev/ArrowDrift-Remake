package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.GUI.Callback;
import com.jdp30.ArrowDrift.game.GUI.ImgButton;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.util.LevelUtil;
import javafx.scene.paint.Color;
import storage.Node;
import sun.awt.geom.AreaOp;
import sun.plugin.dom.exception.InvalidStateException;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p/>
 * Last Edit: 11/03/2018
 */
public class InGameScreen implements Screen {

    SpriteBatch batch;
    Texture img;
    Level level = null;

    int levelPos = 0;

    int topPadding = 10;

    Stage ui = null;

    private ImgButton upDown = null, leftRight = null;
    private BitmapFont font;
    public static Node lvl = null;

    @Override
    public void show() {
        if (lvl == null) {
            throw new InvalidStateException("Level can't be null");
        }

        font = new BitmapFont(Gdx.files.internal("fonts/cg40.fnt"), Gdx.files.internal("fonts/cg40.png"), false);

        batch = new SpriteBatch();

        level = Level.fromNode(lvl);

        float wh = Gdx.graphics.getWidth() / 2;

        upDown = new ImgButton("button/up.png", (int) (wh / 2 - 128 / 2), (int) ((Gdx.graphics.getHeight() - (level.getHeight() * Tile.TILE_SIZE)) / 2 - 128 / 2));
        upDown.setCallback(new Callback() {

            @Override
            public void callback() {
                if (!level.p.isMoving()) {
                    AllowedMovementType t = level.getCurrentMovementType();
                    if (t.getUPDOWN() == 0) {
                        level.p.movedBy(0, 1, level);
                        if (level.p.isMoving()) {
                            level.moves++;
                        }
                    } else if (t.getUPDOWN() == 1) {
                        level.p.movedBy(0, -1, level);
                        if (level.p.isMoving()) {
                            level.moves++;
                        }
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
                        if (level.p.isMoving()) {
                            level.moves++;
                        }
                    } else if (t.getLEFTRIGHT() == 1) {
                        level.p.movedBy(1, 0, level);
                        if (level.p.isMoving()) {
                            level.moves++;
                        }
                    }
                }
            }
        });
    }

    public Node getNextLevel() {
        Node currentCatNode = ArrowDriftGame.getCurrentPack().getRoot().getChild(ArrowDriftGame.currentCat);
        int i = Integer.parseInt(lvl.getName()) + 1;
        Node next = currentCatNode.getChild(i + "");
        return next;
    }

    GlyphLayout layout = new GlyphLayout();

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        if (level != null)
            level.render(batch, Gdx.graphics.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, (Gdx.graphics.getHeight() - level.getHeight() * Tile.TILE_SIZE) - topPadding);
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        layout.setText(font, "Moves: " + level.moves);
        font.draw(batch, "Moves: " + level.moves, Gdx.graphics.getWidth() / 2 - (layout.width) / 2, (Gdx.graphics.getHeight() - level.getHeight() * Tile.TILE_SIZE) - topPadding - topPadding);
        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        upDown.draw(batch);
        leftRight.draw(batch);
        batch.end();
    }

    public void update() {
        if (level == null) return;
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

        if (level != null) {
            if (level.isOver()) {
                LevelUtil.updateMoves(lvl.getName(),ArrowDriftGame.currentCat, level.moves);
                if (getNextLevel() == null) {
                    CategoryFinishedScreen.category = ArrowDriftGame.getCurrentPack().getRoot().getChild(ArrowDriftGame.currentCat);
                    ArrowDriftGame.setCurrentScreen(new CategoryFinishedScreen());
                    return;
                }
                lvl = getNextLevel();
                level = Level.fromNode(lvl);
            }
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
