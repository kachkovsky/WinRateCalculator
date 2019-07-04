package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;

public class UnitAttackAction implements Action<SummonersDuelSubjectsArea> {

    private int enemyUnitAttacked;

    public UnitAttackAction(int enemyUnitAttacked) {
        this.enemyUnitAttacked = enemyUnitAttacked;
    }

    @Override
    public EventGraphNode<SummonersDuelSubjectsArea> calcAct(SummonersDuelSubjectsArea area) {
        SummonersDuelSubjectsArea areaAfterBuy = SummonersDuelSubjectsAreaFactory.createAreaAfterUnitAttack(area, enemyUnitAttacked);
        return new EventGraphNode<>(areaAfterBuy, areaAfterBuy.getNextStage());
    }

    @Override
    public String toString() {
        return "enemy index to attack:" + enemyUnitAttacked + "  " + super.toString();
    }
}
