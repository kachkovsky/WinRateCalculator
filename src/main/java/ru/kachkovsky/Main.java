package ru.kachkovsky;

import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListForTeamCalculator;
import ru.kachkovsky.wrc.winrate.calculator.WinRateListFullCalculator;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        SummonersDuelSubjectsArea area = SummonersDuelSubjectsAreaFactory.createNewGameArea(4);
//        ArrayList<Unit> li = new ArrayList<>();
//        li.add(UnitUtils.createUnit(false, 2, 0, 1));
//        area.getTeams()[0] = new Player(3, 2, li);
//        ArrayList<Unit> l2 = new ArrayList<>();
//        l2.add(UnitUtils.createUnit(false, 2, 0, 1));
//        l2.add(UnitUtils.createUnit(false, 2, 0, 1));
//        area.getTeams()[1] = new Player(3, 0, l2);
        TurnNode<SummonersDuelSubjectsArea> node = new TurnNode<>(area, area.getCurrentStage());

        ConsoleUI consoleUI = new ConsoleUI();

//        consoleUI.uiForFullGame(node);
//        System.exit(0);
        int i = 0;

        Scanner scanner = new Scanner(System.in);
        WinRateListForTeamCalculator calculator = new WinRateListForTeamCalculator();
        Map<Action<SummonersDuelSubjectsArea>, TurnNode<SummonersDuelSubjectsArea>> actionEventGraphNodeMap;
        while ((actionEventGraphNodeMap = node.calcWinRate()) != null) {
            long t = System.currentTimeMillis();
            List<WinRateListFullCalculator.ActionResults<SummonersDuelSubjectsArea>> actionResults = calculator.eventGraphMapToWinRateMapOnlyOneTeam(node.calcWinRate(), area);
            System.out.println("Time: " + (System.currentTimeMillis() - t));
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
