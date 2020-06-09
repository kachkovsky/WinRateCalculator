package ru.kachkovsky.wrc.winrate.calculator.cache;

import org.apache.activemq.util.LFUCache;
import ru.kachkovsky.wrc.SubjectsArea;
import ru.kachkovsky.wrc.winrate.WinRate;

import java.util.List;

public class LFUWRCacheHelper {
    private LFUCache<SubjectsArea, List<WinRate>> wrCache = new LFUCache<>(1000, 0.2f);

    public List<WinRate> find(SubjectsArea area) {
        return wrCache.get(area);
    }

    public List<WinRate> put(SubjectsArea area, List<WinRate> wrList) {
        return wrCache.put(area, wrList);
    }
}