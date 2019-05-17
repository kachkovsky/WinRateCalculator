package ru.kachkovsky.wrc_summoners_duel.player;

import ru.kachkovsky.utils.MathUtils;

import java.util.List;

public class UnitUtils {

    public static Unit createUnit(boolean splash, int atk, int def, int hp) {
        return new Unit(atk, def, hp, splash);
    }

    public static Unit createAfterAttack(Unit u, int atk) {
        return new Unit(u.getAtk(), u.getDef(), u.getHp() - Math.max(0, atk - u.getDef()), u.hasSplash());
    }

    public static int findSplashUnitIndex(List<Unit> units) {
        return findSplashUnitIndex(units, 0);
    }

    public static int findSplashUnitIndex(List<Unit> units, final int startIndex) {
        for (int i = startIndex; i < units.size(); i++) {
            if (units.get(i).hasSplash()) {
                return i;
            }
        }
        return MathUtils.INDEX_NOT_FOUND;
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
