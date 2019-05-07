package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.subject.Subject;
import ru.kachkovsky.wrc.team.SubjectTeamAreaDeterminator;
import ru.kachkovsky.wrc.team.TwoRolesStaticTeamDeterminator;
import ru.kachkovsky.wrc_mafia.SimpleMafiaArea;

public class SummonersDuelTeamDeterminator extends TwoRolesStaticTeamDeterminator<SummonersDuelArea> {
    @Override
    public int getTeamIndex(SummonersDuelArea area, Subject subject) {
        return area.getStaticContents().getTeamIndex(subject);
    }
}
