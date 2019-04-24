package ru.kachkovsky.wrc.winrate;

public class WinRate {

    private float minWinRate;
    private float maxWinRate;

    public WinRate(float winRate) {
        this.minWinRate = winRate;
        this.maxWinRate = winRate;
    }

    //for incalculable chances
    public WinRate(float minWinRate, float maxWinRate) {
        this.minWinRate = minWinRate;
        this.maxWinRate = maxWinRate;
    }

    public float getMinWinRate() {
        return minWinRate;
    }

    public float getMaxWinRate() {
        return maxWinRate;
    }
}
