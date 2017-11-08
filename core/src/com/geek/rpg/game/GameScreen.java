package com.geek.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private Background background;
    private BitmapFont font;

    private Hero player;
    private List<AbstractUnit> units;
    private List<Button> btnGUI;
    private int currentUnit;
    private int selectedUnit;
    private Texture textureSelector;
    private InfoSystem infoSystem;

    private float animationTimer;

    public GameScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    private void prepareButtons() {
        final GameScreen that = this;

        this.btnGUI = new ArrayList<Button>();

        btnGUI.add(new Button("Attack", new Texture("btn-attack.png"), new Rectangle(20, 20, 80, 80)) {
            @Override
            public void action() {
                if (units.get(selectedUnit) instanceof Monster) {
                    player.meleeAttack(units.get(selectedUnit));
                    that.nextTurn();
                }
            }
        });

        btnGUI.add(new Button("Heal", new Texture("btn-health.png"), new Rectangle(110, 20, 80, 80)) {
            @Override
            public void action() {
                player.heal(0.15f);
                that.nextTurn();
            }
        });

        btnGUI.add(new Button("Regeneration", new Texture("btn-block.png"), new Rectangle(200, 20, 80, 80)) {
            @Override
            public void action() {
                if (units.get(selectedUnit) instanceof Monster) {
                    player.regenerate(3);
                    that.nextTurn();
                }
            }
        });

        btnGUI.add(new Button("Block", new Texture("btn-block.png"), new Rectangle(290, 20, 80, 80)) {
            @Override
            public void action() {
                if (units.get(selectedUnit) instanceof Monster) {
                    player.defenceStance(1);
                    that.nextTurn();
                }
            }
        });
    }

    @Override
    public void show() {
        this.background = new Background();
        this.infoSystem = new InfoSystem();

        this.font = new BitmapFont(Gdx.files.internal("font.fnt"));

        this.units = new ArrayList<AbstractUnit>();
        this.player = new Hero(this, new Vector2(400, 200));

        this.units.add(player);
        this.units.add(new Monster(this, new Vector2(600, 400), player, 70));
        units.add(new Monster(this, new Vector2(800, 300), player, 50));
        units.add(new Monster(this, new Vector2(700, 200), player, 10));
        units.add(new Monster(this, new Vector2(800, 100), player, 80));

        textureSelector = new Texture("selector.png");

        currentUnit  = 0;
        selectedUnit = 0;

        this.prepareButtons();

        this.animationTimer = 0.0f;
    }

    @Override
    public void render(float delta) {
        update(delta);
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

            units.get(i).render(batch, font);
        }

        if (this.isHeroTurn()) {
            for (int i = 0; i < btnGUI.size(); i++) {
                btnGUI.get(i).render(batch);
            }
        }

        this.infoSystem.render(batch, font);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public InfoSystem getInfoSystem() {
        return this.infoSystem;
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

        this.animationTimer = 1.0f;
    }

    public void update(float dt) {
        if (this.animationTimer > 0.0f) {
            this.animationTimer -= dt;
        }

        for (int i = 0; i < units.size(); i++) {
            units.get(i).update(dt);

            if (currentUnit != i && InputHandler.checkClickInRect(units.get(i).getRect()) && units.get(i).isAlive()) {
                selectedUnit = i;
            }
        }

        if (this.canIMakeTurn()) {
            if (this.isHeroTurn()) {
                for (int i = 0; i < btnGUI.size(); i++) {
                    btnGUI.get(i).checkClick();
                }
            } else {
                if (((Monster)units.get(currentUnit)).ai(dt)) {
                    this.nextTurn();
                }
            }
        }

        this.infoSystem.update(dt);
    }

    public boolean canIMakeTurn() {
        return this.animationTimer <= 0.0f;
    }
}
