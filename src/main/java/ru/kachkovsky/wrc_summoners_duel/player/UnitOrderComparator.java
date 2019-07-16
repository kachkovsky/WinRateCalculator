package ru.kachkovsky.wrc_summoners_duel.player;

import java.util.Comparator;

public class UnitOrderComparator implements Comparator<Unit> {
    @Override
    public int compare(Unit o1, Unit o2) {
        int result = getUnitCostX2(o1) - getUnitCostX2(o2);
        if (result == 0) {
            result = o1.hasSplash() ?
                    (o2.hasSplash() ? compareAtk(o1, o2) : 1)
                    :
                    (o2.hasSplash() ? -1 : compareAtk(o1, o2));
        }
        return Integer.signum(result);
    }

    private int compareAtk(Unit o1, Unit o2) {
        if (o1.getAtk() == o2.getAtk()) {
            if (o1.getDef() == o2.getDef()) {
                return o1.getHp() - o2.getHp();
            } else {
                return o1.getDef() - o2.getDef();
            }
        } else {
            return o1.getAtk() - o2.getAtk();
        }
    }

    private int getUnitCostX2(Unit u) {
        return u.getHp() / 2 + 2 * (u.getAtk() + u.getDef() + ((u.hasSplash() ? 1 : 0) * 2));
    }
}
