package com.geek.rpg.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.actions.*;
import com.geek.rpg.game.buttons.ButtonsContainer;

import java.util.ArrayList;
import java.util.List;

public class BattleScreen implements Screen {
    private SpriteBatch batch;
    private Background background;
    private BitmapFont font;

    private Hero player;
    private List<AbstractUnit> units;
    private int currentUnit;
    private int selectedUnit;
    private Texture textureSelector;
    private InfoSystem infoSystem;
    private ButtonsContainer buttonsContainer;

    private float animationTimer;

    private List<BaseAction> actions;

    public BattleScreen(SpriteBatch batch) {
        this.batch = batch;
    }

    @Override
    public void show() {
        this.background = new Background();
        this.infoSystem = new InfoSystem();

        this.actions = new ArrayList<BaseAction>();
        this.actions.add(new MeleeAttackAction());
        this.actions.add(new DefenceStanceAction());
        this.actions.add(new RestAction());
        this.actions.add(new RegenerationAction());

        this.textureSelector  = new Texture("selector.png");
        this.buttonsContainer = new ButtonsContainer(this);

        this.font   = new BitmapFont(Gdx.files.internal("font.fnt"));
        this.units  = new ArrayList<AbstractUnit>();
        this.player = new Hero(this, new Vector2(400, 200));

        this.units.add(this.player);
        this.units.add(new Monster(this, new Vector2(600, 400), this.player, 70, actions.get(0), actions.get(1)));
        this.units.add(new Monster(this, new Vector2(800, 300), this.player, 50, actions.get(0), actions.get(1)));
        this.units.add(new Monster(this, new Vector2(700, 200), this.player, 10, actions.get(0), actions.get(1)));
        this.units.add(new Monster(this, new Vector2(800, 100), this.player, 80, actions.get(0), actions.get(1)));

        this.currentUnit    = 0;
        this.selectedUnit   = 0;
        this.animationTimer = 0.0f;
    }

    @Override
    public void render(float delta) {
        this.update(delta);

        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.batch.begin();
        this.background.render(this.batch);

        for (int i = 0; i < this.units.size(); i++) {
            if (this.currentUnit == i) {
                this.batch.setColor(1,1,0,0.5f);
                this.batch.draw(this.textureSelector, this.units.get(i).getPosition().x, this.units.get(i).getPosition().y);
                this.batch.setColor(1,1,1,1);
            }

            if (this.selectedUnit == i) {
                this.batch.setColor(1,0,0,0.5f);
                this.batch.draw(this.textureSelector, this.units.get(i).getPosition().x, this.units.get(i).getPosition().y);
                this.batch.setColor(1,1,1,1);
            }

            this.units.get(i).render(this.batch, this.font);
        }

        this.buttonsContainer.render(this.batch);

        this.infoSystem.render(this.batch, this.font);

        this.batch.end();
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
        return this.units.get(this.currentUnit) instanceof Hero;
    }

    public AbstractUnit getUnitCurrent() {
        return this.units.get(this.currentUnit);
    }

    public boolean isMonsterSelect() {
        return this.units.get(this.selectedUnit) instanceof Monster;
    }

    public AbstractUnit getUnitSelect() {
        return this.units.get(this.selectedUnit);
    }

    public void nextTurn()
    {
        do {
            this.currentUnit++;

            if (this.currentUnit >= this.units.size()) {
                this.currentUnit = 0;
            }
        } while (!this.getUnitCurrent().isAlive());

        this.getUnitCurrent().getTurn();

        this.animationTimer = 1.0f;
    }

    public void update(float dt) {
        if (this.animationTimer > 0.0f) {
            this.animationTimer -= dt;
        }

        for (int i = 0; i < units.size(); i++) {
            this.units.get(i).update(dt);

            if (this.currentUnit != i && InputHandler.checkClickInRect(this.units.get(i).getRect()) && this.units.get(i).isAlive()) {
                this.selectedUnit = i;
                this.getUnitCurrent().setTarget(this.getUnitSelect());
            }
        }

        if (this.canIMakeTurn()) {
            if (this.isHeroTurn()) {
                this.buttonsContainer.enable();
                this.buttonsContainer.checkClick();
            } else {
                this.buttonsContainer.disable();

                if (((Monster)this.getUnitCurrent()).ai(dt)) {
                    this.nextTurn();
                }
            }
        }

        this.infoSystem.update(dt);
    }

    public boolean canIMakeTurn() {
        return this.animationTimer <= 0.0f;
    }

    public List<BaseAction> getActions() {
        return actions;
    }
}
