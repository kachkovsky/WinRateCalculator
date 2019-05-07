package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.stage.Stage;

import java.util.List;

public class SDStage extends Stage<SummonersDuelArea> {
    public SDStage(List<FinishCheck<SummonersDuelArea>> finishChecks) {
        super(finishChecks);
    }

    @Override
    public List<Action<SummonersDuelArea>> getActions(SummonersDuelArea area) {
        return null;
    }
}
