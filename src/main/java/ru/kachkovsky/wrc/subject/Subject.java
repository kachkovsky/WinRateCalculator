package ru.kachkovsky.wrc.subject;

public class Subject {
    private String name;

    public Subject(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
