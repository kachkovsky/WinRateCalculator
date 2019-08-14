package ru.kachkovsky.wrc_summoners_duel.stage.strategy;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategy;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;
import ru.kachkovsky.wrc_summoners_duel.utils.BuyUnitUtils;

import java.util.List;

public class SDBuyBaseStrategy implements StageActionsStrategy<SummonersDuelSubjectsArea> {
    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        int mp = area.getTeams()[area.getCurrentTeamIndex()].getMp();
        int maxEnemyAttack = UnitUtils.findMaxAttack(area.getTeams()[area.getReversePlayerIndex()].getUnits());
        return BuyUnitUtils.getUnitChoiceBuyActions(mp, maxEnemyAttack);
    }
}
