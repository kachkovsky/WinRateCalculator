package ru.kachkovsky.wrc_summoners_duel.stage;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;

import java.util.List;

public class SDFirstBeatStage extends Stage<SummonersDuelSubjectsArea> {
    public SDFirstBeatStage() {
        //TODO: add checks for win now
        super(null);
    }

    @Override
    public List<Action<SummonersDuelSubjectsArea>> getActions(SummonersDuelSubjectsArea area) {
        return null;
    }
}
