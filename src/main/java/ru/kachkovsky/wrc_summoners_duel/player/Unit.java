package ru.kachkovsky.wrc_summoners_duel.player;

public class Unit {

    public static Unit createUnit(int atk, int def, int hp, boolean splash) {
        return new Unit(atk, def, hp, splash);
    }

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
}
