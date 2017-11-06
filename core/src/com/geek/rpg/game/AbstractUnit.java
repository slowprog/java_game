package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

abstract public class AbstractUnit {
    /**
     * Дополнительная защита при блоке.
     */
    final protected int ADDITIONAL_DEFENCE = 5;

    /**
     * Процент залечивания.
     */
    final protected float HEALING_PERCENT = 0.15f;

    protected GeekRpgGame game;
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

    // Secondary stats
    protected int defence;
    protected boolean block;

    protected Vector2 position;
    protected boolean flip;
    protected float attackAction;
    protected float takeDamageAction;

    public AbstractUnit(GeekRpgGame game, Vector2 position, Texture textureUnit) {
        this.game = game;
        this.position = position;
        this.textureUnit  = textureUnit;
        this.rect = new Rectangle(position.x, position.y, this.textureUnit.getWidth(), this.textureUnit.getHeight());

        Pixmap pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();

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

    public void healing() {
        if (this.hp < this.maxHp) {
            // Если вдруг своего здоровья очень мало, то хотя бы +1 сделаем округлением вверх.
            int tmpHp = (int)Math.ceil(this.hp * this.HEALING_PERCENT);
            int tmpHpDiff = (this.hp + tmpHp) - this.maxHp;

            if (tmpHpDiff > 0) {
                tmpHp -= tmpHpDiff;
            }

            this.hp += tmpHp;

            this.message("+" + tmpHp);
        } else {
            this.message("+0");
        }
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isBlock() {
        return this.block;
    }

    public void render(SpriteBatch batch) {
        float dx = 50f * (float)Math.sin((1f - this.attackAction) * 3.14f);

        if (this.flip) {
            dx *= -1;
        }

        this.renderUnit(batch, dx);

        if (this.isAlive()) {
            this.renderInfo(batch, dx);
        }

        if (this.isBlock()) {
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

    private void renderInfo(SpriteBatch batch, float dx) {
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
        return this.defence + (this.isBlock() ? this.ADDITIONAL_DEFENCE : 0);
    }

    public void meleeAttack(AbstractUnit enemy) {
        int dmg = this.strength - (enemy.getDefence());

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
            enemy.message("-" + dmg);
        } else {
            enemy.message("Miss");
        }
    }

    public void message(String text) {
        this.game.addMessage(text, this.getPosition().x + 45, this.getPosition().y + 75);
    }
}
