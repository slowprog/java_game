package com.geek.rpg.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.geek.rpg.game.InputHandler;

abstract public class Button {
    private Texture texture;
    private Rectangle rectangle;
    private boolean enable;

    public Button(Texture texture, Rectangle rectangle) {
        this.texture = texture;
        this.rectangle = rectangle;
    }

    public boolean checkClick() {
        if (InputHandler.checkClickInRect(rectangle)) {
            this.action();

            return true;
        }

        return false;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void render(SpriteBatch batch) {
        if (this.enable) {
            if (InputHandler.checkClickInRect(rectangle)) {
                batch.setColor(1, 1, 1, 1.0f);
            } else if (InputHandler.checkHoverInRect(rectangle)) {
                batch.setColor(1, 1, 1, 0.8f);
            } else {
                batch.setColor(1, 1, 1, 0.5f);
            }
        } else {
            batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        }

        batch.draw(texture, rectangle.getX(), rectangle.getY());

        batch.setColor(1,1,1,1);
    }

    abstract public void action();
}


