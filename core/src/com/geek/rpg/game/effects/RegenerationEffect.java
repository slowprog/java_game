package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.FlyingText;
import com.geek.rpg.game.InfoSystem;

public class RegenerationEffect extends AbstractEffect {
    /**
     * Процент залечивания.
     */
    final private float HEALING_PERCENT = 0.09f;

    public RegenerationEffect(AbstractUnit unit, int rounds) {
        super(unit, rounds);
    }

    @Override
    public void start() {
        this.getUnit().setRegeneration(true);
    }

    @Override
    public void tick() {
        super.tick();

        this.getUnit().messagePositive("Regeneration");
        this.getUnit().heal(this.HEALING_PERCENT);
    }

    @Override
    public void end() {
        this.getUnit().setRegeneration(false);
    }
}
