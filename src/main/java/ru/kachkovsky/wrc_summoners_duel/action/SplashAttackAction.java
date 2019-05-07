package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

public class SplashAttackAction implements Action<SummonersDuelSubjectsArea> {
    @Override
    public EventGraphNode<SummonersDuelSubjectsArea> calcAct(SummonersDuelSubjectsArea area) {
        SummonersDuelSubjectsArea areaAfterBuy = SummonersDuelSubjectsAreaFactory.createAreaAfterSplashAttack(area);
        return new EventGraphNode<>(areaAfterBuy, areaAfterBuy.getNextStage());
    }

}
