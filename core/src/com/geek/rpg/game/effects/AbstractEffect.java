package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;

abstract public class AbstractEffect {
    private AbstractUnit unit;
    private int rounds;

    public AbstractEffect(AbstractUnit unit, int rounds) {
        this.unit = unit;
        this.rounds = rounds;
    }

    abstract public void start();

    abstract public void end();

    public void tick() {
        this.rounds--;
    }

    public boolean isEnded() {
        return this.rounds == 0;
    }

    public AbstractUnit getUnit() {
        return this.unit;
    }
}
