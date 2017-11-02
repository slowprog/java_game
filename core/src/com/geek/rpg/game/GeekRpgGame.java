package com.geek.rpg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GeekRpgGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private Background background;
	private BitmapFont font;

	private Hero player;
	private List<AbstractUnit> units;
	private List<Button> btnGUI;
	private FlyingText[] msgs;
	private int currentUnit;
	private int selectedUnit;
	private Texture textureSelector;

	@Override
	public void create () {
		batch 	   = new SpriteBatch();
		background = new Background();
		font = new BitmapFont(Gdx.files.internal("font.fnt"));
		msgs = new FlyingText[50];

		for (int i = 0; i < msgs.length; i++) {
			msgs[i] = new FlyingText();
		}
		
		units = new ArrayList<AbstractUnit>();
		btnGUI = new ArrayList<Button>();
		player = new Hero(this, new Vector2(400, 200));

		units.add(player);
		units.add(new Monster(this, new Vector2(700, 200), player));
		units.add(new Monster(this, new Vector2(800, 100), player));

		textureSelector = new Texture("selector.png");

		btnGUI.add(new Button("Attack", new Texture("btn.png"), new Rectangle(200, 20, 80, 80)));

		currentUnit  = 0;
		selectedUnit = 0;
	}

	public void addMessage(String text, float x, float y) {
		for (int i = 0; i <= msgs.length; i++) {
			if (!msgs[i].isActive()) {
				msgs[i].setup(text, x, y);

				break;
			}
		}
	}

	@Override
	public void render() {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		background.render(batch);

		for (int i = 0; i < units.size(); i++) {
			if (currentUnit == i) {
				batch.setColor(1,1,0,0.5f);
				batch.draw(textureSelector, units.get(i).getPosition().x, units.get(i).getPosition().y);
				batch.setColor(1,1,1,1);
			}

			if (selectedUnit == i) {
				batch.setColor(1,0,0,0.5f);
				batch.draw(textureSelector, units.get(i).getPosition().x, units.get(i).getPosition().y );
				batch.setColor(1,1,1,1);
			}

			units.get(i).render(batch);

			if (units.get(i).isAlive()) {
				units.get(i).renderInfo(batch);
			}
		}

		for (int i = 0; i < btnGUI.size(); i++) {
			btnGUI.get(i).render(batch);
		}

		for (int i = 0; i < msgs.length; i++) {
			if (msgs[i].isActive()) {
				msgs[i].render(batch, font);
			}
		}

		batch.end();
	}

	public void update(float dt) {
		for (int i = 0; i < units.size(); i++) {
			units.get(i).update(dt);

			if (currentUnit != i && InputHandler.checkClickInRect(units.get(i).getRect())) {
				selectedUnit = i;
			}
		}

		if (this.isHeroTurn()) {
			for (int i = 0; i < btnGUI.size(); i++) {
				if (btnGUI.get(i).checkClick()) {
					String action = btnGUI.get(i).getAction();

					if (action.equals("Attack")) {
						if (units.get(selectedUnit) instanceof Monster) {
							player.meleeAttack(units.get(selectedUnit));
							nextTurn();
						}
					}
				}
			}
		} else {
			if (((Monster)units.get(currentUnit)).ai(dt)) {
				nextTurn();
			}
		}

		for (int i = 0; i < msgs.length; i++) {
			if (msgs[i].isActive()) {
				msgs[i].update(dt);
			}
		}
	}

	public boolean isHeroTurn() {
		return units.get(currentUnit) instanceof Hero;
	}

	public void nextTurn()
	{
		do {
			currentUnit++;
			if (currentUnit >= units.size()) {
                currentUnit = 0;
            }
		} while (!units.get(currentUnit).isAlive());

		units.get(currentUnit).getTurn();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
