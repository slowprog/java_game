package com.geek.rpg.game.actions;

import com.badlogic.gdx.graphics.Texture;
import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.effects.DefenceStanceEffect;

public class DefenceStanceAction extends BaseAction {
    public DefenceStanceAction() {
        this.btnTexture = new Texture("btn-block.png");
    }

    @Override
    public boolean action(AbstractUnit me) {
        me.getEffectsList().add(new DefenceStanceEffect(me, 1));

        return true;
    }
}
