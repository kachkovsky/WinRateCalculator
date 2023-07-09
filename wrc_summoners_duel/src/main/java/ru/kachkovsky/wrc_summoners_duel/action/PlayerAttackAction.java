package ru.kachkovsky.wrc_summoners_duel.action;

import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsAreaFactory;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

public class PlayerAttackAction implements Action<SummonersDuelSubjectsArea> {

    @Override
    public SummonersDuelSubjectsArea calcAct(SummonersDuelSubjectsArea area) {
        return SummonersDuelSubjectsAreaFactory.createAreaAfterPlayerAttack(area);
    }

    @Override
    public String toString() {
        return "Attack player";
    }
}
