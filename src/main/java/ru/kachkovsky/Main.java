package ru.kachkovsky;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListForTeamCalculator;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListFullCalculator;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        SummonersDuelSubjectsArea area = SummonersDuelSubjectsAreaFactory.createNewGameArea(2);
//        area.getTeams()[0] = new Player(3, 0, new ArrayList<>());
//        area.getTeams()[1] = new Player(3, 2, new ArrayList<>());
        EventGraphNode<SummonersDuelSubjectsArea> node = new EventGraphNode<>(area, area.getNextStage());

        ConsoleUI consoleUI = new ConsoleUI();

//        consoleUI.uiForFullGame(node);
//        System.exit(0);
        int i = 0;

        Scanner scanner = new Scanner(System.in);
        WinRateListForTeamCalculator calculator = new WinRateListForTeamCalculator();
        Map<Action<SummonersDuelSubjectsArea>, EventGraphNode<SummonersDuelSubjectsArea>> actionEventGraphNodeMap;
        while ((actionEventGraphNodeMap = node.calcWinRate()) != null) {
            List<WinRateListFullCalculator.ActionResults<SummonersDuelSubjectsArea>> actionResults = calculator.eventGraphMapToWinRateMapOnlyOneTeam(node.calcWinRate(), area);
            //Map<Action<SummonersDuelSubjectsArea>, List<WinRate>> actionListMap = calculator.eventGraphMapToWinRateMap(node.calcWinRate(), area);
            System.out.println("$$$Game calculated$$$");
            //for (Map.Entry<Action<SummonersDuelSubjectsArea>, List<WinRate>> actionListEntry : actionListMap.entrySet()) {
            for (WinRateListFullCalculator.ActionResults<SummonersDuelSubjectsArea> actionResult : actionResults) {
                i++;
                consoleUI.printAction(i, actionResult.getAction(), "");
                consoleUI.printWinRateList(actionResult.getWrList(), "");
            }
            consoleUI.writeCurrentTurn(node);
            node = consoleUI.uiForTurn(scanner, node);
            area = node.getArea();
        }

        consoleUI.printWinRateList(node.getTeamsWinRate(), "FIN ");
    }
}
