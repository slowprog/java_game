package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;

public class DefenceStanceEffect extends AbstractEffect {
    /**
     * Дополнительная защита при блоке.
     */
    final private int ADDITIONAL_DEFENCE = 5;

    public DefenceStanceEffect(AbstractUnit unit, int rounds) {
        super(unit, rounds);
    }

    @Override
    public void start() {
        this.getUnit().setDefence(this.getUnit().getDefence() + this.ADDITIONAL_DEFENCE);
        this.getUnit().setShield(true);
    }

    @Override
    public void end() {
        this.getUnit().setDefence(this.getUnit().getDefence() - this.ADDITIONAL_DEFENCE);
        this.getUnit().setShield(false);
    }
}
