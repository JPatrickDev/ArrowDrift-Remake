package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
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
import org.omg.PortableInterceptor.INACTIVE;

public class ArrowDriftGame extends Game {

    private static ArrowDriftGame INSTANCE;

    public ArrowDriftGame() {
        this.INSTANCE = this;
    }

    @Override
    public void create() {
       //  setScreen(new InGameScreen());
        setScreen(new EditorHomeScreen());
    }

    public static void setCurrentScreen(Screen s){
        INSTANCE.setScreen(s);
    }


    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {

    }

}
