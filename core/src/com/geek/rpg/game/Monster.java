package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Monster extends AbstractUnit {
    private Hero target;

    /**
     * Агрессивность отвечающая за вероятность выбора агрессивных действий по сравнению с защитными.
     */
    private int aggression;

    public Monster(GameScreen game, Vector2 position, Hero target, int aggression) {
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
        if (!this.game.canIMakeTurn()) {
            return false;
        }

        int randomBlock = (int)(Math.random() * (100 + 1));

        if (this.aggression > randomBlock) {
            this.meleeAttack(this.target);
        } else {
            // Если было выбранно неагрессивное действие, то лечимся, если здоровье меньше максимального.
            if (this.maxHp > this.hp) {
                if (this.maxHp / 2 > this.hp) {
                    this.heal();
                } else {
                    this.regenerate(3);
                }
            } else {
                this.defenceStance(1);
            }
        }

        return true;
    }
}
