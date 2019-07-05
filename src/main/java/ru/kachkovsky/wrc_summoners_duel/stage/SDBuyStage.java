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
        int mp = area.getTeams()[area.getCurrentTeamIndex()].getMp();
        List<List<Unit>> unitsCombination = createUnitsUsingCurrentMp(new ArrayList<>(), mp, mp);
        List<Action<SummonersDuelSubjectsArea>> list = new ArrayList<>();
        for (List<Unit> units : unitsCombination) {
            list.add(new BuyAction(units));
        }
        list.add(new BuyAction(Collections.emptyList()));
        return list;
    }

    private List<List<Unit>> createUnitsUsingCurrentMp(List<List<Unit>> createdUnitListVariation, int mp, int maxMpForOneUnit) {
        List<List<Unit>> resultList = new ArrayList<>();
        for (int i = maxMpForOneUnit; i > 0; i--) {
            List<List<Unit>> list = unitsListIntoMultipleCombinationOfUnitLists(oneUnitCombinationForMp(i), createdUnitListVariation);
            resultList.addAll(createUnitsUsingCurrentMp(list, mp - i, Math.min(i, mp - i)));
            resultList.addAll(list);
        }
        return resultList;
    }

    //max mp is 20
    private List<Unit> oneUnitCombinationForMp(int mp) {
        List<Unit> list = new ArrayList<>((mp > 0 ? MathUtils.powI(3, mp - 1) : 0) + (mp > 2 ? MathUtils.powI(3, mp - 3) : 0));
        if (mp > 0) {
            if (mp > 2) {
                genOneUnitForMp(list, true, 1, 0, 1, mp - 3, true);
            }
            genOneUnitForMp(list, false, 1, 0, 1, mp - 1, true);
        }
        return list;
    }

    private void genOneUnitForMp(List<Unit> unitVariations, boolean splash, int atk, int def, int hp, int mp, boolean withAtk) {
        if (mp > 0) {
            if (withAtk) {
                genOneUnitForMp(unitVariations, splash, atk + 1, def, hp, mp - 1, true);
            }
            genOneUnitForMp(unitVariations, splash, atk, def + 1, hp, mp - 1, false);
            unitVariations.add(UnitUtils.createUnit(splash, atk, def, hp + mp * 2));
        } else {
            unitVariations.add(UnitUtils.createUnit(splash, atk, def, hp));
        }
    }

    private static List<List<Unit>> unitsListIntoMultipleCombinationOfUnitLists(List<Unit> unitList, List<List<Unit>> unitListCombination) {
        List<List<Unit>> resultList = new ArrayList<>();
        if (unitListCombination.isEmpty()) {
            resultList.addAll(unitListCombination);
            for (Unit unit : unitList) {
                resultList.add(Collections.singletonList(unit));
            }
        } else {

            for (Unit unit : unitList) {
                for (List<Unit> units : unitListCombination) {
                    ArrayList<Unit> list = new ArrayList<>(units.size() + 1);
                    list.addAll(units);
                    list.add(unit);
                    resultList.add(list);
                }
            }
        }
        return resultList;
    }

}
