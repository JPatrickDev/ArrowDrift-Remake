package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public abstract class AnimatedTile extends Tile {
    protected Animation<TextureRegion> animation = null;

    public AnimatedTile(String texture, int x, int y, boolean solid, AllowedMovementType type) {
        super(texture, x, y, solid, type);
        TextureRegion[][] tmp = TextureRegion.split(this.getTextureObj(), 64,
                64);
        TextureRegion[] animFrames = new TextureRegion[19];
        for (int i = 0; i < 19; i++) {
            animFrames[i] = tmp[0][i];
        }
        animation = new Animation<TextureRegion>(0.05f, animFrames);
    }

    protected float stateTime;

    @Override
    public void draw(SpriteBatch batch, int xo, int yo) {
        stateTime += Gdx.graphics.getDeltaTime();
        if (animation.getKeyFrame(stateTime) != null)
            batch.draw(animation.getKeyFrame(stateTime, true), x + xo, y + yo,Tile.TILE_SIZE,Tile.TILE_SIZE);
    }

    public TextureRegion getCurrentFrame() {
        stateTime += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(stateTime, true);
    }
}
