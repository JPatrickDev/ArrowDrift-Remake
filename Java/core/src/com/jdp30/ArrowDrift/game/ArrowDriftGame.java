package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.utils.Array;
import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;
import storage.Node;
import storage.StorageSystem;

import java.io.IOException;
import java.util.ArrayList;


public class ArrowDriftGame extends Game {

    private static ArrowDriftGame INSTANCE;

    private static final String VERSION_NUMBER = "0.6";

    private BitmapFont font;
    private SpriteBatch batch;

    public static StorageSystem userdata;
    private static StorageSystem currentPack;
    public static String currentCat;

    public ArrowDriftGame() {
        this.INSTANCE = this;

    }

    public static void setCurrentPack(StorageSystem currentPack) {
        ArrowDriftGame.currentPack = currentPack;
    }

    public static StorageSystem getCurrentPack() {
        return currentPack;
    }

    public static String getCurrentPackID() {
        return getCurrentPack().getRoot().getName();
    }

    public static String[] getPacks() {
        FileHandle[] files = Gdx.files.external("Arrow Drift Data/Levels/").list();
        String[] out = new String[files.length];
        for(int i = 0; i != out.length; i++){
            out[i] = files[i].name();
        }
        return out;
    }

    @Override
    public void create() {
        createUserData();
        try {
            userdata = StorageSystem.fromFile("Arrow Drift Data/config");
            StorageSystem mappack = StorageSystem.fromFile("Arrow Drift Data/Levels/DEFAULT");
            ArrowDriftGame.setCurrentPack(mappack);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO - Handle better
            System.exit(0);
        }
        font = new BitmapFont(Gdx.files.internal("fonts/cg12.fnt"), Gdx.files.internal("fonts/cg12.png"), false);
        batch = new SpriteBatch();

        setScreen(new MainMenuScreen());
    }

    public static void setCurrentScreen(Screen s) {
        INSTANCE.setScreen(s);
    }


    @Override
    public void render() {
        super.render();
        batch.begin();
        font.draw(batch, VERSION_NUMBER, 0, 12);
        batch.end();
    }

    @Override
    public void dispose() {
        notifyStorageChanged();
    }

    public void createUserData() {
        if (Gdx.files.external("Arrow Drift Data/config").exists())
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

    public static boolean isPortrait() {
        return Gdx.graphics.getHeight() > Gdx.graphics.getWidth();
    }
}
