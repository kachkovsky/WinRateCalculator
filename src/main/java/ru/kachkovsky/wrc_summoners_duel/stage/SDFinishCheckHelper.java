package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.stage.finish_check.DSWinNowCheck;

import java.util.ArrayList;
import java.util.List;

public class SDFinishCheckHelper {

    private static final DSWinNowCheck CURRENT_PLAYER_WIN_NOW_CHECK = new DSWinNowCheck(true);

    public static List<FinishCheck<SummonersDuelSubjectsArea>> startTurnChecks() {
        ArrayList<FinishCheck<SummonersDuelSubjectsArea>> checks = new ArrayList<>();
        checks.add(CURRENT_PLAYER_WIN_NOW_CHECK);
        return checks;
    }
}
