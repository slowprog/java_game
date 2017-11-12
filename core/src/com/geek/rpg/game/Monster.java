package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.geek.rpg.game.actions.BaseAction;

public class Monster extends AbstractUnit {
    /**
     * Агрессивность отвечающая за вероятность выбора агрессивных действий по сравнению с защитными.
     */
    private int aggression;

    BaseAction attack;
    BaseAction randomAbility;

    public Monster(BattleScreen game, Vector2 position, AbstractUnit target, int aggression, BaseAction attack, BaseAction randomAbility) {
        super(game, position, new Texture("skeleton.png"));

        this.target = target;
        this.aggression = aggression;
        this.attack = attack;
        this.randomAbility = randomAbility;

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

        int r = (int)(Math.random() * 2);

        if (r == 0) {
            return attack.action(this);
        }
        if (r == 1) {
            return randomAbility.action(this);
        }

        return false;
        // if (!this.game.canIMakeTurn()) {
        //     return false;
        // }
        //
        // int randomBlock = (int)(Math.random() * (100 + 1));
        //
        // if (this.aggression > randomBlock) {
        //     this.meleeAttack(this.target);
        // } else {
        //     // Если было выбранно неагрессивное действие, то лечимся, если здоровье меньше максимального.
        //     if (this.maxHp > this.hp) {
        //         if (this.maxHp / 2 > this.hp) {
        //             this.heal();
        //         } else {
        //             this.regenerate(3);
        //         }
        //     } else {
        //         this.defenceStance(1);
        //     }
        // }
        //
        // return true;
    }
}
