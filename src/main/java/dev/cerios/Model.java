package dev.cerios;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class Model {

    private Thread runner;

    public void compute(Marker[][] field, String algorithm, BiConsumer<Integer, Integer> observer) {
        runner = new Thread(() -> {
            Graph graph = new Graph(field.length * field[0].length);

            int iLimit = field.length;
            int jLimit = field[0].length;

            for (int i = 0; i < iLimit; i++) {
                for (int j = 0; j < jLimit; j++) {
                    graph.addNode(new Node(i, j));
                }
            }

            for (int i = 0; i < iLimit; i++) {
                for (int j = 0; j < jLimit; j++) {
                    if (field[i][j] == Marker.OBSTACLE)
                        continue;

                    Node node = new Node(i, j);

                    Node[] neighbours = new Node[4];
                    neighbours[0] = i + 1 < iLimit ? new Node(i + 1, j) : null;
                    neighbours[1] = i - 1 > -1 ? new Node(i - 1, j) : null;
                    neighbours[2] = j + 1 < jLimit ? new Node(i, j + 1) : null;
                    neighbours[3] = j - 1 > -1 ? new Node(i, j - 1) : null;

                    for (Node neighbour : neighbours) {
                        if (neighbour != null && field[neighbour.i()][neighbour.j()] != Marker.OBSTACLE)
                            graph.addEdge(node, neighbour);
                    }
                }
            }

            boolean[][] visited = new boolean[iLimit][jLimit];

            Queue<Node> queue = new LinkedList<>();
            queue.offer(findStart(field));
            Node result = null;

            while (!queue.isEmpty()) {
                Node node = queue.remove();
                visited[node.i()][node.j()] = true;

                if (field[node.i()][node.j()] == Marker.END) {
                    result = node;
                    break;
                }

                for (Node n : graph.findSteps(node)) {
                    if (!visited[n.i()][n.j()]) {
                        queue.offer(n);
                        n.prev = node;
                    }
                }
            }

            while (result != null) {
                observer.accept(result.i(), result.j());
                result = result.prev;
            }
        });
        runner.start();
    }

    private Node findStart(Marker[][] field) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j] == Marker.START) {
                    return new Node(i, j);
                }
            }
        }
        throw new RuntimeException("start not found");
    }

}
