package ru.kachkovsky.wrc.stage;

import ru.kachkovsky.wrc.SubjectsArea;

import java.util.List;

public class NumberedSingleStage<T extends SubjectsArea<T>> extends Stage<T> {
    private final int index;

    public NumberedSingleStage(List<FinishCheck<T>> finishChecks, int index) {
        super(finishChecks);
        this.index = index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }
}
