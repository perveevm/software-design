package ru.perveevm.bridge.graph;

import ru.perveevm.bridge.drawing.DrawingAPI;
import ru.perveevm.bridge.geometry.Circle;
import ru.perveevm.bridge.geometry.Point;
import ru.perveevm.bridge.geometry.Segment;

import java.util.List;

public abstract class Graph {
    private final DrawingAPI drawingAPI;

    public Graph(final DrawingAPI drawingAPI) {
        this.drawingAPI = drawingAPI;
    }

    public void draw() {
        int n = getVertexCount();
        for (int i = 0; i < n; i++) {
            drawVertex(i);
        }
        for (Edge edge : getEdges()) {
            drawEdge(edge);
        }
        drawingAPI.applyAll();
    }

    protected abstract int getVertexCount();

    protected abstract List<Edge> getEdges();

    private void drawVertex(final int v) {
        drawingAPI.drawCircle(new Circle(getVertexPoint(v), 5));
    }

    private void drawEdge(final Edge e) {
        drawingAPI.drawSegment(new Segment(getVertexPoint(e.getFrom()), getVertexPoint(e.getTo())));
    }

    private Point getVertexPoint(final int v) {
        int width = DrawingAPI.WIDTH;
        int height = DrawingAPI.HEIGHT;

        double angle = 2 * Math.PI * v / getVertexCount();
        Point center = new Point((double) width / 2, (double) height / 2);
        double radius = height * 0.3;
        return new Point(center.getX() + radius * Math.cos(angle), center.getY() + radius * Math.sin(angle));
    }
}
