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

import java.util.Iterator;
import java.util.Map;


public class SDTest {

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
}
