package ru.kachkovsky.wrc_summoners_duel.stage.finish_check;

import ru.kachkovsky.wrc.stage.FinishCheck;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.WinRateUtils;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.List;

public class DSWinNowCheck implements FinishCheck<SummonersDuelSubjectsArea> {

    private boolean currentPlayerWin;

    public DSWinNowCheck(boolean currentPlayerWin) {
        this.currentPlayerWin = currentPlayerWin;
    }

    @Override
    public List<WinRate> checkTeamsWinRate(SummonersDuelSubjectsArea area) {
        int indexToWin = indexToWin(area);
        if (isWin(area)) {
            return WinRateUtils.winRateListForOnlyOneWinner(area, indexToWin);
        }
        return null;
    }

    private int indexToWin(SummonersDuelSubjectsArea area) {
        return currentPlayerWin ? area.getCurrentTeamIndex() : area.getReversePlayerIndex();
    }

    public boolean isWin(SummonersDuelSubjectsArea area) {
        int indexToWin = indexToWin(area);
        int indexToLose = currentPlayerWin ? area.getReversePlayerIndex() : area.getCurrentTeamIndex();
        Player curTeam = area.getTeams()[indexToWin];
        Player otherTeam = area.getTeams()[indexToLose];
        return UnitUtils.summaryAttack(curTeam.getUnits()) >= otherTeam.getHp();
    }
}
