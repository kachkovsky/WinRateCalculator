package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.stage.Stage;

public interface OnlyOneTeamCanDoTurnSubjectArea<T extends OnlyOneTeamCanDoTurnSubjectArea<T>> extends SubjectsArea<T> {
    int getCurrentTeamIndex();
}
