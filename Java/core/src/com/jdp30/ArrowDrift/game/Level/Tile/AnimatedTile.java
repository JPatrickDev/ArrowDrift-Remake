package com.jdp30.ArrowDrift.game.Level.Tile;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;

/**
 * Created by Jack Patrick on 11/03/2018.
 * <p>
 * Last Edit: 11/03/2018
 */
public class AnimatedTile extends Tile {
    private Animation<TextureRegion> animation = null;

    public AnimatedTile(String texture, int x, int y, boolean solid, AllowedMovementType type) {
        super(texture, x, y, solid, type);
        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(this.textureObj, 64,
                64);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[19];
        for (int i = 0; i < 19; i++) {
                walkFrames[i] = tmp[0][i];
        }

        // Initialize the Animation with the frame interval and array of frames
        animation = new Animation<TextureRegion>(0.05f, walkFrames);
    }

    float stateTime;

    @Override
    public void draw(SpriteBatch batch, int xo, int yo) {
        //super.draw(batch, xo, yo);
        stateTime += Gdx.graphics.getDeltaTime();
        if (stateTime >= animation.getAnimationDuration())
            stateTime = 0;
        if (animation.getKeyFrame(stateTime) != null)
            batch.draw(animation.getKeyFrame(stateTime), x + xo, y + yo);
    }
}
