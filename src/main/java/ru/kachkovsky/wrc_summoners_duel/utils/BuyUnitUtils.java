package ru.kachkovsky.wrc_summoners_duel.utils;

import ru.kachkovsky.utils.MathUtils;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.action.BuyAction;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;
import ru.kachkovsky.wrc_summoners_duel.player.unit_comparator.UnitListOrderComparator;
import ru.kachkovsky.wrc_summoners_duel.player.unit_comparator.UnitOrderComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BuyUnitUtils {
    private static final UnitOrderComparator UNIT_ORDER_COMPARATOR = new UnitOrderComparator();
    private static final UnitListOrderComparator UNIT_LIST_ORDER_COMPARATOR = new UnitListOrderComparator();


    private static final List<List<Unit>>[] BUY_UNITS_CHOICES_BY_MP = new List[20];
    private static final List<Action<SummonersDuelSubjectsArea>>[] BUY_UNITS_ACTION_CHOICES_BY_MP = new List[20];

    public static List<List<Unit>> getUnitChoicesByMp(int mp) {
        List<List<Unit>> unitChoices = BUY_UNITS_CHOICES_BY_MP[mp];
        if (unitChoices == null) {
            unitChoices = INSTANCE.createUnitsUsingCurrentMp(new ArrayList<>(), mp, mp);
            for (List<Unit> unitChoice : unitChoices) {
                unitChoice.sort(UNIT_ORDER_COMPARATOR);
            }
            unitChoices = unitChoices.stream().distinct().collect(Collectors.toList());
            unitChoices.add(Collections.emptyList());
            unitChoices.sort(UNIT_LIST_ORDER_COMPARATOR);
            BUY_UNITS_CHOICES_BY_MP[mp] = unitChoices;
            return BUY_UNITS_CHOICES_BY_MP[mp];
        }
        return unitChoices;
    }

    public static List<Action<SummonersDuelSubjectsArea>> getUnitChoiceBuyActionsByMp(int mp) {
        List<Action<SummonersDuelSubjectsArea>> list = BUY_UNITS_ACTION_CHOICES_BY_MP[mp];
        if (list == null) {
            list = new ArrayList<>();
            List<List<Unit>> unitsCombination = getUnitChoicesByMp(mp);
            for (List<Unit> units : unitsCombination) {
                list.add(new BuyAction(units));
            }
            BUY_UNITS_ACTION_CHOICES_BY_MP[mp] = list;
        }
        return list;
    }

    private static final BuyUnitUtils INSTANCE = new BuyUnitUtils();

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
