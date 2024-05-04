package dev.cerios;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;

public class Model {

    private Thread runner;

    public void compute(Marker[][] field,
                        String algorithm,
                        BiConsumer<Integer, Integer> resultObserver,
                        BiConsumer<Integer, Integer> pathObserver) {
        runner = new Thread(() -> {
            Graph graph = Graph.fromField(field);

            List<Graph.Node> result = graph.bfs();

            for (Graph.Node n : result) {
                resultObserver.accept(n.r(), n.c());
            }
        });
        runner.start();
    }
}
