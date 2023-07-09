package ru.kachkovsky.wrc_summoners_duel.stage.strategy;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategy;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.action.PlayerAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.SplashAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.UnitAttackAction;
import ru.kachkovsky.wrc_summoners_duel.player.ActionSelectToAttackSorter;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class SDBeatBaseBotStrategy implements StageActionsStrategy<SummonersDuelSubjectsArea> {


    private static final ActionSelectToAttackSorter SORTER = new ActionSelectToAttackSorter();
    private static final List<Action<SummonersDuelSubjectsArea>> SPLASH_ACTION_LIST = Collections.singletonList(new SplashAttackAction());

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        Unit unit = area.getTeams()[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex());
        if (unit.hasSplash()) {
            return SPLASH_ACTION_LIST;
        }
        int atk = area.getTeams()[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk();
        Player otherPlayer = area.getTeams()[area.getReversePlayerIndex()];
        List<Unit> units = otherPlayer.getUnits();

        List<Unit> unitsUniqueAttacked = UnitUtils.unitsUniqueAttacked(atk, units);
        Iterator<Unit> it = unitsUniqueAttacked.iterator();

        while (it.hasNext()) {
            Unit u = it.next();
            //always kill the player instead of a unit if you can
            if (u.alive(otherPlayer.getHp())) {
                it.remove();
            }
        }

        SORTER.prepareSort(unitsUniqueAttacked, atk);
        unitsUniqueAttacked.add(null);
        SORTER.sort(unitsUniqueAttacked);

        List<Action<SummonersDuelSubjectsArea>> list = new ArrayList<>();
        for (int i = 0; i < unitsUniqueAttacked.size(); i++) {
            Unit u = unitsUniqueAttacked.get(i);
            if (u != null) {
                list.add(new UnitAttackAction(units.indexOf(u)));
            } else {
                list.add(new PlayerAttackAction());
            }
        }
        return list;
    }
}
