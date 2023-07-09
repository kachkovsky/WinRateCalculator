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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (hp != player.hp) return false;
        if (mp != player.mp) return false;
        return units.equals(player.units);
    }

    @Override
    public int hashCode() {
        int result = hp;
        result = 31 * result + mp;
        return result;
    }
}
