package ru.kachkovsky.wrc_summoners_duel;

import ru.kachkovsky.wrc.eventsgraph.TurnNode;
import ru.kachkovsky.wrc.stage.Action;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc_console_ui.ConsoleUI;
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBeatBaseBotStrategy;
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyBaseStrategy;
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDBuyHeuristicSeparateAfterZeroStrategy;
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class SDConfigAndStartUI {

    public static void start(){
        //        //System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
//        float old = 1;
//        //for (int i = 0; i < 18; i++) {
//            List<List<Unit>> unitChoicesByMp = BuyUnitUtils.getUnitChoices(4,0);
//            for (List<Unit> units : unitChoicesByMp) {
//                System.out.println(units);
//            }
//            System.out.println(unitChoicesByMp.size() / old);
//            old = unitChoicesByMp.size();
//        //}
//        System.exit(-1);

        SummonersDuelSubjectsArea area = SummonersDuelSubjectsAreaFactory.createNewGameArea(7);
//        ArrayList<Unit> li = new ArrayList<>();
//        li.add(UnitUtils.createUnit(false, 1, 0, 1));
//        area.getTeams()[0] = new Player(7, 2, li);
//        ArrayList<Unit> l2 = new ArrayList<>();
//        l2.add(UnitUtils.createUnit(false, 1, 1, 1));
//        //l2.add(UnitUtils.createUnit(false, 2, 0, 1));
//        area.getTeams()[1] = new Player(7, 0, l2);

        TurnNode<SummonersDuelSubjectsArea> node = new TurnNode<>(area);



        SDBuyBaseStrategy sdBuyBaseStrategy = new SDBuyBaseStrategy();
        SDBeatBaseBotStrategy sdBeatBaseBotStrategy = new SDBeatBaseBotStrategy();
        StageActionsStrategyResolver<SummonersDuelSubjectsArea> defaultResolver = SDStrategyUtils.createResolver(sdBuyBaseStrategy, sdBeatBaseBotStrategy);

        StageActionsStrategyResolver<SummonersDuelSubjectsArea> opposingPartiesResolver = SDStrategyUtils.createResolver(
                (a) -> (a.getCurrentTeamIndex() == 0 ? new SDBuyHeuristicSeparateAfterZeroStrategy() : sdBuyBaseStrategy).getActions(a),
                new SDBeatBaseBotStrategy());

        final int indexToHeuristic = 0;
        StageActionsStrategyResolver<SummonersDuelSubjectsArea> opposingParties3TResolver = SDStrategyUtils.createResolver(
                (a) -> {
                    if (a.getCurrentTeamIndex() == indexToHeuristic) {
                        List<Action<SummonersDuelSubjectsArea>> actions = sdBuyBaseStrategy.getActions(a);
                        return actions.subList(0, min(3, actions.size()));
                    } else {
                        return sdBuyBaseStrategy.getActions(a);
                    }
                },
                (a) -> {
                    if (a.getCurrentTeamIndex() == indexToHeuristic) {
                        List<Action<SummonersDuelSubjectsArea>> actions = sdBeatBaseBotStrategy.getActions(a);
                        return actions.subList(0, min(3, actions.size()));
                    } else {
                        return sdBeatBaseBotStrategy.getActions(a);
                    }
                });

        StageActionsStrategyResolver<SummonersDuelSubjectsArea> opposingParties333Resolver = SDStrategyUtils.createResolver(
                (a) -> {
                    if (a.getCurrentTeamIndex() == indexToHeuristic) {
                        List<Action<SummonersDuelSubjectsArea>> actions = sdBuyBaseStrategy.getActions(a);
                        return actions.subList(0,
                                max(min(3, actions.size()), (int) (actions.size() / 1.5f))
                                //min(actions.size(), actions.size() / 2 + 3)-1
                        );
                    } else {
                        return sdBuyBaseStrategy.getActions(a);
                    }
                },
                (a) -> {
                    if (a.getCurrentTeamIndex() == indexToHeuristic) {
                        List<Action<SummonersDuelSubjectsArea>> actions = sdBeatBaseBotStrategy.getActions(a);
                        return actions.subList(0,
                                max(min(3, actions.size()), actions.size() / 3)
                        );
                    } else {
                        return sdBeatBaseBotStrategy.getActions(a);
                    }
                });
        ConsoleUI consoleUI = new ConsoleUI();
        consoleUI.uiForFullGameWithRatesDebug(node, opposingParties3TResolver);
    }

}
