package ru.kachkovsky.wrc_summoners_duel.stage.strategy;

import ru.kachkovsky.wrc.stage.NumberedSingleStage;
import ru.kachkovsky.wrc.stage.Stage;
import ru.kachkovsky.wrc.stage.strategy.SimpleStageActionsStrategyResolver;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategy;
import ru.kachkovsky.wrc.stage.strategy.StageActionsStrategyResolver;
import ru.kachkovsky.wrc_summoners_duel.SummonersDuelSubjectsArea;
import ru.kachkovsky.wrc_summoners_duel.stage.SDFinishCheckHelper;

public class SDStrategyUtils {

    public static final Stage<SummonersDuelSubjectsArea> BUY_STAGE = new NumberedSingleStage<>(SDFinishCheckHelper.buyFinishChecks(), 1);
    public static final Stage<SummonersDuelSubjectsArea> BEAT_STAGE = new NumberedSingleStage<>(null, 2);
    public static final Stage<SummonersDuelSubjectsArea> FIRST_STAGE = new NumberedSingleStage<>(SDFinishCheckHelper.startTurnChecks(), 3);

    public static StageActionsStrategyResolver<SummonersDuelSubjectsArea> createResolver(StageActionsStrategy<SummonersDuelSubjectsArea> buyStrategy,
                                                                                         StageActionsStrategy<SummonersDuelSubjectsArea> beatStrategy) {
        SimpleStageActionsStrategyResolver<SummonersDuelSubjectsArea> resolver = new SimpleStageActionsStrategyResolver<>();
        resolver.put(FIRST_STAGE, (area) -> ((area.isBuyStage()) ? buyStrategy : beatStrategy).getActions(area));
        resolver.put(BEAT_STAGE, beatStrategy);
        resolver.put(BUY_STAGE, buyStrategy);
        return resolver;
    }
}
