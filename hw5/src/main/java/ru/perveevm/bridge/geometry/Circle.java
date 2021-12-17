package ru.perveevm.bridge.geometry;

public class Circle {
    private final Point center;
    private final double radius;

    public Circle(final Point center, final double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }
}
