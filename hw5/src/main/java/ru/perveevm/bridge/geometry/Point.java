package ru.perveevm.bridge.geometry;

public class Point {
    private final double x;
    private final double y;

    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(final Point other) {
        return Math.hypot(x - other.x, y - other.y);
    }
}
