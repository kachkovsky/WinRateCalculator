package ru.kachkovsky;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;
import ru.kachkovsky.wrc_summoners_duel.SDStage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelArea;

public class Main {

    public static void main(String[] args) {
        EventGraphNode<SummonersDuelArea> node = new EventGraphNode<>(new SummonersDuelArea(), new SDStage(null));
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.uiForFullGame(node);
    }
}
