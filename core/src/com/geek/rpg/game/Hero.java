package com.geek.rpg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hero extends AbstractUnit {
    public Hero(GeekRpgGame game, Vector2 position) {
        super(game, position, new Texture("knight.png"));
        this.name = "Alex";
        this.maxHp = 100;
        this.hp = this.maxHp;
        this.level = 1;
        this.strength = 40;
        this.dexterity = 10;
        this.endurance = 10;
        this.spellpower = 10;
        this.defence = 5;
        this.flip = false;
    }

    @Override
    public void getTurn() {
    }
}
