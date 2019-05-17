package ru.kachkovsky.wrc_summoners_duel.stage.finish_check;

import ru.kachkovsky.utils.MathUtils;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.UnitUtils;

import java.util.List;

public class DSHeuristicNextTurnFinishCheck extends DSWinNowCheck {
    public DSHeuristicNextTurnFinishCheck() {
        super(false);
    }

    @Override
    public List<WinRate> checkTeamsWinRate(SummonersDuelSubjectsArea area) {
        //if another team can't win next turn
        if (!isWin(area)) {
            int indexToWin = area.getCurrentPlayerIndex();
            int indexToLose = area.getReversePlayerIndex();

            Player curTeam = area.getTeams()[indexToWin];
            Player otherTeam = area.getTeams()[indexToLose];

            int splashUnitIndex = UnitUtils.findSplashUnitIndex(otherTeam.getUnits());
            if (splashUnitIndex == MathUtils.INDEX_NOT_FOUND) {
                curTeam.getUnits().size() + curTeam.getMp()> otherTeam.getUnits().size() + otherTeam.getHp()
            }


            //return UnitUtils.summaryAttack(curTeam.getUnits()) >= otherTeam.getHp();
        }
        return null;
    }
}
