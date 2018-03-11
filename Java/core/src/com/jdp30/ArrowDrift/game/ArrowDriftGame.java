package com.jdp30.ArrowDrift.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.jdp30.ArrowDrift.game.GUI.ImgButton;
import com.jdp30.ArrowDrift.game.Level.AllowedMovementType;
import com.jdp30.ArrowDrift.game.Level.Level;
import com.jdp30.ArrowDrift.game.Level.Tile.Tile;

public class ArrowDriftGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Level level = null;

	int topPadding = 10;

	Stage ui = null;

	private ImgButton upDown = null,leftRight = null;
	@Override
	public void create () {
		batch = new SpriteBatch();
		//level = new Level(7,7);
		level = Level.load("level.txt");

		Drawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture("upDown.png")));
		ImageButton playButton = new ImageButton(drawable);
		float wh = Gdx.graphics.getWidth()/2;

		upDown = new ImgButton("button/up.png",(int)(wh/2 - 128/2), (int) ((Gdx.graphics.getHeight() - (level.getHeight() * Tile.TILE_SIZE))/2 - 128/2));
		leftRight = new ImgButton("button/right.png", (int) (Gdx.graphics.getWidth() / 2 + ((wh/2 - 128/2))), (int) ((Gdx.graphics.getHeight() - (level.getHeight() * Tile.TILE_SIZE))/2 - 128/2));
	}

	@Override
	public void render () {

		update();

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		level.render(batch, Gdx.graphics.getWidth() / 2 - (level.getWidth() * Tile.TILE_SIZE)/2,(Gdx.graphics.getHeight() - level.getHeight() * Tile.TILE_SIZE) - topPadding);
		upDown.draw(batch);
		leftRight.draw(batch);
		batch.end();
	}


	public void update(){
		AllowedMovementType t = level.getCurrentMovementType();
		if(t.getUPDOWN() == 0){
			upDown.setTexture(new Texture("button/up.png"));
		}else if(t.getUPDOWN() == 1){
			upDown.setTexture(new Texture("button/down.png"));
		}

		if(t.getLEFTRIGHT() == 0){
			leftRight.setTexture(new Texture("button/left.png"));
		}else if(t.getLEFTRIGHT() == 1){
			leftRight.setTexture(new Texture("button/right.png"));
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
		level.dispose();
	}
}
