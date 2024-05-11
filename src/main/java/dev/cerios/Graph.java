package dev.cerios;

import dev.cerios.matrix.ArrayMatrix;
import dev.cerios.matrix.IntMatrix;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class Graph<T> {
    private final Node<T>[] nodes;
    private final Map<T, Integer> valueIndex;
    private final boolean[] visited;
    private final IntMatrix matrix;
    private int startIndex;
    private int endIndex;
    private int index = -1;

    public Graph(int vertices) {
        this.matrix = new ArrayMatrix(vertices, vertices);
        this.nodes = (Node<T>[]) new Node[vertices];
        this.visited = new boolean[vertices];
        this.valueIndex = new HashMap<>(vertices);
    }

    public static Graph<Position> fromField(Marker[][] field) {
        int rows = field.length;
        int cols = field[0].length;

        Graph<Position> graph = new Graph<>(rows * cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Position pos = new Position(i, j);
                graph.addValue(pos);

                if (field[i][j] == Marker.START) graph.startIndex = graph.getIndex();
                else if (field[i][j] == Marker.END) graph.endIndex = graph.getIndex();
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Marker current = field[i][j];
                Position currentPos = new Position(i, j);
                if (current == Marker.OBSTACLE) continue;

                for (int[] vector : Config.SEARCH_VECTORS) {
                    int rr = i + vector[0];
                    int cc = j + vector[1];

                    if (rr >= rows || rr < 0 || cc >= cols || cc < 0)
                        continue;

                    Marker neighbor = field[rr][cc];
                    Position neighborPos = new Position(rr, cc);
                    if (neighbor == Marker.OBSTACLE) {
                        continue;
                    }

                    BiFunction<Integer, Integer, Integer> transitionFunc = current.getLevel() >= neighbor.getLevel() ?
                            Math::min : Math::max;

                    graph.addEdge(currentPos,
                            neighborPos,
                            transitionFunc.apply(current.getLevel(), neighbor.getLevel()));
                }
            }
        }

        return graph;
    }

//    private int countIndex(Node node) {
//        return node.index();
//    }
//
//    private int countIndex(int r, int c) {
//        return r * Config.COLS + c;
//    }

    public void addValue(T value) {
        index++;
        if (index >= nodes.length) {
            throw new RuntimeException(String.format("Matrix allocated size (%s) exceeded", nodes.length));
        }

        if (nodes[index] != null) {
            throw new RuntimeException(String.format("Element already exists at %d. position", index));
        }

        Node<T> node = new Node<>(index, value);
        nodes[index] = node;
        valueIndex.put(value, index);
    }

    public void addEdge(int index1, int index2, int weight) {
        matrix.set(index1, index2, weight);
    }

    public void addEdge(T src, T dst, int weight) {
        Integer srcIndex = valueIndex.get(src);
        Integer dstIndex = valueIndex.get(dst);

        if (srcIndex == null || dstIndex == null) {
            throw new RuntimeException("src or dst value not registered");
        }

        addEdge(srcIndex, dstIndex, weight);
    }

    public List<T> bfs() {
        Queue<Node<T>> q = new LinkedList<>();
        q.offer(nodes[startIndex]);
        visited[startIndex] = true;

        Node<T>[] prevs = new Node[nodes.length];

        boolean success = false;

        while (!q.isEmpty()) {
            Node<T> n = q.remove();

            if (n.index() == endIndex) {
                success = true;
                break;
            }

            for (Edge edge : findSteps(n)) {
                visited[edge.to()] = true;
                q.offer(nodes[edge.to()]);
                prevs[edge.to()] = n;
            }
        }

        if (!success) return Collections.emptyList();

        return constructPath(prevs);
    }

    public List<T> dfs() {
        Stack<Node<T>> stack = new Stack<>();
        stack.push(nodes[startIndex]);
        visited[startIndex] = true;

        Node<T>[] prevs = new Node[nodes.length];

        boolean success = false;

        while (!stack.isEmpty()) {
            Node<T> n = stack.pop();

            if (n.index() == endIndex) {
                success = true;
                break;
            }

            for (Edge edge : findSteps(n)) {
                visited[edge.to()] = true;
                stack.push(nodes[edge.to()]);
                prevs[edge.to()] = n;
            }
        }

        if (!success) return Collections.emptyList();

        return constructPath(prevs);
    }

    public List<T> dijkstra() {
        int[] dists = new int[nodes.length];

        Arrays.fill(dists, Integer.MAX_VALUE);
        dists[startIndex] = 0;

        int[] prev = new int[nodes.length];
        Arrays.fill(prev, -1);

        while (IntStream.range(0, visited.length).mapToObj(idx -> visited[idx]).anyMatch(x -> !x)) {
            int lo = findLowestDistanceUnseen(dists);
            visited[lo] = true;

            for (Edge edge : findSteps(nodes[lo])) {
                int dist = dists[edge.from()] + edge.weight();
                if (dist < dists[edge.to()]) {
                    prev[edge.to()] = lo;
                    dists[edge.to()] = dist;
                }
            }
        }

        return constructPath(prev);
    }

    /**
     * find index of lowest value in array that was not seen
     *
     * @param dists source array of values
     * @return index of lowest element from source
     */
    private int findLowestDistanceUnseen(int[] dists) {
        int lowest = Integer.MAX_VALUE;
        int lowestIndex = -1;

        for (int i = 0; i < dists.length; i++) {
            if (visited[i]) continue;

            if (dists[i] < lowest || (dists[i] == lowest && lowestIndex == -1)) {
                lowest = dists[i];
                lowestIndex = i;
            }
        }

        return lowestIndex;
    }

    private List<T> constructPath(Node<T>[] prevMapping) {
        Node<T> n = nodes[endIndex];

        List<T> result = new LinkedList<>();

        while (n != null) {
            result.addFirst(n.value());
            n = prevMapping[n.index()];
        }

        return result;
    }

    private List<T> constructPath(int[] prev) {
        int index = endIndex;

        List<T> result = new LinkedList<>();

        while (index != -1) {
            result.addFirst(nodes[index].value());
            index = prev[index];
        }

        return result;
    }

    public int getIndex() {
        return index;
    }

    private List<Edge> findSteps(Node<T> node) {
        int current = node.index();
        int[] matrixRow = matrix.getRow(current);

        List<Edge> possibilities = new ArrayList<>();
        for (int i = 0; i < matrixRow.length; i++) {
            if (matrixRow[i] > 0 && !visited[i])
                possibilities.add(new Edge(current, i, matrixRow[i]));
        }

        return possibilities;
    }

    private record Edge(int from, int to, int weight) {
    }

    public record Node<T>(int index, T value) {
    }
}
