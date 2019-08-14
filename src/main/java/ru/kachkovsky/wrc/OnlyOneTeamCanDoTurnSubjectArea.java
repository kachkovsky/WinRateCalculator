package ru.kachkovsky.wrc;

public interface OnlyOneTeamCanDoTurnSubjectArea<T extends OnlyOneTeamCanDoTurnSubjectArea<T>> extends SubjectsArea<T> {
    int getCurrentTeamIndex();
}
