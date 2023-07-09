package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

public class SplashAttackAction implements Action<SummonersDuelSubjectsArea> {
    @Override
    public SummonersDuelSubjectsArea calcAct(SummonersDuelSubjectsArea area) {
        return SummonersDuelSubjectsAreaFactory.createAreaAfterSplashAttack(area);
    }

}
