package dev.cerios;

import dev.cerios.matrix.ArrayMatrix;
import dev.cerios.matrix.IntMatrix;

import java.util.*;

public class Graph {
    private final Node[] indexNodes;
    private final boolean[] visited;
    private final IntMatrix matrix;
    private Node startNode;
    private Node endNode;

    private static final int[] dr = new int[]{1, -1, 0, 0};
    private static final int[] dc = new int[]{0, 0, 1, -1};

    public Graph(int size) {
        this.matrix = new ArrayMatrix(size, size);
        this.indexNodes = new Node[size];
        this.visited = new boolean[size];
    }

    public static Graph fromField(Marker[][] field) {
        int rows = field.length;
        int cols = field[0].length;

        Graph graph = new Graph(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Node n = new Node(i, j);
                graph.addNode(n);

                if (field[i][j] == Marker.START) graph.startNode = n;
                else if (field[i][j] == Marker.END) graph.endNode = n;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (field[i][j] == Marker.OBSTACLE) continue;

                for (int k = 0; k < dr.length; k++) {
                    int rr = i + dr[k];
                    int cc = j + dc[k];

                    if (rr >= rows || rr < 0 || cc >= cols || cc < 0)
                        continue;

                    var neighbor = field[rr][cc];
                    if (neighbor == Marker.OBSTACLE) {
                        continue;
                    }

                    graph.addEdge(graph.countIndex(i, j), graph.countIndex(rr, cc));
                }
            }
        }

        return graph;
    }

    private int countIndex(Node node) {
        return countIndex(node.r(), node.c());
    }

    private int countIndex(int r, int c) {
        return r * Config.COLS + c;
    }

    public void addNode(Node node) {
        int index = countIndex(node);
        if (index >= indexNodes.length)
            throw new RuntimeException(String.format("Matrix allocated size (%s) exceeded", indexNodes.length));

        if (indexNodes[index] != null) {
            throw new RuntimeException(String.format("Element already exists at %s's position", node));
        }

        indexNodes[index] = node;
    }

    public void addEdge(int index1, int index2) {
        matrix.set(index1, index2, 1);
        matrix.set(index2, index1, 1);
    }

    public List<Node> bfs() {
        Queue<Node> q = new LinkedList<>();
        q.offer(startNode);
        visited[countIndex(startNode)] = true;

        Node[] prevs = new Node[indexNodes.length];

        boolean success = false;

        while (!q.isEmpty()) {
            Node n = q.remove();

            if (n.equals(endNode)) {
                success = true;
                break;
            }

            for (Node neighbor : findSteps(n)) {
                int neighborIndex = countIndex(neighbor);
                visited[neighborIndex] = true;
                q.offer(neighbor);
                prevs[neighborIndex] = n;
            }
        }

        if (!success) return Collections.emptyList();

        return constructPath(prevs);
    }

    private List<Node> constructPath(Node[] prevMapping) {
        Node n = endNode;

        List<Node> result = new LinkedList<>();

        while (n != null) {
            result.addFirst(n);
            n = prevMapping[countIndex(n)];
        }

        return result;
    }


    private List<Node> findSteps(Node node) {
        int[] matrixRow = matrix.getRow(countIndex(node));

        List<Node> possibilities = new ArrayList<>();
        for (int i = 0; i < matrixRow.length; i++) {
            if (matrixRow[i] > 0 && !visited[i])
                possibilities.add(indexNodes[i]);
        }
        return possibilities;
    }

    public record Node(int r, int c) {
    }
}
