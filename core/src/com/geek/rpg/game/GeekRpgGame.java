package com.geek.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class GeekRpgGame extends ApplicationAdapter {
	SpriteBatch batch;
	Background background;
	ArrayList<AbstractUnit> units = new ArrayList<AbstractUnit>();
	int currentUnit;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Background();

		Hero hero = new Hero();
		hero.setPosition(new Vector2(400, 200));

		units.add(hero);
		
		Monster monster = new Monster();
		monster.setPosition(new Vector2(700, 200));

		units.add(monster);

		currentUnit = 0;
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		background.render(batch);

		for (int i = 0; i < units.size(); i++) {
			units.get(i).render(batch);
		}

		batch.end();
	}


	public void update(float dt) {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).update(dt);
		}

		for (int i = 0; i < units.size(); i++) {
			if (currentUnit != i && InputHandler.checkClickInRect(units.get(i).getRect())) {
				units.get(currentUnit).meleeAttack(units.get(i));

				currentUnit++;

				if (currentUnit > units.size() - 1) {
					currentUnit = 0;
				}

				break;
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
