package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.stage.finish_check.DSHeuristicNextTurnFinishCheck;
import ru.kachkovsky.wrc_summoners_duel.stage.finish_check.DSHeuristicNextTurnFinishCheckOnBuy;
import ru.kachkovsky.wrc_summoners_duel.stage.finish_check.DSWinNowCheck;
import ru.kachkovsky.wrc_summoners_duel.stage.finish_check.NextTurnOneUnitFinishCheck;

import java.util.ArrayList;
import java.util.List;

public class SDFinishCheckHelper {

    private static final DSWinNowCheck CURRENT_PLAYER_WIN_NOW_CHECK = new DSWinNowCheck(true);
    private static final NextTurnOneUnitFinishCheck NEXT_TURN_ONE_UNIT_FINISH_CHECK = new NextTurnOneUnitFinishCheck();

    public static List<FinishCheck<SummonersDuelSubjectsArea>> startTurnChecks() {
        ArrayList<FinishCheck<SummonersDuelSubjectsArea>> checks = new ArrayList<>();
        checks.add(CURRENT_PLAYER_WIN_NOW_CHECK);
        checks.add(new DSHeuristicNextTurnFinishCheck(NEXT_TURN_ONE_UNIT_FINISH_CHECK));
        return checks;
    }

    public static List<FinishCheck<SummonersDuelSubjectsArea>> buyFinishChecks() {
        ArrayList<FinishCheck<SummonersDuelSubjectsArea>> checks = new ArrayList<>();
        checks.add(new DSHeuristicNextTurnFinishCheckOnBuy(NEXT_TURN_ONE_UNIT_FINISH_CHECK));
        return checks;
    }
}
