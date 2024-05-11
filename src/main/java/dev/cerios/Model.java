package dev.cerios;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Model {

    private Consumer<Integer> afterCompute;
    private Marker[][] lastField = null;

    public void compute(Marker[][] field,
                        String algorithm,
                        BiConsumer<Integer, Integer> resultObserver,
                        BiConsumer<Integer, Integer> pathObserver) {
        lastField = field;
        Thread runner = new Thread(() -> {
            Graph<Position> graph = Graph.fromField(field);

            List<Position> result = graph.dijkstra();

            result.forEach(n -> resultObserver.accept(n.r(), n.c()));
            if (afterCompute != null) afterCompute.accept(result.size() - 1);
        });
        runner.start();
    }

    public void setAfterCompute(Consumer<Integer> afterCompute) {
        this.afterCompute = afterCompute;
    }

    public Marker[][] getLastField() {
        return lastField;
    }
}
