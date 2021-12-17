package ru.perveevm.bridge.graph;

import ru.perveevm.bridge.drawing.DrawingAPI;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AdjacencyMatrixGraph extends Graph {
    private final int vertexCount;
    private final boolean[][] matrix;

    public AdjacencyMatrixGraph(final DrawingAPI drawingAPI, final Path inputFilePath)
            throws IOException {
        super(drawingAPI);

        BufferedReader reader = Files.newBufferedReader(inputFilePath);

        vertexCount = Integer.parseInt(reader.readLine());
        matrix = new boolean[vertexCount][vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            List<Integer> row = Arrays.stream(reader.readLine().split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            for (int j = 0; j < row.size(); j++) {
                if (row.get(j) == 1) {
                    matrix[i][j] = true;
                    matrix[j][i] = true;
                }
            }
        }

        reader.close();
    }

    @Override
    protected int getVertexCount() {
        return vertexCount;
    }

    @Override
    protected List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < i; j++) {
                if (matrix[i][j]) {
                    edges.add(new Edge(i, j));
                }
            }
        }
        return edges;
    }
}
