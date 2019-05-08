package ru.kachkovsky.wrc_mafia;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.winrate.DefaultWinRateComparator;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.Comparator;
import java.util.List;

public class SimpleMafiaArea implements SubjectsArea {

    private static final TwoRolesMafiaTeamDeterminator TEAM_DETERMINATOR = new TwoRolesMafiaTeamDeterminator();
    private static final DefaultWinRateComparator COMPARATOR = new DefaultWinRateComparator();

    private SimpleMafiaAriaStaticContents staticContents;
    private List<Subject> subjectList;

    public SimpleMafiaArea(SimpleMafiaAriaStaticContents staticContents, List<Subject> subjectList) {
        this.staticContents = staticContents;
        this.subjectList = subjectList;
    }

    public SimpleMafiaAriaStaticContents getStaticContents() {
        return staticContents;
    }

    @Override
    public <T extends SubjectsArea> SubjectTeamAreaDeterminator<T> getTeamDeterminator() {
        return (SubjectTeamAreaDeterminator<T>) TEAM_DETERMINATOR;
        //TODO: determinator for riot police
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
        return subjectList;
    }

    @Override
    public Subject getCurrentSubject() {
        return null;
    }

    @Override
    public String areaToLogString() {
        return "area stub";
    }
}
