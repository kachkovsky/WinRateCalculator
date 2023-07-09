package ru.kachkovsky.wrc.winrate.calculator;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;

import java.util.List;

public class ActionResults<T extends SubjectsArea<T>> {

    public ActionResults(Action<T> action, TurnNode<T> nodeAfterAction) {
        this.action = action;
        this.nodeAfterAction = nodeAfterAction;
    }

    public ActionResults(Action<T> action, TurnNode<T> nodeAfterAction, List<WinRate> wrList) {
        this.action = action;
        this.nodeAfterAction = nodeAfterAction;
        this.wrList = wrList;
    }

    protected Action<T> action;
    protected TurnNode<T> nodeAfterAction;
    protected List<WinRate> wrList;

    public Action<T> getAction() {
        return action;
    }

    public TurnNode<T> getNodeAfterAction() {
        return nodeAfterAction;
    }

    public List<WinRate> getWrList() {
        return wrList;
    }
}