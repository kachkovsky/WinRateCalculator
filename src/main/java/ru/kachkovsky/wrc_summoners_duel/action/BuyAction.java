package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.List;

public class BuyAction implements Action<SummonersDuelSubjectsArea> {

    private List<Unit> unitsToBuy;

    public BuyAction(List<Unit> unitsToBuy) {
        this.unitsToBuy = unitsToBuy;
    }

    @Override
    public EventGraphNode<SummonersDuelSubjectsArea> calcAct(SummonersDuelSubjectsArea area) {
        SummonersDuelSubjectsArea areaAfterBuy = SummonersDuelSubjectsAreaFactory.createAreaAfterBuy(area, unitsToBuy);
        return new EventGraphNode<>(areaAfterBuy, areaAfterBuy.getNextStage());
    }

    @Override
    public String toString() {
        return "Buy:" + UnitUtils.unitsToString(unitsToBuy);
    }
}
