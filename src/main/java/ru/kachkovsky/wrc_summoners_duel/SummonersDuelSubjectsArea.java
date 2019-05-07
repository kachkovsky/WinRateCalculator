package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.SimpleAriaStaticContents;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.DefaultWinRateComparator;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc_summoners_duel.player.Player;

import java.util.Comparator;
import java.util.List;


public class SummonersDuelSubjectsArea implements SubjectsArea {
    private static DefaultWinRateComparator COMPARATOR = new DefaultWinRateComparator();
    private static SimpleAriaStaticContents STATIC_CONTENTS = new SimpleAriaStaticContents();
    private static SummonersDuelTeamDeterminator TEAM_DETERMINATOR = new SummonersDuelTeamDeterminator();

    private Player[] teams = new Player[2];
    private int currentPlayerIndex;
    private int currentPlayerUnitIndex;
    private Stage<SummonersDuelSubjectsArea> nextStage;

    public SummonersDuelSubjectsArea(Player[] teams, int currentPlayerIndex, int currentPlayerUnitIndex, Stage<SummonersDuelSubjectsArea> nextStage) {
        this.teams = teams;
        this.currentPlayerIndex = currentPlayerIndex;
        this.currentPlayerUnitIndex = currentPlayerUnitIndex;
        this.nextStage = nextStage;
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
