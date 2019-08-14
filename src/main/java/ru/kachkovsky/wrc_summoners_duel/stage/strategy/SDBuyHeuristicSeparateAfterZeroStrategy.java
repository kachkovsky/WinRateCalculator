package ru.kachkovsky.wrc_summoners_duel.stage.strategy;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategy;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;
import ru.kachkovsky.wrc_summoners_duel.utils.BuyUnitUtils;

import java.util.List;

public class SDBuyHeuristicSeparateAfterZeroStrategy implements StageActionsStrategy<SummonersDuelSubjectsArea> {
    private static final int ITEMS_AFTER_EMPTY_BUY_AND_ONE = 3;

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        int mp = area.getTeams()[area.getCurrentTeamIndex()].getMp();
        int maxEnemyAttack = UnitUtils.findMaxAttack(area.getTeams()[area.getReversePlayerIndex()].getUnits());
        List<List<Unit>> unitChoices = BuyUnitUtils.getUnitChoices(mp, maxEnemyAttack);
        int i;
        for (i = 0; i < unitChoices.size(); i++) {
            if (unitChoices.get(i).isEmpty()) {
                break;
            }
        }
        if (i + ITEMS_AFTER_EMPTY_BUY_AND_ONE < unitChoices.size()) {
            //list and 2 items after empty buy
            unitChoices = unitChoices.subList(0, i + ITEMS_AFTER_EMPTY_BUY_AND_ONE);
        }
        return BuyUnitUtils.buyActionsFromUnitsCombination(unitChoices);
    }

}
