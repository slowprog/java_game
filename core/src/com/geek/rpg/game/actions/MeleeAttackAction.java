package com.geek.rpg.game.actions;

import com.badlogic.gdx.graphics.Texture;
import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.Calculator;
import com.geek.rpg.game.Monster;

public class MeleeAttackAction extends BaseAction {
    public MeleeAttackAction() {
        this.btnTexture = new Texture("btn-attack.png");
    }

    @Override
    public boolean action(AbstractUnit me) {
        if (me.getTarget().getClass().equals(me.getClass())) {
            return false;
        }

        me.setAttackAction(1.0f);

        if (!Calculator.isTargetEvaded(me, me.getTarget())) {
            int dmg = Calculator.getMeleeDamage(me, me.getTarget());

            me.getTarget().changeHp(-dmg);
        } else {
            me.getTarget().miss();
        }

        return true;
    }
}
