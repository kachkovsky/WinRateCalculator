package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.TurnNode;
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
    public SummonersDuelSubjectsArea calcAct(SummonersDuelSubjectsArea area) {
        return SummonersDuelSubjectsAreaFactory.createAreaAfterBuy(area, unitsToBuy);
    }

    @Override
    public String toString() {
        return "Buy:" + UnitUtils.unitsToString(unitsToBuy);
    }
}
