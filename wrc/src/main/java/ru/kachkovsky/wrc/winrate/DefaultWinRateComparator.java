package ru.kachkovsky.wrc.winrate;

import java.util.Comparator;

public class DefaultWinRateComparator implements Comparator<WinRate> {
    @Override
    public int compare(WinRate o1, WinRate o2) {
        return (int) Math.signum(o1.getMinWinRate() + o1.getMaxWinRate() - (o2.getMinWinRate() + o2.getMaxWinRate()));
    }
}
