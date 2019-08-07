package ru.kachkovsky.wrc_summoners_duel.stage.finish_check;

import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.List;

public class NextTurnOneUnitFinishCheck implements FinishCheck<SummonersDuelSubjectsArea> {
    @Override
    public List<WinRate> checkTeamsWinRate(SummonersDuelSubjectsArea area) {
        int indexToWin = area.getCurrentTeamIndex();
        int indexToLose = area.getReversePlayerIndex();
        Player curTeam = area.getTeams()[indexToWin];
        Player otherTeam = area.getTeams()[indexToLose];


        int def = UnitUtils.minDefForUnitAliveAfterAttackByAllWithMaxEconomic(otherTeam.getUnits());
        int manaToBuyHp = UnitUtils.findMaxAttack(otherTeam.getUnits()) - def / 2;
        if (curTeam.getMp() > def + manaToBuyHp) {
            int atk = curTeam.getMp() - def - manaToBuyHp;
            if (atk > otherTeam.getHp()) {
                return WinRateUtils.winRateListForOnlyOneWinner(area, indexToWin);
            }
        }
        return null;
    }
}
