package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class AbstractUnit {
    protected GeekRpgGame game;
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

    protected Vector2 position;
    protected boolean flip;
    protected float attackAction;
    protected float takeDamageAction;

    public AbstractUnit(GeekRpgGame game, Vector2 position, Texture texture) {
        this.game = game;
        this.position = position;
        this.texture  = texture;
        this.rect = new Rectangle(position.x, position.y, this.texture.getWidth(), this.texture.getHeight());

        Pixmap pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();

        this.textureHp = new Texture(pixmap);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
        this.rect = new Rectangle(position.x, position.y, this.texture.getWidth(), this.texture.getHeight());
    }

    public Vector2 getPosition() {
        return position;
    }

    abstract public void getTurn();

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

        float ang = 0;

        batch.draw(
                this.texture,
                this.position.x + dx + (this.isAlive() ? 0 : this.texture.getWidth()),
                this.position.y,
                0,
                0,
                this.texture.getWidth(),
                this.texture.getHeight(),
                1,
                1,
                this.isAlive() ? 0 : 90,
                0,
                0,
                this.texture.getWidth(),
                this.texture.getHeight(),
                this.flip,
                false
        );

        batch.setColor(1f, 1f, 1f, 1f);
    }

    public void renderInfo(SpriteBatch batch) {
        batch.setColor(0.5f, 0,0,1);
        batch.draw(textureHp, position.x, position.y + this.texture.getHeight());
        batch.setColor(0,1,0,1);
        batch.draw(textureHp, position.x, position.y + this.texture.getHeight(), 0, 0, (int)((float)this.hp / (float)this.maxHp * textureHp.getWidth()), 20);
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

    public void meleeAttack(AbstractUnit enemy) {
        int dmg = this.strength - enemy.defence;

        dmg = (int)(dmg * 0.8f + (float)dmg * Math.random() * 0.5f);

        if (dmg < 0) {
            dmg = 0;
        }

        this.attackAction = 1.0f;

        int attackChance = 50 + (this.dexterity - enemy.dexterity) * 1 + (this.level - enemy.level) * 5;

        if (attackChance > 95) {
            attackChance = 95;
        }

        if (attackChance < 20) {
            attackChance = 20;
        }

        if (Math.random() * 100 <= attackChance) {
            enemy.takeDamage(dmg);
            this.game.addMessage("-" + dmg, enemy.getPosition().x + 45, enemy.getPosition().y + 75);
        } else {
            this.game.addMessage("MISS", enemy.getPosition().x + 45, enemy.getPosition().y + 75);
        }
    }
}
