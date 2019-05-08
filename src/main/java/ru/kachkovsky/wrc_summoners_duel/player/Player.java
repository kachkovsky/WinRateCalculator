package ru.kachkovsky.wrc_summoners_duel.player;

import ru.kachkovsky.utils.StringUtils;

import java.util.List;

public class Player {
    private int hp;
    private int mp;
    private List<Unit> units;

    public Player(int hp, int mp, List<Unit> units) {
        this.hp = hp;
        this.mp = mp;
        this.units = units;
    }

    public int getHp() {
        return hp;
    }

    public int getMp() {
        return mp;
    }

    public List<Unit> getUnits() {
        return units;
    }

    @Override
    public String toString() {
        return StringUtils.spaces(hp, 'h') + "_" + StringUtils.spaces(mp, 'm') + ". Units: " + UnitUtils.unitsToString(units);
    }
}
