package ru.kachkovsky.winratecalculator.test;

import org.junit.Assert;
import org.junit.Test;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListFullCalculator;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SDTest {

    @Test
    public void areaEquality() {
        Assert.assertEquals(SummonersDuelSubjectsAreaFactory.createNewGameArea(2), SummonersDuelSubjectsAreaFactory.createNewGameArea(2));
        Assert.assertEquals(SummonersDuelSubjectsAreaFactory.createNewGameArea(3), SummonersDuelSubjectsAreaFactory.createNewGameArea(3));
        Assert.assertNotEquals(SummonersDuelSubjectsAreaFactory.createNewGameArea(3), SummonersDuelSubjectsAreaFactory.createNewGameArea(2));
    }

    @Test
    public void newUnit() {
        Unit unit = UnitUtils.createUnit(false, 1, 0, 0);
        Assert.assertEquals(false, unit.hasSplash());
        Assert.assertEquals(1, unit.getAtk());
        Assert.assertEquals(0, unit.getDef());
        Assert.assertEquals(0, unit.getHp());

        unit = UnitUtils.createUnit(true, 1, 1, 1);
        Assert.assertEquals(true, unit.hasSplash());
        Assert.assertEquals(1, unit.getAtk());
        Assert.assertEquals(1, unit.getDef());
        Assert.assertEquals(1, unit.getHp());

        unit = UnitUtils.createUnit(false, 1, 7, 15);
        Assert.assertEquals(false, unit.hasSplash());
        Assert.assertEquals(1, unit.getAtk());
        Assert.assertEquals(7, unit.getDef());
        Assert.assertEquals(15, unit.getHp());

        unit = UnitUtils.createUnit(true, 55, 1, 44);
        Assert.assertEquals(true, unit.hasSplash());
        Assert.assertEquals(55, unit.getAtk());
        Assert.assertEquals(1, unit.getDef());
        Assert.assertEquals(44, unit.getHp());
    }

    @Test
    public void calc() {
        SummonersDuelSubjectsArea area = SummonersDuelSubjectsAreaFactory.createNewGameArea(2);
        EventGraphNode<SummonersDuelSubjectsArea> node = new EventGraphNode<>(area, area.getNextStage());

        Map<Action<SummonersDuelSubjectsArea>, EventGraphNode<SummonersDuelSubjectsArea>> actionEventGraphNodeMap = node.calcWinRate();
        EventGraphNode<SummonersDuelSubjectsArea> next = actionEventGraphNodeMap.values().iterator().next();

        Map<Action<SummonersDuelSubjectsArea>, EventGraphNode<SummonersDuelSubjectsArea>> innerMap = next.calcWinRate();

        Iterator<Map.Entry<Action<SummonersDuelSubjectsArea>, EventGraphNode<SummonersDuelSubjectsArea>>> iterator = innerMap.entrySet().iterator();
        iterator.next();
        iterator.next();
        iterator.next();
        Map.Entry<Action<SummonersDuelSubjectsArea>, EventGraphNode<SummonersDuelSubjectsArea>> map = iterator.next();
        WinRateListFullCalculator c = new WinRateListFullCalculator();
        //Map<Action<SummonersDuelSubjectsArea>, List<WinRate>> actionListMap = c.eventGraphMapToWinRateMap(innerMap, next.getArea());
        //Assert.assertTrue(actionListMap!=null);
    }

    @Test
    public void unitsUniqueAttacked() {
        Unit u1, u2, u3, u4, u0;
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(false, 1, 5, 2));
        li.add(u0 = UnitUtils.createUnit(true, 5, 3, 2));
        li.add(u1 = UnitUtils.createUnit(true, 5, 4, 1));
        li.add(UnitUtils.createUnit(false, 1, 22, 2));
        li.add(UnitUtils.createUnit(false, 1, 22, 4));
        li.add(u2 = UnitUtils.createUnit(true, 1, 6, 1));
        li.add(u3 = UnitUtils.createUnit(false, 4, 6, 5));
        li.add(UnitUtils.createUnit(false, 1, 1, 5));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));
        li.add(UnitUtils.createUnit(false, 1, 7, 4));
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(false, 1, 2, 1));

        List<Unit> units = UnitUtils.unitsUniqueAttacked(7, li);
        Assert.assertEquals(u0, units.get(0));
        Assert.assertEquals(u1, units.get(1));
        Assert.assertEquals(u2, units.get(2));
        Assert.assertEquals(u3, units.get(3));
        Assert.assertEquals(units.size(), 4);
    }

    @Test
    public void unitsUniqueAttacked1() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));
        List<Unit> units = UnitUtils.unitsUniqueAttacked(7, li);
        Assert.assertEquals(units.size(), 1);

        li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));
        units = UnitUtils.unitsUniqueAttacked(7, li);

        Assert.assertEquals(units.size(), 1);
    }

    @Test
    public void checkEqualsSortedLists() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));

        ArrayList<Unit> li2 = new ArrayList<>();
        li2.add(UnitUtils.createUnit(true, 4, 2, 2));
        li2.add(UnitUtils.createUnit(true, 4, 0, 2));
        Assert.assertTrue(UnitUtils.overallAdvantageOfFirstSortedListCheck(li, li2));
    }

    @Test
    public void checkEmptySortedLists() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));

        ArrayList<Unit> li2 = new ArrayList<>();
        Assert.assertTrue(UnitUtils.overallAdvantageOfFirstSortedListCheck(li, li2));
    }

    @Test
    public void checkMoreSortedLists() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 1, 1, 1));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));
        li.add(UnitUtils.createUnit(true, 4, 2, 2));

        ArrayList<Unit> li2 = new ArrayList<>();

        li2.add(UnitUtils.createUnit(true, 4, 0, 2));
        li2.add(UnitUtils.createUnit(true, 4, 2, 2));

        Assert.assertTrue(UnitUtils.overallAdvantageOfFirstSortedListCheck(li, li2));
    }

    @Test
    public void checkFalseSortedLists() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 2, 2));
        li.add(UnitUtils.createUnit(true, 4, 0, 2));

        ArrayList<Unit> li2 = new ArrayList<>();
        li2.add(UnitUtils.createUnit(true, 4, 0, 2));
        li2.add(UnitUtils.createUnit(true, 4, 2, 2));
        Assert.assertFalse(UnitUtils.overallAdvantageOfFirstSortedListCheck(li, li2));
    }

    @Test
    public void checkFalseMoreSortedLists() {
        ArrayList<Unit> li = new ArrayList<>();
        li.add(UnitUtils.createUnit(true, 4, 0, 2));
        li.add(UnitUtils.createUnit(true, 4, 2, 2));

        ArrayList<Unit> li2 = new ArrayList<>();

        li2.add(UnitUtils.createUnit(true, 1, 1, 1));
        li2.add(UnitUtils.createUnit(true, 4, 0, 2));
        li2.add(UnitUtils.createUnit(true, 4, 2, 2));
        Assert.assertFalse(UnitUtils.overallAdvantageOfFirstSortedListCheck(li, li2));
    }
}
