package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.action.PlayerAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.SplashAttackAction;
import ru.kachkovsky.wrc_summoners_duel.action.UnitAttackAction;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SDBeatStage extends Stage<SummonersDuelSubjectsArea> {

    private static final List<Action<SummonersDuelSubjectsArea>> SPLASH_ACTION_LIST = Collections.singletonList(new SplashAttackAction());

    public SDBeatStage(List<FinishCheck<SummonersDuelSubjectsArea>> finishChecks) {
        super(finishChecks);
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        Unit unit = area.getTeams()[area.getCurrentPlayerIndex()].getUnits().get(area.getCurrentPlayerUnitIndex());
        if (unit.hasSplash()) {
            return SPLASH_ACTION_LIST;
        }
        int atk = area.getTeams()[area.getCurrentPlayerIndex()].getUnits().get(area.getCurrentPlayerUnitIndex()).getAtk();
        List<Action<SummonersDuelSubjectsArea>> list = new ArrayList<>();
        List<Unit> units = area.getTeams()[area.getReversePlayerIndex()].getUnits();
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).alive(atk)) {
                list.add(new UnitAttackAction(i));
            }
        }
        list.add(new PlayerAttackAction());
        return list;
    }
}
