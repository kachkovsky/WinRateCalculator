package ru.kachkovsky.wrc.winrate;

public class WinRate {

    private float minWinRate;
    private float maxWinRate;
    private int calculatedVariants = 1;

    public WinRate(float winRate) {
        this.minWinRate = winRate;
        this.maxWinRate = winRate;
    }

    //for incalculable chances
    public WinRate(float minWinRate, float maxWinRate) {
        this.minWinRate = minWinRate;
        this.maxWinRate = maxWinRate;
    }

    public WinRate(float minWinRate, float maxWinRate, int calculatedVariants) {
        this.minWinRate = minWinRate;
        this.maxWinRate = maxWinRate;
        this.calculatedVariants = calculatedVariants;
    }

    public float getMinWinRate() {
        return minWinRate;
    }

    public float getMaxWinRate() {
        return maxWinRate;
    }

    public int getCalculatedVariants() {
        return calculatedVariants;
    }
}
