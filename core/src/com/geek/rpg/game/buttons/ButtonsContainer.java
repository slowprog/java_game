package com.geek.rpg.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.geek.rpg.game.GameScreen;

import java.util.ArrayList;
import java.util.List;

public class ButtonsContainer {
    private List<Button> buttons;

    public ButtonsContainer(final GameScreen game) {
        this.buttons = new ArrayList<Button>();

        this.buttons.add(new Button(new Texture("btn-attack.png"), new Rectangle(20, 20, 90, 90)) {
            @Override
            public void action() {
                if (game.isMonsterSelect()) {
                    game.getUnitCurrent().meleeAttack(game.getUnitSelect());
                    game.nextTurn();
                }
            }
        });

        this.buttons.add(new Button(new Texture("btn-block.png"), new Rectangle(120, 20, 90, 90)) {
            @Override
            public void action() {
                game.getUnitCurrent().defenceStance(1);
                game.nextTurn();
            }
        });

        this.buttons.add(new Button(new Texture("btn-health.png"), new Rectangle(220, 20, 90, 90)) {
            @Override
            public void action() {
                game.getUnitCurrent().heal();
                game.nextTurn();
            }
        });

        this.buttons.add(new Button(new Texture("btn-regeneration.png"), new Rectangle(320, 20, 90, 90)) {
            @Override
            public void action() {
                game.getUnitCurrent().regenerate(3);
                game.nextTurn();
            }
        });
    }

    public void checkClick() {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).checkClick();
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).render(batch);
        }
    }

    public void enable() {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setEnable(true);
        }
    }

    public void disable() {
        for (int i = 0; i < this.buttons.size(); i++) {
            this.buttons.get(i).setEnable(false);
        }
    }
}
