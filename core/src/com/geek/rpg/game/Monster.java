package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Monster extends AbstractUnit {
    private Hero target;
    private float sleepTimer;

    /**
     * Агрессивность отвечающая за вероятность выбора атаки по сравнению с защитой от 0 до 100.
     */
    private int aggression;

    public Monster(GeekRpgGame game, Vector2 position, Hero target, int aggression) {
        super(game, position, new Texture("skeleton.png"));
        this.target = target;
        this.aggression = aggression;

        this.name = "Skelet";
        this.maxHp = 50;
        this.hp = this.maxHp;
        this.level = 1;
        this.strength = 15;
        this.dexterity = 5;
        this.endurance = 5;
        this.spellpower = 0;
        this.defence = 1;
        this.flip = true;
    }

    public boolean ai(float dt) {
        if (this.sleepTimer > 0.0f) {
            this.sleepTimer -= dt;

            return false;
        }

        int randomBlock = (int)(Math.random() * (100 + 1));

        if (this.aggression > randomBlock) {
            this.meleeAttack(this.target);
        } else {
            // Если было выбранно неагрессивное действие, то лечимся, если здоровье меньше максимального.
            if (this.maxHp > this.hp) {
                this.healing();
            } else {
                this.setBlock(true);
            }
        }

        return true;
    }

    @Override
    public void getTurn() {
        this.sleepTimer = 1.0f;
    }
}
