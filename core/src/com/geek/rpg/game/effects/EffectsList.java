package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;

import java.util.ArrayList;
import java.util.List;

public class EffectsList {
    private List<AbstractEffect> effects;

    public EffectsList() {
        this.effects = new ArrayList<AbstractEffect>();;
    }

    public void tick(AbstractUnit unit) {
        for (int i = effects.size() - 1; i >= 0; i--) {
            effects.get(i).tick();

            if (effects.get(i).isEnded() || !unit.isAlive()) {
                effects.get(i).end();
                effects.remove(i);
            }
        }
    }

    public void add(AbstractEffect effect) {
        effect.start();
        effects.add(effect);
    }
}
