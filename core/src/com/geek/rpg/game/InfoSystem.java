package com.geek.rpg.game;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class InfoSystem {
    private FlyingText[] msgs;
    private int messageCount;

    public InfoSystem() {
        this.msgs = new FlyingText[20];

        for (int i = 0; i < msgs.length; i++) {
            msgs[i] = new FlyingText();
        }
    }

    public void addMessage(String text, AbstractUnit unit, FlyingText.Colors color) {
        this.addMessage(
            text,
            unit.getPosition().x + unit.getRect().getWidth() / 2,
            unit.getPosition().y + unit.getRect().getHeight() / 2,
            color
        );
    }

    public void addMessage(String text, float x, float y, FlyingText.Colors color) {
        for (int i = 0; i <= this.msgs.length; i++) {
            if (!this.msgs[i].isActive()) {
                this.msgs[i].setup(text, x, y - this.messageCount * 20, color);

                break;
            }
        }

        this.messageCount++;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        for (int i = 0; i < this.msgs.length; i++) {
            if (this.msgs[i].isActive()) {
                this.msgs[i].render(batch, font);
            }
        }
    }

    public void update(float dt) {
        this.messageCount = 0;

        for (int i = 0; i < this.msgs.length; i++) {
            if (this.msgs[i].isActive()) {
                this.msgs[i].update(dt);
            }
        }
    }
}
