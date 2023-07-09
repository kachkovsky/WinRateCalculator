package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.SimpleAreaStaticContents;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminant;
import ru.kachkovsky.wrc.winrate.DefaultWinRateComparator;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.player.PlayerUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils.*;


public class SummonersDuelSubjectsArea implements OnlyOneTeamCanDoTurnSubjectArea<SummonersDuelSubjectsArea> {
    private static final DefaultWinRateComparator COMPARATOR = new DefaultWinRateComparator();
    private static final SimpleAreaStaticContents STATIC_CONTENTS = new SimpleAreaStaticContents();
    private static final SummonersDuelTeamDeterminant TEAM_DETERMINANT = new SummonersDuelTeamDeterminant();

    private final Player[] teams;
    private final int currentPlayerIndex;
    private final int currentPlayerUnitIndex;
    private Stage<SummonersDuelSubjectsArea> currentStage;

    public SummonersDuelSubjectsArea(Player[] teams, int currentPlayerIndex, int currentPlayerUnitIndex, boolean turnStarted, boolean gameStarted) {
        this(teams, currentPlayerIndex, currentPlayerUnitIndex);
        if (turnStarted) {
            this.currentStage = FIRST_STAGE;
            if (!gameStarted) {
                teams[currentPlayerIndex] = PlayerUtils.copyAndTurnMpToPlayer(teams[currentPlayerIndex]);
            }
        } else if (isBuyStage()) {
            this.currentStage = BUY_STAGE;
            //mana up is only after buy stage(only turn start) - for winner calculations
            //teams[currentPlayerIndex] = PlayerUtils.copyAndTurnMpToPlayer(teams[currentPlayerIndex]);
        } else {
            this.currentStage = BEAT_STAGE;
        }
    }

    private SummonersDuelSubjectsArea(Player[] teams, int currentPlayerIndex, int currentPlayerUnitIndex) {
        this.teams = teams;
        this.currentPlayerIndex = currentPlayerIndex;
        this.currentPlayerUnitIndex = currentPlayerUnitIndex;
    }

    public Player[] getTeams() {
        return teams;
    }

    public int getCurrentTeamIndex() {
        return currentPlayerIndex;
    }

    public int getCurrentPlayerUnitIndex() {
        return currentPlayerUnitIndex;
    }

    public Stage<SummonersDuelSubjectsArea> getCurrentStage() {
        return currentStage;
    }

    @Override
    public SubjectTeamAreaDeterminant<SummonersDuelSubjectsArea> getTeamDeterminant() {
        return TEAM_DETERMINANT;
    }

    @Override
    public Comparator<WinRate> getWinRateComparator() {
        return COMPARATOR;
    }

    @Override
    public int getNumberOfTeams() {
        return 2;
    }

    @Override
    public List<Subject> getSubjectList() {
        return STATIC_CONTENTS.getSubjects();
    }

    @Override
    public Subject getCurrentSubject() {
        return getSubjectList().get(currentPlayerIndex);
    }

    @Override
    public String areaToLogString() {
        return PlayerUtils.playersToString(teams);
    }

    public static SimpleAreaStaticContents getStaticContents() {
        return STATIC_CONTENTS;
    }


    public int getReversePlayerIndex() {
        return currentPlayerIndex ^ 1;
    }

    public boolean isBuyStage() {
        return currentPlayerUnitIndex == teams[currentPlayerIndex].getUnits().size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SummonersDuelSubjectsArea)) return false;

        SummonersDuelSubjectsArea area = (SummonersDuelSubjectsArea) o;

        if (currentPlayerIndex != area.currentPlayerIndex) return false;
        if (currentPlayerUnitIndex != area.currentPlayerUnitIndex) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(teams, area.teams)) return false;
        return currentStage.equals(area.currentStage);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(teams);
        result = 31 * result + currentPlayerIndex;
        result = 31 * result + currentPlayerUnitIndex;
        result = 31 * result + currentStage.hashCode();
        return result;
    }
}
