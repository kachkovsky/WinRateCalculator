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
    private static ArrayList<FinishCheck<SummonersDuelSubjectsArea>> START_TURN_CHECKS = new ArrayList<>();

    static {
        START_TURN_CHECKS.add(CURRENT_PLAYER_WIN_NOW_CHECK);
        START_TURN_CHECKS.add(new DSHeuristicNextTurnFinishCheck(NEXT_TURN_ONE_UNIT_FINISH_CHECK));
    }

    private static ArrayList<FinishCheck<SummonersDuelSubjectsArea>> BUY_FINISH_CHECKS = new ArrayList<>();

    static {
        BUY_FINISH_CHECKS.add(new DSHeuristicNextTurnFinishCheckOnBuy(NEXT_TURN_ONE_UNIT_FINISH_CHECK));
    }

    public static List<FinishCheck<SummonersDuelSubjectsArea>> startTurnChecks() {
        return START_TURN_CHECKS;
    }

    public static List<FinishCheck<SummonersDuelSubjectsArea>> buyFinishChecks() {
        return BUY_FINISH_CHECKS;
    }
}
