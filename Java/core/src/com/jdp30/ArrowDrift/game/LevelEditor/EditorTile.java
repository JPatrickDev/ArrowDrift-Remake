package com.jdp30.ArrowDrift.game.LevelEditor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Direction;
import com.jdp30.ArrowDrift.game.Level.Tile.AnimatedTile;
import com.jdp30.ArrowDrift.game.Level.Tile.BeltTile;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;
import com.sun.xml.internal.ws.Closeable;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class EditorTile extends Actor implements Cloneable {

    private Tile parent;

    public EditorTile(Tile t, int x, int y) {
        setPosition(x, y);
        setSize(64, 64);
        this.parent = t.copy();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (parent instanceof AnimatedTile) {
            if (parent instanceof BeltTile) {
                batch.draw(((AnimatedTile) parent).getCurrentFrame(), getX(),getY(), 32, 32, 64, 64, 1, 1, ((BeltTile) parent).getAngle());
            } else {
                batch.draw(((AnimatedTile) parent).getCurrentFrame(), getX(), getY());
            }
        } else {
            batch.draw(parent.getTextureObj(), getX(), getY());
            if (parent.getMovementType()!= AllowedMovementType.NONE) {
                Texture t = parent.movementTextures.get(parent.getMovementType());
                batch.draw(t, getX(),getY());
            }
        }
    }

    public Tile getParentTile() {
        return parent;
    }

    @Override
    protected EditorTile clone() throws CloneNotSupportedException {
        EditorTile t = new EditorTile(this.parent, (int) this.getX(), (int) getY());
        return t;
    }

    public void setTile(Tile tile) {
        this.parent = tile.copy();
    }

    public void rightClicked() {
        System.out.println("Right clicked");
        if (this.parent instanceof BeltTile) {
            System.out.println("Changing DIR");
            ((BeltTile) this.parent).setDir(Direction.next(((BeltTile) parent).getDir()));
        }else{
            this.parent.setType(AllowedMovementType.next(parent.getMovementType()));
        }
    }
}
