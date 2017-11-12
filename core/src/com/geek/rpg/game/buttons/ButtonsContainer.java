package com.geek.rpg.game.buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.geek.rpg.game.BattleScreen;
import com.geek.rpg.game.actions.BaseAction;

import java.util.ArrayList;
import java.util.List;

public class ButtonsContainer {
    private List<Button> buttons;

    public ButtonsContainer(final BattleScreen game) {
        this.buttons = new ArrayList<Button>();

        for (int i = 0; i < game.getActions().size(); i++) {
            final int w = i;

            this.buttons.add(new Button(game.getActions().get(w).getBtnTexture(), new Rectangle(200 + i * 100, 20, 90, 90)) {
                @Override
                public void action() {
                    if (game.getActions().get(w).action(game.getUnitCurrent())) {
                        game.nextTurn();
                    }
                }
            });
        }


        //
        // this.buttons.add(new Button(new Texture("btn-block.png"), new Rectangle(120, 20, 90, 90)) {
        //     @Override
        //     public void action() {
        //         game.getUnitCurrent().defenceStance(1);
        //         game.nextTurn();
        //     }
        // });
        //
        // this.buttons.add(new Button(new Texture("btn-health.png"), new Rectangle(220, 20, 90, 90)) {
        //     @Override
        //     public void action() {
        //         game.getUnitCurrent().heal();
        //         game.nextTurn();
        //     }
        // });
        //
        // this.buttons.add(new Button(new Texture("btn-regeneration.png"), new Rectangle(320, 20, 90, 90)) {
        //     @Override
        //     public void action() {
        //         game.getUnitCurrent().regenerate(3);
        //         game.nextTurn();
        //     }
        // });
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
