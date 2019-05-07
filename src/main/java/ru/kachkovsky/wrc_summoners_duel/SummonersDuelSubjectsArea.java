package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.SimpleAriaStaticContents;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.DefaultWinRateComparator;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc_summoners_duel.player.Player;
import ru.kachkovsky.wrc_summoners_duel.stage.SDBuyStage;
import ru.kachkovsky.wrc_summoners_duel.stage.SDFirstBeatStage;

import java.util.Comparator;
import java.util.List;


public class SummonersDuelSubjectsArea implements SubjectsArea {
    private static DefaultWinRateComparator COMPARATOR = new DefaultWinRateComparator();
    private static SimpleAriaStaticContents STATIC_CONTENTS = new SimpleAriaStaticContents();
    private static SummonersDuelTeamDeterminator TEAM_DETERMINATOR = new SummonersDuelTeamDeterminator();

    private static SDFirstBeatStage FIRST_STAGE = new SDFirstBeatStage();
    private static SDBuyStage BEAT_STAGE = new SDBuyStage(null);
    private static SDBuyStage BUY_STAGE = new SDBuyStage(null);
    private Player[] teams;
    private int currentPlayerIndex;
    private int currentPlayerUnitIndex;
    private Stage<SummonersDuelSubjectsArea> nextStage;

    public SummonersDuelSubjectsArea(Player[] teams, int currentPlayerIndex, int currentPlayerUnitIndex, boolean turnStarted) {
        this.teams = teams;
        this.currentPlayerIndex = currentPlayerIndex;
        this.currentPlayerUnitIndex = currentPlayerUnitIndex;
        if (turnStarted) {
            this.nextStage = FIRST_STAGE;
        } else if (currentPlayerUnitIndex == teams[currentPlayerIndex].getUnits().size()) {
            this.nextStage = BUY_STAGE;
        } else {
            this.nextStage = BEAT_STAGE;
        }
    }

    public Player[] getTeams() {
        return teams;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public int getCurrentPlayerUnitIndex() {
        return currentPlayerUnitIndex;
    }

    public Stage<SummonersDuelSubjectsArea> getNextStage() {
        return nextStage;
    }

    @Override
    public SubjectTeamAreaDeterminator<SummonersDuelSubjectsArea> getTeamDeterminator() {
        return TEAM_DETERMINATOR;
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

    public static SimpleAriaStaticContents getStaticContents() {
        return STATIC_CONTENTS;
    }


    public int reversePlayerIndex() {
        return currentPlayerIndex ^ 1;
    }
}