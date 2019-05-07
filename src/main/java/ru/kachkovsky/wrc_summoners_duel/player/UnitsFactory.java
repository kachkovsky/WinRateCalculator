package ru.kachkovsky.wrc_summoners_duel.player;

public class UnitsFactory {

    public static Unit createUnit(int atk, int def, int hp, boolean splash) {
        return new Unit(atk, def, hp, splash);
    }

    public static Unit createAfterAttack(Unit u, int atk) {
        return new Unit(u.getAtk(), u.getDef(), u.getHp() - Math.max(0, atk - u.getDef()), u.hasSplash());
    }
}
