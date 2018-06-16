package com.jdp30.ArrowDrift.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.GUI.ImgButton;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.util.LevelUtil;
import com.jdp30.ArrowDrift.game.util.Util;
import storage.Node;
import sun.plugin.dom.exception.InvalidStateException;


/**
 * Created by Jack Patrick on 11/03/2018.
 * <p/>
 * Last Edit: 11/03/2018
 */
public class InGameScreen implements Screen {

    private SpriteBatch batch;
    private ShapeRenderer shape;
    private Level level = null;


    private int topPadding = 10;

    private BitmapFont font;
    public static Node lvl = null;

    private Rectangle gameArea, uiArea, infoArea, leftButton, rightButton;

    private Stage stage;

    private ImgButton upDown, leftRight;

    @Override
    public void show() {


        final Skin skin = new Skin(Gdx.files.internal("ui/skin.json"));

        if (lvl == null) {
            throw new InvalidStateException("Level can't be null");
        }

        int buttonPadding = 50;
        if (ArrowDriftGame.isPortrait())
            setPortraitRects();
        else {
            setLandscapeRects();
            buttonPadding = 25;
        }
        preLevel();


        font = new BitmapFont(Gdx.files.internal("fonts/cg40.fnt"), Gdx.files.internal("fonts/cg40.png"), false);

        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        shape.setAutoShapeType(true);

        level = Level.fromNode(lvl);


        stage = new Stage();
        stage.getViewport().setScreenPosition((int) uiArea.x, (int) uiArea.y);
        stage.getViewport().setScreenSize((int) uiArea.getWidth(), (int) uiArea.getHeight());


        int i = (int) uiArea.getWidth();
        if (uiArea.getHeight() < i)
            i = (int) uiArea.getHeight() * 2;
        int buttonWidth = (int) ((i / 2)) - 2 * buttonPadding;


        upDown = new ImgButton("button/up.png", (int) (leftButton.getX() + (leftButton.getWidth() / 2 - buttonWidth / 2)), (int) (leftButton.getY() + (leftButton.getHeight() / 2 - buttonWidth / 2)), buttonWidth, buttonWidth);
        stage.addActor(upDown);
        leftRight = new ImgButton("button/up.png", (int) (rightButton.getX() + (rightButton.getWidth() / 2 - buttonWidth / 2)), (int) (rightButton.getY() + (rightButton.getHeight() / 2 - buttonWidth / 2)), buttonWidth, buttonWidth);
        stage.addActor(leftRight);
        Gdx.input.setInputProcessor(stage);
        leftRight.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!level.p.isMoving()) {
                    AllowedMovementType t = level.getCurrentMovementType();
                    if (t.getLEFTRIGHT() == 0) {
                        level.p.moveLeft(level);
                    } else if (t.getLEFTRIGHT() == 1) {
                        level.p.moveRight(level);
                    }
                }
            }
        });
        upDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                if (!level.p.isMoving()) {
                    AllowedMovementType t = level.getCurrentMovementType();
                    if (t.getUPDOWN() == 0) {
                        level.p.moveUp(level);
                    } else if (t.getUPDOWN() == 1) {
                        level.p.moveDown(level);
                    }
                }
            }
        });

        final TextButton menu = new TextButton("Options", skin);
        menu.setSize(infoArea.getWidth() / 2, infoArea.getHeight() / 3);
        menu.setPosition(infoArea.getX() + (infoArea.getWidth() / 2 - menu.getWidth() / 2), infoArea.getY() + topPadding);
        menu.addListener(new ClickListener() {

            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                Util.menu("Menu", new String[]{"Back To Main Menu", "Settings"}, null, stage);
            }
        });
        stage.addActor(menu);

    }

    public void setPortraitRects() {
        float third = Gdx.graphics.getHeight() / 3.0f;
        float sixth = third / 2.0f;
        float seventh = Gdx.graphics.getHeight() / 7.0f;
        uiArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), seventh * 2);
        infoArea = new Rectangle(0, uiArea.getHeight(), Gdx.graphics.getWidth(), sixth);
        gameArea = new Rectangle(0, (uiArea.getHeight() + infoArea.getHeight()), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - (uiArea.getHeight() + infoArea.getHeight()));
        leftButton = new Rectangle(uiArea.getX(), uiArea.getY(), uiArea.getWidth() / 2, uiArea.getHeight());
        rightButton = new Rectangle(uiArea.getX() + leftButton.getWidth(), uiArea.getY(), uiArea.getWidth() / 2, uiArea.getHeight());
    }

    public void setLandscapeRects() {
        float third = Gdx.graphics.getHeight() / 3.0f;
        float sixth = third / 2.0f;
        float seventh = Gdx.graphics.getHeight() / 7.0f;
        uiArea = new Rectangle(0, 0, Gdx.graphics.getWidth(), seventh * 2);
        infoArea = new Rectangle(third, 0, Gdx.graphics.getWidth() - third * 2, uiArea.getHeight());
        leftButton = new Rectangle(uiArea.getX(), uiArea.getY(), third, uiArea.getHeight());
        rightButton = new Rectangle(uiArea.getX() + uiArea.getWidth() - third, uiArea.getY(), third, uiArea.getHeight());

        gameArea = new Rectangle(0, (uiArea.getHeight()), Gdx.graphics.getWidth(), Gdx.graphics.getHeight() - (uiArea.getHeight()));
    }

    public Node getNextLevel() {
        Node currentCatNode = ArrowDriftGame.getCurrentPack().getRoot().getChild(ArrowDriftGame.currentCat);
        int i = Integer.parseInt(lvl.getName()) + 1;
        Node next = currentCatNode.getChild(i + "");
        return next;
    }


    public void preLevel() {
        int levelWidth = Integer.parseInt(lvl.getValue("width"));
        int levelHeight = Integer.parseInt(lvl.getValue("height"));

        int maxWidth = (int) (gameArea.getWidth() - (2 * topPadding));
        int maxHeight = (int) (gameArea.getHeight() - (2 * topPadding));
        int currentSize = 8;
        for (int i = 8; i <= 512; i += 2) {
            System.out.println(i);
            int tSize = i;
            int width = tSize * levelWidth;
            int height = tSize * levelHeight;
            if (width <= maxWidth && height <= maxHeight) {
                currentSize = i;
            } else {
                break;
            }
        }

        Tile.TILE_SIZE = currentSize;
    }


    GlyphLayout layout = new GlyphLayout();

    @Override
    public void render(float delta) {
        update();

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(Color.RED);
        shape.rect(gameArea.getX(), gameArea.getY(), gameArea.getWidth(), gameArea.getHeight());
        shape.setColor(Color.BLUE);
        shape.rect(uiArea.getX(), uiArea.getY(), uiArea.getWidth(), uiArea.getHeight());
        shape.setColor(Color.GOLD);
        shape.rect(infoArea.getX(), infoArea.getY(), infoArea.getWidth(), infoArea.getHeight());
        shape.end();

        batch.begin();
        if (level != null)
            level.render(batch, Gdx.graphics.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE) / 2, (int) (gameArea.getY() + (gameArea.getHeight() / 2 - (level.getHeight() * Tile.TILE_SIZE) / 2)));
        font.setColor(com.badlogic.gdx.graphics.Color.BLACK);

        layout.setText(font, "Moves: " + level.moves);
        font.draw(batch, "Moves: " + level.moves, infoArea.getX() + (infoArea.getWidth() / 2 - layout.width / 2), infoArea.getY() + infoArea.getHeight() - topPadding);

        font.setColor(com.badlogic.gdx.graphics.Color.WHITE);
        batch.end();


        stage.draw();
    }

    public void update() {
        if (level == null) return;
        AllowedMovementType t = level.getCurrentMovementType();

        //TODO: Don't set this every tick
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


        if (level.isOver()) {
            LevelUtil.updateMoves(lvl.getName(), ArrowDriftGame.currentCat, ArrowDriftGame.getCurrentPackID(), level.moves);
            if (getNextLevel() == null) {
                CategoryFinishedScreen.category = ArrowDriftGame.getCurrentPack().getRoot().getChild(ArrowDriftGame.currentCat);
                ArrowDriftGame.setCurrentScreen(new CategoryFinishedScreen());
                return;
            }
            lvl = getNextLevel();
            preLevel();
            level = Level.fromNode(lvl);
        }

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
