package com.geek.rpg.game;

import java.io.Serializable;

/**
 * Created by SlowProg on 18.11.2017.
 */

public class Hero implements Serializable {
    private Unit[][] units;

    public Hero() {
        units = new Unit[2][3];
    }

    public Unit[][] getUnits() {
        return units;
    }

    /**
     * Устанавливаем массив юнитов для героя. Установка идёт
     * слева на право и сверху вниз по сетке 2 х 3.
     *
     * @param inUnits
     */
    public void setArmy(Unit... inUnits) {
        int counter = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                units[j][i] = inUnits[counter];

                if (units[j][i] != null) {
                    units[j][i].setHero(this);
                }

                counter++;
            }
        }
    }
}
