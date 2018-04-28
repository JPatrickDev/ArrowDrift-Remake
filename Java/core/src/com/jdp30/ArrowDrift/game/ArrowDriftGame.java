package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;


public class ArrowDriftGame extends Game {

    private static ArrowDriftGame INSTANCE;

    private static final String VERSION_NUMBER = "0.3";

    private BitmapFont font;
    private SpriteBatch batch;
    public ArrowDriftGame() {
        this.INSTANCE = this;

    }

    @Override
    public void create() {
        font = new BitmapFont(Gdx.files.internal("fonts/cg12.fnt"),Gdx.files.internal("fonts/cg12.png"),false);
        batch = new SpriteBatch();
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
