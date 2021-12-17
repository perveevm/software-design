package ru.perveevm.bridge.graph;

import ru.perveevm.bridge.drawing.DrawingAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EdgesListGraph extends Graph {
    private final int vertexCount;
    private final List<Edge> edges = new ArrayList<>();

    public EdgesListGraph(final DrawingAPI drawingAPI, final Path inputFilePath)
            throws IOException {
        super(drawingAPI);

        BufferedReader reader = Files.newBufferedReader(inputFilePath);

        List<String> lines = reader.lines().collect(Collectors.toList());
        vertexCount = Integer.parseInt(lines.get(0));
        for (int i = 1; i < lines.size(); i++) {
            String[] data = lines.get(i).split(" ");
            edges.add(new Edge(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
        }

        reader.close();
    }

    @Override
    protected int getVertexCount() {
        return vertexCount;
    }

    @Override
    protected List<Edge> getEdges() {
        return edges;
    }
}
