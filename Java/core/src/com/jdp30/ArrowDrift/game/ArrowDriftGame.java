package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;
import storage.Node;
import storage.StorageSystem;

import java.io.IOException;


public class ArrowDriftGame extends Game {

    private static ArrowDriftGame INSTANCE;

    private static final String VERSION_NUMBER = "0.3";

    private BitmapFont font;
    private SpriteBatch batch;

    public static StorageSystem userdata;
    public ArrowDriftGame() {
        this.INSTANCE = this;

    }

    @Override
    public void create() {
        createUserData();
        try {
            userdata = StorageSystem.fromFile("Arrow Drift Data/config");
        } catch (IOException e) {
            e.printStackTrace();
            //TODO - Handle better
            System.exit(0);
        }
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
        notifyStorageChanged();
    }

    public void createUserData(){
        if(Gdx.files.external("Arrow Drift Data/config").exists())
            return;
        StorageSystem system = new StorageSystem("userdata");
        Node settings = new Node("settings");
        system.getRoot().addChild(settings);
        try {
            system.save("Arrow Drift Data/config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void notifyStorageChanged() {
        System.out.println("Saving...");
        try {
            userdata.save("Arrow Drift Data/config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
