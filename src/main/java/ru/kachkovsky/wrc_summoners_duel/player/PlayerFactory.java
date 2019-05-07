package ru.kachkovsky.wrc_summoners_duel.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerFactory {
    public static Player copyAndAddUnits(Player old, List<Unit> units) {
        Player player = new Player(old.getHp(), old.getMp(), new ArrayList<>(old.getUnits()));
        player.getUnits().addAll(units);
        return player;
    }
}
