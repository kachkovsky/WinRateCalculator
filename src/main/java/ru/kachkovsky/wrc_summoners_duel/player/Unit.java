package ru.kachkovsky.wrc_summoners_duel.player;

import ru.kachkovsky.utils.StringUtils;

public class Unit {
    private final int value;

    //USe UnitUtils to create new unit
    Unit(int atk, int def, int hp, boolean splash) {
        value = atk
                + hp * 0x100
                + def * 0x10000
                + (splash ? 0x1000000 : 0);
    }

    public int getAtk() {
        return value & 0xFF;
    }

    public int getDef() {
        return (value >> 16) & 0xFF;
    }

    public int getHp() {
        return (value >> 8) & 0xFF;
    }

    public boolean hasSplash() {
        return ((value >> 24) & 0x1) == 1;
    }

    public boolean alive(int attacked) {
        return getDef() + getHp() > attacked;
    }

    @Override
    public String toString() {
        return StringUtils.spaces(getAtk(), 'A') + "_"
                + StringUtils.spaces(getDef(), 'D') + "_"
                + (hasSplash() ? "sp" : "") + "_"
                + StringUtils.spaces(getHp(), 'H');
    }

    public int getCost() {
        return getHp() / 2 + getAtk() + getDef() + ((hasSplash() ? 1 : 0) * 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return (value == o.hashCode() && (o instanceof Unit));
    }

    @Override
    public int hashCode() {
        return value;
    }
}
