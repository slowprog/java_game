package com.geek.rpg.game.actions;

import com.badlogic.gdx.graphics.Texture;
import com.geek.rpg.game.AbstractUnit;

abstract public class BaseAction {
    protected Texture btnTexture;

    public Texture getBtnTexture() {
        return btnTexture;
    }

    abstract public boolean action(AbstractUnit me);
}
