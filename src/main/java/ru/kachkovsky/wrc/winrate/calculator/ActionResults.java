package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

public class ActionResults<T extends SubjectsArea<T>> {

    protected Action<T> action;
    protected TurnNode<T> nodeAfterAction;
    protected List<WinRate> wrList;
    protected boolean indirectWRDependency;

    public ActionResults(Action<T> action, TurnNode<T> nodeAfterAction) {
        this.action = action;
        this.nodeAfterAction = nodeAfterAction;
    }

    public ActionResults(Action<T> action, TurnNode<T> nodeAfterAction, List<WinRate> wrList) {
        this.action = action;
        this.nodeAfterAction = nodeAfterAction;
        this.wrList = wrList;
    }

    public ActionResults(Action<T> action, TurnNode<T> nodeAfterAction, List<WinRate> wrList, boolean indirectWRDependency) {
        this.action = action;
        this.nodeAfterAction = nodeAfterAction;
        this.wrList = wrList;
        this.indirectWRDependency = indirectWRDependency;
    }

    public Action<T> getAction() {
        return action;
    }

    public TurnNode<T> getNodeAfterAction() {
        return nodeAfterAction;
    }

    public List<WinRate> getWrList() {
        return wrList;
    }

    public boolean isIndirectWRDependency() {
        return indirectWRDependency;
    }
}