package com.geek.rpg.game.effects;

import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.FlyingText;
import com.geek.rpg.game.InfoSystem;

public class DefenceStanceEffect extends Effect {
    /**
     * Дополнительная защита при блоке.
     */
    final protected int ADDITIONAL_DEFENCE = 5;

    @Override
    public void start(InfoSystem infoSystem, AbstractUnit unit, int rounds) {
        super.start(infoSystem, unit, rounds);

        this.unit.setDefence(unit.getDefence() + this.ADDITIONAL_DEFENCE);
        this.unit.setShield(true);

        infoSystem.addMessage("Shields up! +" + this.ADDITIONAL_DEFENCE, unit, FlyingText.Colors.GREEN);
    }

    @Override
    public void end() {
        this.unit.setDefence(unit.getDefence() - this.ADDITIONAL_DEFENCE);
        this.unit.setShield(false);

        infoSystem.addMessage("Shields down! -" + this.ADDITIONAL_DEFENCE, unit, FlyingText.Colors.WHITE);
    }
}
