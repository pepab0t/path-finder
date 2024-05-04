package dev.cerios;

import java.util.List;
import java.util.function.BiConsumer;

public class Model {

    private Runnable afterCompute;

    public void compute(Marker[][] field,
                        String algorithm,
                        BiConsumer<Integer, Integer> resultObserver,
                        BiConsumer<Integer, Integer> pathObserver) {
        Thread runner = new Thread(() -> {
            Graph graph = Graph.fromField(field);

            List<Graph.Node> result = graph.bfs();

            result.forEach(n -> resultObserver.accept(n.r(), n.c()));
            if (afterCompute != null) afterCompute.run();
        });
        runner.start();
    }

    public void setAfterCompute(Runnable afterCompute) {
        this.afterCompute = afterCompute;
    }
}
