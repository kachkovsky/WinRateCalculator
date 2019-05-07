package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.subject.Subject;

import java.util.ArrayList;
import java.util.List;

public class SimpleAriaStaticContents {
    protected Subject first;
    protected Subject second;
    private List<Subject> subjects;

    public SimpleAriaStaticContents() {
        subjects = new ArrayList<>();
        subjects.add(first);
        subjects.add(second);
    }

    public int getTeamIndex(Subject s) {
        return subjects.indexOf(s);
    }


    public List<Subject> getSubjects() {
        return subjects;
    }
}
