package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.action.PlayerAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.SplashAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.UnitAttackAction;
import ru.kachkovsky.wrc_summoners_duel.player.ActionSelectToAttackSorter;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SDBeatStage extends Stage<SummonersDuelSubjectsArea> {

    private static final ActionSelectToAttackSorter SORTER = new ActionSelectToAttackSorter();
    private static final List<Action<SummonersDuelSubjectsArea>> SPLASH_ACTION_LIST = Collections.singletonList(new SplashAttackAction());

    public SDBeatStage(List<FinishCheck<SummonersDuelSubjectsArea>> finishChecks) {
        super(finishChecks);
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        Unit unit = area.getTeams()[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex());
        if (unit.hasSplash()) {
            return SPLASH_ACTION_LIST;
        }
        int atk = area.getTeams()[area.getCurrentTeamIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk();
        List<Unit> units = area.getTeams()[area.getReversePlayerIndex()].getUnits();
        //TODO: order to beat units
        //
        List<Unit> unitsUniqueAttacked = UnitUtils.unitsUniqueAttacked(atk, units);
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
