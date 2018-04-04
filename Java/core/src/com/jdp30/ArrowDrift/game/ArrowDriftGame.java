package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jdp30.ArrowDrift.game.GUI.Callback;
import com.jdp30.ArrowDrift.game.GUI.ImgButton;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.LevelEditor.EditorHomeScreen;
import com.jdp30.ArrowDrift.game.Screens.InGameScreen;
import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;
import org.omg.PortableInterceptor.INACTIVE;

public class ArrowDriftGame extends Game {

    private static ArrowDriftGame INSTANCE;

    private static final String VERSION_NUMBER = "0.1";

    private BitmapFont font;
    private SpriteBatch batch;
    public ArrowDriftGame() {
        this.INSTANCE = this;

    }

    @Override
    public void create() {
        font = new BitmapFont(Gdx.files.internal("fonts/cg12.fnt"),Gdx.files.internal("fonts/cg12.png"),false);
        batch = new SpriteBatch();
       //  setScreen(new InGameScreen());
     //   setScreen(new EditorHomeScreen());
        setScreen(new MainMenuScreen());
    }

    public static void setCurrentScreen(Screen s){
        INSTANCE.setScreen(s);
    }


    @Override
    public void render() {
        super.render();
        batch.begin();
        font.draw(batch,VERSION_NUMBER,0,12);
        batch.end();
    }

    @Override
    public void dispose() {

    }

}
