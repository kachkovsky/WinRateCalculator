package ru.kachkovsky.wrc_summoners_duel.player;

import java.util.List;

public class UnitUtils {

    public static Unit createUnit(int atk, int def, int hp, boolean splash) {
        return new Unit(atk, def, hp, splash);
    }

    public static Unit createAfterAttack(Unit u, int atk) {
        return new Unit(u.getAtk(), u.getDef(), u.getHp() - Math.max(0, atk - u.getDef()), u.hasSplash());
    }

    public static String unitsToString(List<Unit> units) {
        StringBuilder sb = new StringBuilder();
        for (Unit unit : units) {
            sb.append(unit.toString()).append(" | ");
        }
        return sb.toString();
    }

    public static int summaryAttack(List<Unit> units) {
        int atk = 0;
        for (Unit unit : units) {
            atk += unit.getAtk();
        }
        return atk;
    }
}
