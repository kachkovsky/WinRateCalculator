package ru.kachkovsky.wrc_summoners_duel.winrate;

import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.winrate.WinRate;
import ru.kachkovsky.wrc.winrate.calculator.cache.LFUWRCacheHelper;
import ru.kachkovsky.wrc_summoners_duel.stage.strategy.SDStrategyUtils;

import java.util.List;

public class LFUSummonerDuelWRCacheHelper extends LFUWRCacheHelper {

    public List<WinRate> find(SubjectsArea area) {
        return super.find(area);
    }

    public List<WinRate> put(SubjectsArea area, List<WinRate> wrList) {
        if (SDStrategyUtils.FIRST_STAGE.equals(area.getCurrentStage())) {
            return null;
        }
        return super.put(area, wrList);
    }
}