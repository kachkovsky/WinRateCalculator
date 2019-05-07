package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

public class AttackAction implements Action<SummonersDuelSubjectsArea> {

    private int enemyUnitAttacked;

    public AttackAction(int enemyUnitAttacked) {
        this.enemyUnitAttacked = enemyUnitAttacked;
    }

    @Override
    public EventGraphNode<SummonersDuelSubjectsArea> calcAct(SummonersDuelSubjectsArea area) {
        SummonersDuelSubjectsArea areaAfterBuy = SummonersDuelSubjectsAreaFactory.createAreaAfterAttack(area, enemyUnitAttacked);
        return new EventGraphNode<>(areaAfterBuy, areaAfterBuy.getNextStage());
    }

}