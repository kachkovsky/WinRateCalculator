package ru.kachkovsky.wrc.breadth_first_search;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;

import java.util.List;

public class BreadthFirstNode<T extends SubjectsArea> {

    private List<BreadthFirstNode<T>> nextTurnNodes;
    private TurnNode<T> turnNode;
    //can be calculated once. No need to keep this value
    //private Integer monoTeamActions;

    public BreadthFirstNode(T area) {
        this(new TurnNode<>(area));
    }

    public BreadthFirstNode(TurnNode<T> turnNode) {
        this.turnNode = turnNode;
    }

    public TurnNode<T> getTurnNode() {
        return turnNode;
    }

    public List<BreadthFirstNode<T>> getNextTurnNodes() {
        return nextTurnNodes;
    }

    public void setNextTurnNodes(List<BreadthFirstNode<T>> nextTurnNodes) {
        this.nextTurnNodes = nextTurnNodes;
    }
}
