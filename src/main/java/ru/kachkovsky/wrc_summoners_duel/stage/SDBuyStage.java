package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.utils.MathUtils;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.action.BuyAction;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SDBuyStage extends Stage<SummonersDuelSubjectsArea> {
    public SDBuyStage(List<FinishCheck<SummonersDuelSubjectsArea>> finishChecks) {
        super(finishChecks);
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        int mp = area.getTeams()[area.getCurrentPlayerIndex()].getMp();
        return createUnitsActionsRecursive(Collections.emptyList(), mp, mp);
    }

    //TODO: handle enemy splash
    private List<Action<SummonersDuelSubjectsArea>> createUnitsActionsRecursive(List<Unit> createdUnitList, int mp, int maxMpForOneUnit) {
        for (int i = mp; i > 0; i--) {
            //TODO: implement buy
            //createUnitsActionsRecursive(Collections.emptyList(),);
        }
        return Collections.singletonList(new BuyAction(new ArrayList<>()));
    }

    //private List<Action<SummonersDuelSubjectsArea>> createUnitsVariationWithCurrentMp(List<Unit> createdUnitList, int mp) {

    //max mp is 20
    List<Unit> unitsCombinationForMp(int mp) {
        List<Unit> list = new ArrayList<>((mp > 0 ? MathUtils.powI(3, mp - 1) : 0) + (mp > 2 ? MathUtils.powI(3, mp - 3) : 0));
        if (mp > 0) {
            if (mp > 2) {
                genUnitsForMp(list, true, 1, 0, 1, mp - 3);
            }
            genUnitsForMp(list, false, 1, 0, 1, mp - 1);
        }
        return list;
    }

    private void genUnitsForMp(List<Unit> unitVariations, boolean splash, int atk, int def, int hp, int mp) {
        if (mp > 0) {
            genUnitsForMp(unitVariations, splash, atk + 1, def, hp, mp - 1);
            genUnitsForMp(unitVariations, splash, atk, def + 1, hp, mp - 1);
            unitVariations.add(UnitUtils.createUnit(splash, atk, def, hp + mp));
        } else {
            unitVariations.add(UnitUtils.createUnit(splash, atk, def, hp));
        }
    }
}
