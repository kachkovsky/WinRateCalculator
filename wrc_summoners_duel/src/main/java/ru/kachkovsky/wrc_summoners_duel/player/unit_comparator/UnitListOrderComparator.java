package ru.kachkovsky.wrc_summoners_duel.player.unit_comparator;

import ru.kachkovsky.wrc_summoners_duel.player.Unit;

import java.util.Comparator;
import java.util.List;

public class UnitListOrderComparator implements Comparator<List<Unit>> {

    private int opponentMaxAtk;

    public UnitListOrderComparator(int opponentMaxAtk) {
        this.opponentMaxAtk = opponentMaxAtk;
    }

    @Override
    public int compare(List<Unit> o1, List<Unit> o2) {
        return Integer.signum(profit(o2) - profit(o1));
    }

    public int profit(List<Unit> list) {
        int profit = 0;
        for (Unit unit : list) {
            profit += profit(unit);
        }
        return profit;
    }

    private int profit(Unit unit) {
        return (int) ((Math.max(0, (unit.hasSplash() ? 1 : 0)
                * (unit.getAtk() * 12)
        )
                + (int) Math.sqrt(unit.getAtk() * 100))
                / (unit.alive(opponentMaxAtk) ? 1 : Math.pow(2, unit.getCost()))
                + (Math.max(-100,
                -11 * Math.max(0, Math.max(0, unit.getHp() - opponentMaxAtk) - unit.getAtk()) +
                        -15 * Math.max(0, Math.max(0, unit.getDef() - opponentMaxAtk) + 1 - unit.getAtk()))
        ));
    }
}
