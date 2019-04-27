package ru.kachkovsky.wrc_mafia;

import ru.kachkovsky.wrc.subject.Subject;

import java.util.ArrayList;
import java.util.List;

public class SimpleMafiaAriaStaticContents {
    protected Subject maf;
    protected Subject honest;
    private List<Subject> subjects;

    public SimpleMafiaAriaStaticContents() {
        subjects = new ArrayList<>();
        subjects.add(honest);
        subjects.add(maf);
    }

    public int getTeamIndex(Subject s) {
        return subjects.indexOf(s);
    }

}
