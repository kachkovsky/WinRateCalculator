package ru.kachkovsky.wrc.breadth_first_search;

import ru.kachkovsky.wrc.OnlyOneTeamCanDoTurnSubjectArea;
import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

public class OneTeamDoTurnBreadthFirstSearch {
    private BreadthFirstNode<OnlyOneTeamCanDoTurnSubjectArea> parent;

    public OneTeamDoTurnBreadthFirstSearch(BreadthFirstNode<OnlyOneTeamCanDoTurnSubjectArea> node) {
        this.parent = node;
    }

    public OneTeamDoTurnBreadthFirstSearch(OnlyOneTeamCanDoTurnSubjectArea area) {
        this(new BreadthFirstNode<>(area));
    }

    public BreadthFirstNode<OnlyOneTeamCanDoTurnSubjectArea> getParent() {
        return parent;
    }

    public List<WinRate> doSearch(BreadthFirstNode<OnlyOneTeamCanDoTurnSubjectArea> node) {
        if (node.getTurnNode().getTeamsWinRate() != null) {
            return node.getTurnNode().getTeamsWinRate();
        }
        if (node.getNextTurnNodes() != null) {
            for (BreadthFirstNode<OnlyOneTeamCanDoTurnSubjectArea> nextTurnNode : node.getNextTurnNodes()) {
                doSearch(nextTurnNode);
            }
        } else {
            //TODO: true and use parent top remove recursions
            node.getTurnNode().calcWinRate(false);
            OnlyOneTeamCanDoTurnSubjectArea area = node.getTurnNode().getArea();
            area.

            //TODO: manual calculations for UniversalBreadthFirstSearch
            int currentTeamIndex = area.getCurrentTeamIndex();

        }
        //do calculations for return at the end
    }


}
