package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;

import javax.security.auth.Subject;

public interface TeamDeterminator<T extends SubjectsArea> {
    int getTeamIndex(T area, Subject subject);
}
