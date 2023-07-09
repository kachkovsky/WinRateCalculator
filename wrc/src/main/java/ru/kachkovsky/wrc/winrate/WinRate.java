package ru.kachkovsky.wrc.winrate;

public class WinRate {

    private final float minWinRate;
    private final float maxWinRate;
    private final int calculatedVariants;

    public WinRate(float winRate) {
        this.minWinRate = winRate;
        this.maxWinRate = winRate;
        calculatedVariants = 1;
    }

    //for incalculable chances
    public WinRate(float minWinRate, float maxWinRate) {
        this.minWinRate = minWinRate;
        this.maxWinRate = maxWinRate;
        calculatedVariants = 1;
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

    @Override
    public String toString() {
        return String.format("%.2f %.2f  | %d", minWinRate, maxWinRate, calculatedVariants);
    }
}
