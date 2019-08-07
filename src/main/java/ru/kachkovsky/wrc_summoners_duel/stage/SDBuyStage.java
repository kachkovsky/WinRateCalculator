package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;
import ru.kachkovsky.wrc_summoners_duel.utils.BuyUnitUtils;

import java.util.List;

public class SDBuyStage extends Stage<SummonersDuelSubjectsArea> {
    public SDBuyStage(List<FinishCheck<SummonersDuelSubjectsArea>> finishChecks) {
        super(finishChecks);
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        int mp = area.getTeams()[area.getCurrentTeamIndex()].getMp();
        int maxEnemyAttack = UnitUtils.findMaxAttack(area.getTeams()[area.getReversePlayerIndex()].getUnits());
        return BuyUnitUtils.getUnitChoiceBuyActions(mp, maxEnemyAttack);
    }

}
