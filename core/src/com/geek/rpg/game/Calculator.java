package com.geek.rpg.game;

public class Calculator {
    private static final int MIN_MELEE_ATTACK_CHANCE = 20;
    private static final int MAX_MELEE_ATTACK_CHANCE = 95;

    public static int getMeleeDamage(AbstractUnit attacker, AbstractUnit target) {
        int dmg = attacker.getStrength() - (target.getDefence());

        dmg = (int)(dmg * 0.8f + (float)dmg * Math.random() * 0.4f);

        if (dmg < 1) {
            dmg = 1;
        }

        return dmg;
    }

    public static boolean isTargetEvaded(AbstractUnit attacker, AbstractUnit target) {
        int attackChance = (int)(70.0f + (attacker.getDexterity() - target.getDexterity()) * 0.2f + (attacker.getLevel() - target.getLevel()) * 2.0f);

        if (attackChance > Calculator.MAX_MELEE_ATTACK_CHANCE) {
            attackChance = Calculator.MAX_MELEE_ATTACK_CHANCE;
        }

        if (attackChance < Calculator.MIN_MELEE_ATTACK_CHANCE) {
            attackChance = Calculator.MIN_MELEE_ATTACK_CHANCE;
        }

        return Math.random() * 100 > attackChance;
    }
}
