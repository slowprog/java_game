package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.effects.DefenceStanceEffect;
import com.geek.rpg.game.effects.EffectsList;
import com.geek.rpg.game.effects.RegenerationEffect;

abstract public class AbstractUnit {
    /**
     * Процент залечивания по умолчанию для юнита для разового применения.
     */
    final private float HEALING_DEFAULT_PERCENT = 0.30f;

    protected GameScreen game;
    private EffectsList effectsList;

    /**
     * Положение юнита на поле.
     */
    protected Vector2 position;
    protected Rectangle rect;
    protected boolean flip;

    /**
     * Все данные непосрсдвенно юнита.
     */
    protected Texture textureHp;
    protected Texture textureUnit;
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int level;

    /**
     * Статы юнита.
     */
    protected int strength;
    protected int dexterity;
    protected int endurance;
    protected int spellpower;
    protected int defence;

    /**
     * Отображать щит поверх юнита или нет.
     */
    protected boolean shield;
    protected Texture textureShield;

    /**
     * Отображать иконку регенерации юнита или нет.
     */
    protected boolean regeneration;
    protected Texture textureRegeneration;

    /**
     * Продолжительность действий с юнитом.
     */
    protected float takeDamageAction;
    protected float attackAction;

    public AbstractUnit(GameScreen game, Vector2 position, Texture textureUnit) {
        this.game = game;
        this.position = position;
        this.textureUnit  = textureUnit;
        this.rect = new Rectangle(position.x, position.y, this.textureUnit.getWidth(), this.textureUnit.getHeight());
        this.effectsList = new EffectsList();

        Pixmap pixmap = new Pixmap(90, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1);
        pixmap.fill();
        pixmap.setColor(1, 1, 1, 1);
        pixmap.fillRectangle(2, 2, 86, 16);

        this.textureHp = new Texture(pixmap);

        this.textureShield = new Texture("shield.png");
        this.textureRegeneration = new Texture("regeneration.png");
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

        this.messagePositive("+" + (this.hp - prevHp));
    }

    public void heal() {
        this.heal(this.HEALING_DEFAULT_PERCENT);
    }

    public void meleeAttack(AbstractUnit enemy) {
        this.attackAction = 1.0f;

        if (!Calculator.isTargetEvaded(this, enemy)) {
            int dmg = Calculator.getMeleeDamage(this, enemy);

            enemy.takeDamage(dmg);
            enemy.messageNegative("-" + dmg);
        } else {
            enemy.messageInfo("Miss");
        }
    }

    public void defenceStance(int rounds) {
        this.effectsList.add(new DefenceStanceEffect(this, rounds));
    }

    public void regenerate(int rounds) {
        this.effectsList.add(new RegenerationEffect(this, rounds));
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        float dx = 50f * (float)Math.sin((1.0f - this.attackAction) * 3.14f);

        if (this.flip) {
            dx *= -1;
        }

        this.renderUnit(batch, dx);

        if (this.isAlive()) {
            this.renderInfo(batch, dx, font);

            if (this.shield) {
                this.renderShield(batch, dx);
            }

            if (this.regeneration) {
                this.renderRegeneration(batch, dx);
            }
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

    private void renderRegeneration(SpriteBatch batch, float dx) {
        batch.setColor(1, 1, 1, 0.9f);

        batch.draw(
            this.textureRegeneration,
            this.position.x + dx + 70,
            this.position.y + 129
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

    public void messageInfo(String text) {
        this.game.getInfoSystem().addMessage(text, this, FlyingText.Colors.WHITE);
    }

    public void messageNegative(String text) {
        this.game.getInfoSystem().addMessage(text, this, FlyingText.Colors.RED);
    }

    public void messagePositive(String text) {
        this.game.getInfoSystem().addMessage(text, this, FlyingText.Colors.GREEN);
    }

    public void getTurn() {
        this.effectsList.tick(this);
    }

    public void setShield(boolean shield) {
        this.shield = shield;
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public Vector2 getPosition() {
        return position;
    }

    public int getDefence() {
        return this.defence;
    }

    public void setRegeneration(boolean regeneration) {
        this.regeneration = regeneration;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public Rectangle getRect() {
        return this.rect;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getDexterity() {
        return this.dexterity;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public int getSpellpower() {
        return this.spellpower;
    }

    public int getLevel() {
        return this.level;
    }
}
