package com.geek.rpg.game.actions;

import com.badlogic.gdx.graphics.Texture;
import com.geek.rpg.game.Assets;
import com.geek.rpg.game.Calculator;
import com.geek.rpg.game.Unit;

public class FireballAction extends BaseAction {
    public FireballAction() {
        super("FIREBALL", "btnFireball");
    }

    @Override
    public boolean action(Unit me) {
        if (me.getTarget() == null) return false;
        if (me.isMyTeammate(me.getTarget())) return false;

        me.setAttackAction(1.0f);
        me.setCurrentAnimation(Unit.AnimationType.ATTACK);

        int dmg = Calculator.getFireballDamage(me, me.getTarget());
        me.getTarget().changeHp(-dmg);

        me.getBattleScreen().getSpecialFxEmitter().setup(me, me.getTarget(), 1.0f, 2f, 2f, 0f,true);
        me.getBattleScreen().getSpecialFxEmitter().setup(me.getTarget(), me.getTarget(), 1.0f, 2f, 10f, 1f, false);
        return true;
    }
}
