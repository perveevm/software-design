package ru.perveevm.bridge.drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import ru.perveevm.bridge.geometry.Circle;
import ru.perveevm.bridge.geometry.Segment;

import java.util.ArrayList;
import java.util.List;

public class JavaFXDrawingAPI extends Application implements DrawingAPI {
    private static final List<Shape> shapes = new ArrayList<>();

    @Override
    public void drawSegment(Segment segment) {
        shapes.add(new Line(segment.getP1().getX(), segment.getP1().getY(),
                segment.getP2().getX(), segment.getP2().getY()));
    }

    @Override
    public void drawCircle(Circle circle) {
        shapes.add(new javafx.scene.shape.Circle(circle.getCenter().getX(), circle.getCenter().getY(),
                circle.getRadius()));
    }

    @Override
    public void applyAll() {
        launch(JavaFXDrawingAPI.class);
    }

    @Override
    public void start(Stage stage) {
        Group group = new Group();
        group.getChildren().addAll(shapes);
        stage.setScene(new Scene(group, WIDTH, HEIGHT));
        stage.show();
    }
}
