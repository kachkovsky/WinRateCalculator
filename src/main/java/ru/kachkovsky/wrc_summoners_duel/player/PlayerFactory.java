package ru.kachkovsky.wrc_summoners_duel.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    public static Player copyAndAddUnits(Player old, List<Unit> units) {
        Player player = new Player(old.getHp(), old.getMp(), new ArrayList<>(old.getUnits()));
        player.getUnits().addAll(units);
        return player;
    }

    public static Player doSplashAttack(Player oldEnemy, int atk) {
        ArrayList<Unit> units = new ArrayList<>(oldEnemy.getUnits().size());
        for (Unit u : oldEnemy.getUnits()) {
            if (u.alive(atk)) {
                units.add(UnitsFactory.createAfterAttack(u, atk));
            }
        }
        return new Player(oldEnemy.getHp() - atk, oldEnemy.getMp(), units);
    }

    public static Player doAttack(Player oldEnemy, int atk, int unitIndex) {
        ArrayList<Unit> units = new ArrayList<>(oldEnemy.getUnits().size());
        Unit unit = units.get(unitIndex);
        if (unit.alive(atk)) {
            units.set(unitIndex, UnitsFactory.createAfterAttack(unit, atk));
        } else {
            units.remove(unitIndex);
        }
        return new Player(oldEnemy.getHp(), oldEnemy.getMp(), units);
    }
}