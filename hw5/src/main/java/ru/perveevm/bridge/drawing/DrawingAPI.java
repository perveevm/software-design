package ru.perveevm.bridge.drawing;

import ru.perveevm.bridge.geometry.Circle;
import ru.perveevm.bridge.geometry.Segment;

public interface DrawingAPI {
    int WIDTH = 800;
    int HEIGHT = 600;

    void drawSegment(final Segment segment);

    void drawCircle(final Circle circle);

    void applyAll();
}
