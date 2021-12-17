package ru.perveevm.bridge;

import ru.perveevm.bridge.drawing.AWTDrawingAPI;
import ru.perveevm.bridge.drawing.DrawingAPI;
import ru.perveevm.bridge.drawing.JavaFXDrawingAPI;
import ru.perveevm.bridge.graph.AdjacencyMatrixGraph;
import ru.perveevm.bridge.graph.EdgesListGraph;
import ru.perveevm.bridge.graph.Graph;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    private static void printUsage() {
        System.out.println("Usage: <awt/fx> <list/matrix> <input file path>");
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            printUsage();
            return;
        }

        DrawingAPI drawingAPI;
        switch (args[0]) {
            case "awt" -> drawingAPI = new AWTDrawingAPI();
            case "fx" -> drawingAPI = new JavaFXDrawingAPI();
            default -> {
                printUsage();
                return;
            }
        }

        Graph graph;
        try {
            switch (args[1]) {
                case "list" -> graph = new EdgesListGraph(drawingAPI, Path.of(args[2]));
                case "matrix" -> graph = new AdjacencyMatrixGraph(drawingAPI, Path.of(args[2]));
                default -> {
                    printUsage();
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Cannot read graph from file: " + e.getMessage());
            return;
        }

        graph.draw();
    }
}
