package ru.kachkovsky.winratecalculator.test;

import org.junit.Assert;
import org.junit.Test;
import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListFullCalculator;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class SDTest {

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
