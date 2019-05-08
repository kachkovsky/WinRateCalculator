package ru.kachkovsky;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

public class Main {

    public static void main(String[] args) {

        SummonersDuelSubjectsArea area = SummonersDuelSubjectsAreaFactory.createNewGameArea(2);
        EventGraphNode<SummonersDuelSubjectsArea> node = new EventGraphNode<>(area, area.getNextStage());
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.uiForFullGame(node);
    }
}
