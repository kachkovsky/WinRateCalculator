package ru.kachkovsky.wrc_summoners_duel.player;

import ru.kachkovsky.utils.StringUtils;

public class Unit {
    //USe UnitUtils to create new unit
    Unit(int atk, int def, int hp, boolean splash) {
        this.atk = atk;
        this.def = def;
        this.hp = hp;
        this.splash = splash;
    }

    private int atk;
    private int def;
    private int hp;
    private boolean splash;

    public int getAtk() {
        return atk;
    }

    public int getDef() {
        return def;
    }

    public int getHp() {
        return hp;
    }

    public boolean hasSplash() {
        return splash;
    }

    public boolean alive(int attacked) {
        return def + hp > attacked;
    }

    @Override
    public String toString() {
        return StringUtils.spaces(atk, 'A') + "_"
                + StringUtils.spaces(def, 'D') + "_"
                + (splash ? "sp" : "") + "_"
                + StringUtils.spaces(hp, 'H');
    }

    public int getCost() {
        return getHp() / 2 + getAtk() + getDef() + ((hasSplash() ? 1 : 0) * 2);
    }
}
