package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.SimpleAriaStaticContents;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.DefaultWinRateComparator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;
import java.util.List;


public class SummonersDuelArea implements SubjectsArea {
    private static DefaultWinRateComparator COMPARATOR = new DefaultWinRateComparator();
    private static SimpleAriaStaticContents STATIC_CONTENTS = new SimpleAriaStaticContents();
    private static SummonersDuelTeamDeterminator TEAM_DETERMINATOR = new SummonersDuelTeamDeterminator();

    @Override
    public SubjectTeamAreaDeterminator<SummonersDuelArea> getTeamDeterminator() {
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

    public static SimpleAriaStaticContents getStaticContents() {
        return STATIC_CONTENTS;
    }
}
