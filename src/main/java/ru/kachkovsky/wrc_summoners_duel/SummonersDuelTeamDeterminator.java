package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.TwoRolesStaticTeamDeterminator;

public class SummonersDuelTeamDeterminator extends TwoRolesStaticTeamDeterminator<SummonersDuelSubjectsArea> {
    @Override
    public int getTeamIndex(SummonersDuelSubjectsArea area, Subject subject) {
        return SummonersDuelSubjectsArea.getStaticContents().getTeamIndex(subject);
    }
}
