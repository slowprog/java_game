package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class AbstractUnit {
    protected Texture textureHp;
    protected Texture texture;
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

    // Secondary stats
    protected int defence;
    protected int luck;

    protected Vector2 position;
    protected boolean flip;
    protected float attackAction;
    protected float takeDamageAction;

    public AbstractUnit() {
        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 0, 0, 1);
        pixmap.fill();

        this.textureHp = new Texture(pixmap);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.rect = new Rectangle(position.x, position.y, this.texture.getWidth(), this.texture.getHeight());
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

    public void render(SpriteBatch batch) {
        if (this.takeDamageAction > 0) {
            batch.setColor(1f, 1f - this.takeDamageAction, 1f - this.takeDamageAction, 1f);
        }

        float dx = 50f * (float)Math.sin((1f - this.attackAction) * 3.14f);

        if (this.flip) {
            dx *= -1;
        }

        batch.draw(
                this.texture,
                this.position.x + dx,
                this.position.y,
                this.texture.getWidth(),
                this.texture.getHeight(),
                0,
                0,
                this.texture.getWidth(),
                this.texture.getHeight(),
                this.flip,
                false
        );

        batch.setColor(1f, 1f, 1f, 1f);

        batch.draw(
                this.textureHp,
                this.position.x + dx,
                this.position.y + this.texture.getHeight(),
                (100 * this.hp / this.maxHp),
                20
        );
    }

    public void update(float dt) {
        if (this.takeDamageAction > 0) {
            this.takeDamageAction -= dt;
        }

        if (this.attackAction > 0) {
            this.attackAction -= dt;
        }
    }

    public void meleeAttack(AbstractUnit enemy) {

        int dmg = this.strength - enemy.defence;

        if (dmg < 0) {
            dmg = 0;
        }

        this.attackAction = 1.0f;

        int randomLuck = 0 + (int)(Math.random() * 100);

        if (randomLuck > this.luck) {
            return;
        }

        enemy.takeDamage(dmg);
    }
}
