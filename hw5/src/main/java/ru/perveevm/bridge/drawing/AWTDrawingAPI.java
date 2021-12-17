package ru.perveevm.bridge.drawing;

import ru.perveevm.bridge.geometry.Circle;
import ru.perveevm.bridge.geometry.Segment;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class AWTDrawingAPI extends Frame implements DrawingAPI {
    private final List<Shape> shapes = new ArrayList<>();

    @Override
    public void drawSegment(final Segment segment) {
        shapes.add(new Line2D.Double(segment.getP1().getX(), segment.getP1().getY(),
                segment.getP2().getX(), segment.getP2().getY()));
    }

    @Override
    public void drawCircle(final Circle circle) {
        shapes.add(new Ellipse2D.Double(circle.getCenter().getX() - circle.getRadius(),
                circle.getCenter().getY() - circle.getRadius(), circle.getRadius() * 2, circle.getRadius() * 2));
    }

    @Override
    public void applyAll() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(DrawingAPI.WIDTH, DrawingAPI.HEIGHT);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, DrawingAPI.WIDTH, DrawingAPI.HEIGHT);
        graphics2D.setColor(Color.BLACK);

        for (Shape shape : shapes) {
            graphics2D.draw(shape);
            graphics2D.fill(shape);
        }
    }
}
