package ru.kachkovsky.wrc.team;

import ru.kachkovsky.wrc.SubjectsArea;

import javax.security.auth.Subject;

public interface TeamDeterminator<T extends SubjectsArea> {
    boolean isInTeam(T area, Subject subject);
}
