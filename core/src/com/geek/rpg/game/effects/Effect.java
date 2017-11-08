package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.InfoSystem;

abstract public class Effect {
    InfoSystem infoSystem;
    AbstractUnit unit;
    protected int rounds;

    public void start(InfoSystem infoSystem, AbstractUnit unit, int rounds) {
        this.infoSystem = infoSystem;
        this.unit = unit;
        this.rounds = rounds;
    }

    public void tick() {
        this.rounds--;
    }

    public boolean isEnded() {
        return this.rounds == 0;
    }

    abstract public void end();
}
