package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.effects.DefenceStanceEffect;
import com.geek.rpg.game.effects.Effect;
import com.geek.rpg.game.effects.RegenerationEffect;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractUnit {
    protected GameScreen game;
    protected Texture textureHp;
    protected Texture textureUnit;
    protected Texture textureShield;
    protected String name;
    protected int hp;
    protected int maxHp;

    protected int level;

    protected Rectangle rect;

    // Primary stats
    protected int strength;
    protected int dexterity;
    protected int endurance;
    protected int spellpower;
    protected int defence;

    private List<Effect> effects;

    /**
     * Отображать щит поверх юнита или нет.
     */
    protected boolean shield;

    protected Vector2 position;
    protected boolean flip;
    protected float takeDamageAction;
    protected float attackAction;

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getEndurance() {
        return endurance;
    }

    public int getSpellpower() {
        return spellpower;
    }

    public int getLevel() {
        return level;
    }

    public AbstractUnit(GameScreen game, Vector2 position, Texture textureUnit) {
        this.game = game;
        this.position = position;
        this.textureUnit  = textureUnit;
        this.rect = new Rectangle(position.x, position.y, this.textureUnit.getWidth(), this.textureUnit.getHeight());
        this.effects = new ArrayList<Effect>();

        Pixmap pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(2, 2, 86, 16);

        this.textureHp = new Texture(pixmap);

        this.textureShield = new Texture("shield.png");
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.rect = new Rectangle(position.x, position.y, this.textureUnit.getWidth(), this.textureUnit.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    public void getTurn() {
        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).tick();

            if (effects.get(i).isEnded() || !this.isAlive()) {
                effects.get(i).end();
                effects.remove(i);
            }
        }
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public void takeDamage(int dmg) {
        this.hp -= dmg;

        if (this.hp < 0) {
            this.hp = 0;
        }

        this.takeDamageAction = 1.0f;
    }

    public void heal(float percent) {
        int prevHp = this.hp;

        // Если вдруг своего здоровья очень мало, то хотя бы +1 сделаем округлением вверх.
        this.hp += (int)Math.ceil(this.hp * percent);

        if (this.hp > this.maxHp) {
            this.hp = this.maxHp;
        }

        this.message("+" + (this.hp - prevHp), FlyingText.Colors.GREEN);
    }

    public void defenceStance(int rounds) {
        DefenceStanceEffect dse = new DefenceStanceEffect();
        dse.start(game.getInfoSystem(), this, rounds);
        effects.add(dse);
    }

    public void regenerate(int rounds) {
        RegenerationEffect dse = new RegenerationEffect();
        dse.start(game.getInfoSystem(), this, rounds);
        effects.add(dse);
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isShield() {
        return this.shield;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        float dx = 50f * (float)Math.sin((1.0f - this.attackAction) * 3.14f);

        if (this.flip) {
            dx *= -1;
        }

        this.renderUnit(batch, dx);

        if (this.isAlive()) {
            this.renderInfo(batch, dx, font);
        }

        if (this.isShield()) {
            this.renderShield(batch, dx);
        }
    }

    private void renderUnit(SpriteBatch batch, float dx) {
        if (this.takeDamageAction > 0) {
            batch.setColor(1f, 1f - this.takeDamageAction, 1f - this.takeDamageAction, 1f);
        }

        batch.draw(
                this.textureUnit,
                this.position.x + dx + (this.isAlive() ? 0 : this.textureUnit.getWidth()),
                this.position.y,
                0,
                0,
                this.textureUnit.getWidth(),
                this.textureUnit.getHeight(),
                1,
                1,
                this.isAlive() ? 0 : 90,
                0,
                0,
                this.textureUnit.getWidth(),
                this.textureUnit.getHeight(),
                this.flip,
                false
        );

        batch.setColor(1f, 1f, 1f, 1f);
    }

    private void renderInfo(SpriteBatch batch, float dx, BitmapFont font) {
        batch.setColor(0.5f, 0,0,1);

        batch.draw(
                this.textureHp,
                this.position.x + dx,
                this.position.y + this.textureUnit.getHeight()
        );

        batch.setColor(0,1,0,1);

        batch.draw(
                this.textureHp,
                this.position.x + dx,
                this.position.y + this.textureUnit.getHeight(),
                0,
                0,
                (int)((float)this.hp / (float)this.maxHp * textureHp.getWidth()),
                20
        );

        batch.setColor(1,1,1,1);

        font.draw(batch, String.valueOf(this.hp), this.position.x + dx, this.position.y + 169, 90, 1, false);
    }

    private void renderShield(SpriteBatch batch, float dx) {
        batch.setColor(1, 1, 1, 0.5f);

        batch.draw(
                this.textureShield,
                this.position.x + dx,
                this.position.y
        );

        batch.setColor(1,1,1,1);
    }

    public void update(float dt) {
        if (this.takeDamageAction > 0) {
            this.takeDamageAction -= dt;
        }

        if (this.attackAction > 0) {
            this.attackAction -= dt;
        }
    }

    public int getDefence() {
        return this.defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public void meleeAttack(AbstractUnit enemy) {
        this.attackAction = 1.0f;

        if (!Calculator.isTargetEvaded(this, enemy)) {
            int dmg = Calculator.getMeleeDamage(this, enemy);

            enemy.takeDamage(dmg);
            enemy.message("-" + dmg, FlyingText.Colors.RED);
        } else {
            enemy.message("Miss", FlyingText.Colors.WHITE);
        }
    }

    public void message(String text, FlyingText.Colors color) {
        this.game.getInfoSystem().addMessage(text, this, color);
    }
}
