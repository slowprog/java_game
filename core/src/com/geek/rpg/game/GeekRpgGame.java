package com.geek.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GeekRpgGame extends Game {
	private SpriteBatch batch;
	private BattleScreen gameScreen;

	@Override
	public void create () {
		this.batch 	    = new SpriteBatch();
		this.gameScreen = new BattleScreen(this.batch);

		this.setScreen(gameScreen);
	}

	@Override
	public void render() {
		this.getScreen().render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		batch.dispose();
		gameScreen.dispose();
	}
}
