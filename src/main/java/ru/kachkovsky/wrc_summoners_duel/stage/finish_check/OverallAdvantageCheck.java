package ru.kachkovsky.wrc_summoners_duel.stage.finish_check;

import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.Unit;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.List;

public class OverallAdvantageCheck implements FinishCheck<SummonersDuelSubjectsArea> {

    @Override
    public List<WinRate> checkTeamsWinRate(SummonersDuelSubjectsArea area) {
        if (isWin(area)) {
            return WinRateUtils.winRateListForOnlyOneWinner(area, area.getCurrentTeamIndex());
        }
        return null;
    }

    private boolean isWin(SummonersDuelSubjectsArea area) {
        int indexToWin = area.getCurrentTeamIndex();
        int indexToLose = area.getReversePlayerIndex();
        Player curTeam = area.getTeams()[indexToWin];
        Player otherTeam = area.getTeams()[indexToLose];
        if (curTeam.getHp() < otherTeam.getHp() || (curTeam.getMp() - 2) < otherTeam.getMp()) {
            return false;
        }
        List<Unit> cList = curTeam.getUnits();
        List<Unit> rList = otherTeam.getUnits();

        return UnitUtils.overallAdvantageOfFirstSortedListCheck(cList, rList);
    }
}