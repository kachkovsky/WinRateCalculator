package ru.kachkovsky.wrc_summoners_duel.player;

import java.util.Comparator;
import java.util.List;

public class ActionSelectToAttackSorter implements Comparator<Unit> {
    private int maxEnemyAtk;
    private int maxSplashEnemyAtk;
    private int myAtk;

    public void prepareSort(List<Unit> units, int myAtk) {
        this.myAtk = myAtk;
        this.maxSplashEnemyAtk = UnitUtils.findMaxSplashAttack(units);
        this.maxEnemyAtk = UnitUtils.findMaxAttack(units);
    }
    public void sort(List<Unit> units) {
        units.sort(this);
    }
    @Override
    public int compare(Unit o1, Unit o2) {
        return Integer.signum(priority(o2) - priority(o1));
    }

    private int priority(Unit u) {
        if (u == null) {
            return 1000;
        }
        int result = u.getAtk() * (u.hasSplash() ? 3 : 1);
        if ((u.expectedHpAfterAttack(myAtk) <= 0)
                && ((u.getAtk() == maxEnemyAtk) || (u.hasSplash() && maxSplashEnemyAtk == u.getAtk()))) {
            return 1000 * result;
        }
        return result;
    }
}
