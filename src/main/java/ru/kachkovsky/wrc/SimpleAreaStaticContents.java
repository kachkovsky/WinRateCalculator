package ru.kachkovsky.wrc;

import ru.kachkovsky.wrc.subject.Subject;

import java.util.ArrayList;
import java.util.List;

public class SimpleAreaStaticContents {
    protected Subject first = new Subject("WHITE");
    protected Subject second = new Subject("BLACK");
    private List<Subject> subjects;

    public SimpleAreaStaticContents() {
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
