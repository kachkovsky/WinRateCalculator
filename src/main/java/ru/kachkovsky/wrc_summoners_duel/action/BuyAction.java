package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.EventGraphNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;

import java.util.List;

public class BuyAction implements Action<SummonersDuelSubjectsArea> {

    private int playerIndex;
    private List<Unit> unitsToBuy;

    public BuyAction(int playerIndex, List<Unit> unitsToBuy) {
        this.playerIndex = playerIndex;
        this.unitsToBuy = unitsToBuy;
    }

    @Override
    public EventGraphNode<SummonersDuelSubjectsArea> calcAct(SummonersDuelSubjectsArea area) {
        SummonersDuelSubjectsArea areaAfterBuy = SummonersDuelSubjectsAreaFactory.createAreaAfterBuy(area, unitsToBuy);
        return new EventGraphNode<>(areaAfterBuy, areaAfterBuy.getNextStage());
    }

    @Override
    public Subject getSubject() {
        return SummonersDuelSubjectsArea.getStaticContents().getSubjects().get(playerIndex);
    }
}
