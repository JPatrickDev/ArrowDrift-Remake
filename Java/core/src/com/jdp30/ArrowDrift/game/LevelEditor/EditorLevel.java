package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorLevel extends ClickListener {

    private int width, height;
    private EditorTile[][] tiles = null;
    Stage stage;
    private EditorMainScreen parent;

    public EditorLevel(EditorMainScreen parent, Stage stage, int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new EditorTile[width][height];
        this.stage = stage;
        this.parent = parent;
    }

    /* public void render(SpriteBatch batch) {

         for (EditorTile[] tt : tiles) {
             for (EditorTile t : tt) {
                 t.draw(batch, 1f);
             }
         }
     }
 */
    public void setTiles(EditorTile[][] tiles) {
        this.tiles = tiles;
        for (EditorTile[] tt : tiles) {
            for (EditorTile t : tt) {
                t.addListener(this);
                stage.addActor(t);
            }
        }
    }

    public static EditorLevel fromLevel(EditorMainScreen main, Stage stage, Level level) {
        EditorTile[][] tiles = new EditorTile[level.getWidth()][level.getHeight()];
        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                tiles[x][y] = new EditorTile(level.getTile(x, y), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
            }
        }
        EditorLevel l = new EditorLevel(main, stage, level.getWidth(), level.getHeight());
        l.setTiles(tiles);
        return l;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        EditorTile t = (EditorTile)event.getListenerActor();
        if(parent.getCurrentTile() != null){
            t.setTile(parent.getCurrentTile().getParentTile());
        }
    }
}
