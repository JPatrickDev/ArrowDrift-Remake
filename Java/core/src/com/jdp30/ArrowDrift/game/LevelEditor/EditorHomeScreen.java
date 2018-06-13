package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.GUI.FileDialog;
import com.jdp30.ArrowDrift.game.GUI.LevelSelectDialog;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.jdp30.ArrowDrift.game.Screens.MainMenuScreen;
import jdk.nashorn.internal.scripts.JO;
import storage.Node;
import storage.StorageSystem;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorHomeScreen implements Screen {

    Skin skin;
    Stage stage;
    SpriteBatch batch;

    @Override
    public void show() {
        Tile.TILE_SIZE = 64;
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // A skin can be loaded via JSON or defined programmatically, either is fine. Using a skin is optional but strongly
        // recommended solely for the convenience of getting a texture, region, etc as a drawable, tinted drawable, etc.
        skin = new Skin(Gdx.files.internal("ui/skin.json"));


        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);


        final TextButton newLevel = new TextButton("New Map Pack", skin);
        newLevel.getLabelCell().padBottom(5f).padTop(5f);
        newLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                newLevel();
            }
        });

        table.add(newLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton loadLevel = new TextButton("Load Map Pack", skin);
        loadLevel.getLabelCell().padBottom(5f).padTop(5f);
        loadLevel.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                loadPack();
            }
        });
        table.add(loadLevel).width(newLevel.getWidth() * 3).pad(20);
        table.row();

        final TextButton back = new TextButton("Back To Main Menu", skin);
        back.getLabelCell().padBottom(5f).padTop(5f);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ArrowDriftGame.setCurrentScreen(new MainMenuScreen());
            }
        });
        table.add(back).width(newLevel.getWidth() * 3).pad(20);
        table.row();
    }

    StorageSystem currentSystem = null;
    private void loadPack() {
        FileDialog files = new FileDialog("Choose Map Pack", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals("OK")) {
                    FileHandle file = getFile();
                    try {
                        StorageSystem system = StorageSystem.fromFile(file.toString());
                        currentSystem = system;
                        showCatSelectDialog(system,file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        files.setDirectory(Gdx.files.external("Arrow Drift Data/Levels/"));
        files.show(stage);
    }

    private void showCatSelectDialog(final StorageSystem system,final FileHandle file){
        LevelSelectDialog files = new LevelSelectDialog("Select Category", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals("OK")) {
                    Node n = selected;
                    if(n == null){
                        String name = JOptionPane.showInputDialog(null,"Category Name:");
                        Node newCat = new Node(name);
                        newCat.addTexture("preview",new Texture("levelPreview.png"));
                        system.getRoot().addChild(newCat);
                        try {
                            system.save(file.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        showCatSelectDialog(system,file);
                    }else{
                        showLevelSelectDialog(n,file);
                    }
                }
            }
        };
        files.setNode(system.getRoot());
        files.show(stage);
    }

    private void showLevelSelectDialog(final Node category, final FileHandle file){
        LevelSelectDialog files = new LevelSelectDialog("Select Level", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals("OK")) {
                    Node n = selected;
                    if(n == null){
                        String name = JOptionPane.showInputDialog(null,"Level  Name:");
                        int w = Integer.parseInt(JOptionPane.showInputDialog(null,"Width:"));
                        int h = Integer.parseInt(JOptionPane.showInputDialog(null,"Height:"));
                        Level level = Level.blank(w,h);
                        category.addChild(level.toNode(name));
                        try {
                            currentSystem.save(file.toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        showLevelSelectDialog(category,file);
                    }else{
                      //  showLevelSelectDialog(n,file);
                        startEditor(Level.fromNode(n),category,n.getName(),currentSystem,file.toString());
                    }
                }
            }
        };
        files.setNode(category);
        files.show(stage);
    }

    public void newLevel() {
        String packName = JOptionPane.showInputDialog(null,"Name:");
        StorageSystem system = new StorageSystem(packName);
        currentSystem = system;
        try {
            system.save(Gdx.files.external("Arrow Drift Data/Levels/" + packName).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        showCatSelectDialog(system,Gdx.files.external("Arrow Drift Data/Levels/" + packName));
    }

    public void startEditor(Level level,Node catNode,String name,StorageSystem system,String path) {
        System.out.println("starting editor");
        ArrowDriftGame.setCurrentScreen(new EditorMainScreen(level,name,catNode,system,path));
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
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
        skin.dispose();
    }
}
