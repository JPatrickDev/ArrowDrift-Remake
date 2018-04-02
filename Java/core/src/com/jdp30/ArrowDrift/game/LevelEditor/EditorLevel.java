package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorLevel {

    private int width, height;
    private EditorTile[][] tiles = null;
    Stage stage;

    public EditorLevel(Stage stage, int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new EditorTile[width][height];
        this.stage = stage;
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
                stage.addActor(t);
            }
        }
    }

    public static EditorLevel fromLevel(Stage stage,Level level) {
        EditorTile[][] tiles = new EditorTile[level.getWidth()][level.getHeight()];
        for (int x = 0; x != level.getWidth(); x++) {
            for (int y = 0; y != level.getHeight(); y++) {
                tiles[x][y] = new EditorTile(level.getTile(x, y), x * Tile.TILE_SIZE, y * Tile.TILE_SIZE);
            }
        }
        EditorLevel l = new EditorLevel(stage,level.getWidth(), level.getHeight());
        l.setTiles(tiles);
        return l;
    }

}
