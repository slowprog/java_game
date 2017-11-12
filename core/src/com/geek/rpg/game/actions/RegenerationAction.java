package com.geek.rpg.game.actions;

import com.badlogic.gdx.graphics.Texture;
import com.geek.rpg.game.AbstractUnit;
import com.geek.rpg.game.effects.RegenerationEffect;

public class RegenerationAction extends BaseAction {
    public RegenerationAction() {
        this.btnTexture = new Texture("btn-regeneration.png");
    }

    @Override
    public boolean action(AbstractUnit me) {
        me.getEffectsList().add(new RegenerationEffect(me, 3));

        return true;
    }
}
