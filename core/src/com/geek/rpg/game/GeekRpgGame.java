package com.geek.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GeekRpgGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	AbstractUnit currentUnit;
	Hero hero;
	Monster monster;
	Background background;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Background();
		hero = new Hero();
		hero.setPosition(new Vector2(400, 200));
		monster = new Monster();
		monster.setPosition(new Vector2(700, 200));
		currentUnit = hero;
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		background.render(batch);
		hero.render(batch);
		monster.render(batch);

		batch.end();
	}


	public void update(float dt) {
		monster.update(dt);
		hero.update(dt);

		if (currentUnit == hero) {
			if (InputHandler.checkClickInRect(monster.getRect())) {
				hero.meleeAttack(monster);

				currentUnit = monster;
			}
		}


		if (currentUnit == monster) {
			if (InputHandler.checkClickInRect(hero.getRect())) {
				monster.meleeAttack(hero);

				currentUnit = hero;
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
