package com.futurteam.conveyor.models;

public final class RandomAdapter {

    private static final double M = 2.0E48D;
    private static final double K = 2.5214903917E10D;
    private static final double B = 11.0D;
    private static double L = 47.0D;

    public static double random() {
        return (L = (K * L + B) % M) / M;
    }

    public static double withDelta(double expect, double delta) {
        return expect - delta + 2 * delta * random();
    }

}
