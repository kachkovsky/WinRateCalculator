package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.stage.Stage;

public interface OnlyOneTeamCanDoTurnSubjectArea<T extends OnlyOneTeamCanDoTurnSubjectArea> extends SubjectsArea<T> {
    int getCurrentTeamIndex();
}
