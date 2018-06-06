package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jdp30.ArrowDrift.game.Entity.Entity;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

/**
 * Created by Jack Patrick on 02/04/2018.
 * <p>
 * Last Edit: 02/04/2018
 */
public class EntityContainer extends Actor {

    private Entity parent;
    public EntityContainer(Entity parent){
        this.parent = parent;
        setWidth(Tile.TILE_SIZE);
        setHeight(Tile.TILE_SIZE);
        setX(parent.getX() * Tile.TILE_SIZE);
        setY(parent.getY() * Tile.TILE_SIZE);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(parent.getTexture(),getX(),getY(),Tile.TILE_SIZE,Tile.TILE_SIZE);
    }

    public Entity getEntity() {
        return parent.copy();
    }

    public EntityContainer copy(){
        return new EntityContainer(getEntity());
    }
}
