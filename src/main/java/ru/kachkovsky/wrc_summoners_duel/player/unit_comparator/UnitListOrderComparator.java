package ru.kachkovsky.wrc_summoners_duel.player.unit_comparator;

import ru.kachkovsky.wrc_summoners_duel.player.Unit;

import java.util.Comparator;
import java.util.List;

public class UnitListOrderComparator implements Comparator<List<Unit>> {
    @Override
    public int compare(List<Unit> o1, List<Unit> o2) {
        return Integer.signum(profit(o2) - profit(o1));
    }

    private int profit(List<Unit> list) {
        int profit = 1;
        for (Unit unit : list) {
            profit *= profit(unit);
        }
        return profit;
    }

    private int profit(Unit unit) {
        return Math.max(0, (unit.hasSplash() ? 1 : 0)
                * ((int) Math.pow(16, Math.sqrt(unit.getAtk()) * 3))
        )
                + unit.getAtk() * 10 +
                Math.max(-10,
                        -4 * Math.max(0, unit.getHp() - unit.getAtk()) + unit.getHp()
                                - 3 * Math.max(0, unit.getDef() + 1 - unit.getAtk()) + unit.getDef() * 2);
    }
}
