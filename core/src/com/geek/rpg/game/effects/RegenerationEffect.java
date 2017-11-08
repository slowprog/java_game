package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.FlyingText;
import com.geek.rpg.game.InfoSystem;

public class RegenerationEffect extends Effect {
    /**
     * Процент залечивания.
     */
    final protected float HEALING_PERCENT = 0.05f;

    private int roundsTotal;

    @Override
    public void start(InfoSystem infoSystem, AbstractUnit unit, int rounds) {
        super.start(infoSystem, unit, rounds);

        this.roundsTotal = rounds;

        infoSystem.addMessage("Regeneration " + this.roundsTotal + "T/" + Math.floor(this.HEALING_PERCENT * 100) + "%", unit, FlyingText.Colors.GREEN);
    }

    @Override
    public void tick() {
        super.tick();

        infoSystem.addMessage("Regeneration", unit, FlyingText.Colors.GREEN);
        unit.heal(this.HEALING_PERCENT);
    }

    @Override
    public void end() {
        infoSystem.addMessage("Regeneration end", unit, FlyingText.Colors.WHITE);
    }
}
