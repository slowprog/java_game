package com.geek.rpg.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by SlowProg on 19.11.2017.
 */

public class SpecialFxEmitter {
    private SpecialFX[] fxs;

    public SpecialFxEmitter() {
        this.fxs = new SpecialFX[100];
        for (int i = 0; i < fxs.length; i++) {
            this.fxs[i] = new SpecialFX();
        }
    }

    public void setup(Unit me, Unit target, float maxTime, float scaleFrom, float scaleTo, boolean oneCycle) {
        for (int i = 0; i < fxs.length; i++) {
            if (!fxs[i].isActive()) {
                fxs[i].setup(me.getPosition().x + 45, me.getPosition().y + 75, target.getPosition().x + 45, target.getPosition().y + 75, maxTime, scaleFrom, scaleTo, oneCycle);
                break;
            }
        }
    }


    public void render(SpriteBatch batch) {
        for (int i = 0; i < fxs.length; i++) {
            if (fxs[i].isActive()) {
                fxs[i].render(batch);
            }
        }
    }

    public void update(float dt) {
        for (int i = 0; i < fxs.length; i++) {
            if (fxs[i].isActive()) {
                fxs[i].update(dt);
            }
        }
    }
}
