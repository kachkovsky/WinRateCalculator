package ru.kachkovsky.wrc_summoners_duel.player;

import ru.kachkovsky.utils.MathUtils;

import java.util.ArrayList;
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

    public static int minDefForUnitAliveAfterAttackByAllWithMaxEconomic(List<Unit> otherPlayerUnits) {
        int oldAtk = 0;
        int curAtk = 0;
        for (Unit u : otherPlayerUnits) {
            if (curAtk <= u.getAtk()) {
                oldAtk = curAtk;
                curAtk = u.getAtk();
            } else if (oldAtk < u.getAtk()) {
                oldAtk = u.getAtk();
            }
        }
        return oldAtk;
    }

    public static int findMaxEnemyAttack(List<Unit> otherPlayerUnits) {
        int atk = 0;
        for (Unit u : otherPlayerUnits) {
            if (atk < u.getAtk()) {
                atk = u.getAtk();
            }
        }
        return atk;
    }

    public static int mpForUnits(List<Unit> units) {
        int cost = 0;
        for (Unit unit : units) {
            cost += unit.getCost();
        }
        return cost;
    }

    public static boolean firstBetterOrEquals(Unit u1, Unit u2) {
        return u1.getAtk() >= u2.getAtk()
                && u1.getHp() >= u2.getHp()
                && u1.getDef() >= u2.getDef()
                && (u1.hasSplash() || (!u1.hasSplash() && !u2.hasSplash()));
    }

    public static List<Unit> unitsUniqueAttacked(int atk, List<Unit> unitsAttacked) {
        List<Unit> list = new ArrayList<>();
        for (int i = 0; i < unitsAttacked.size(); i++) {
            if (unitsAttacked.get(i).getDef() < atk) {
                list.add(unitsAttacked.get(i));
            }
        }
        int last = list.size() - 1;
        do {
            int i = last - 1;
            while (i >= 0) {
                Unit u1 = list.get(i);
                Unit u2 = list.get(last);
                if (firstBetterOrEquals(u1, u2) && (u1.expectedHpAfterAttack(atk) >= u2.expectedHpAfterAttack(atk))) {
                    list.remove(last);
                    last--;
                    i = last - 1;
                } else if (firstBetterOrEquals(u2, u1) && (u2.expectedHpAfterAttack(atk) >= u1.expectedHpAfterAttack(atk))) {
                    list.remove(i);
                    last--;
                    i--;
                } else {
                    i--;
                }
            }
            last--;
        } while (last > 0);
        return list;
    }

    public static boolean overallAdvantageOfFirstSortedListCheck(List<Unit> firstList, List<Unit> secondList) {
        int i = 0;
        for (int i1 = 0; i1 < secondList.size() && i < firstList.size(); i1++) {
            while (i < firstList.size()) {
                if (firstBetterOrEquals(firstList.get(i), secondList.get(i1))) {
                    if (i1 == secondList.size() - 1) {
                        return true;
                    } else {
                        break;
                    }
                }
                i++;
            }
            i++;
        }
        return secondList.isEmpty();
    }
}
