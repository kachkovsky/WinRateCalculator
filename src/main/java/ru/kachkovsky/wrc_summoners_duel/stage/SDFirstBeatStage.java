package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;

import java.util.List;

public class SDFirstBeatStage extends Stage<SummonersDuelSubjectsArea> {
    private SDBeatStage innerBeatStage;
    private SDBuyStage innerBuyStage;

    private static final List<FinishCheck<SummonersDuelSubjectsArea>> TURN_START_FINISH_CHECKS = SDFinishCheckHelper.startTurnChecks();

    public SDFirstBeatStage(SDBeatStage innerBeatStage, SDBuyStage innerBuyStage) {
        super(TURN_START_FINISH_CHECKS);
        this.innerBeatStage = innerBeatStage;
        this.innerBuyStage = innerBuyStage;
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        if (area.isBuyStage()) {
            return innerBuyStage.getActions(area);
        } else {
            return innerBeatStage.getActions(area);
        }
    }
}
