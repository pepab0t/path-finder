package dev.cerios;

import java.util.*;

public class Graph {

    private final Map<Node, Integer> nodes = new HashMap<>();
    private final Map<Integer, Node> indexNodes = new HashMap<>();
    private final int[][] matrix;

    private int index = 0;

    public Graph(int size) {
        this.matrix = new int[size][size];
    }

    public void addNode(Node node) {
        if (nodes.containsKey(node))
            return;
        nodes.put(node, index);
        indexNodes.put(index++, node);
    }

    public void addEdge(Node n1, Node n2) {
//        System.out.println(n1.toString() + ", " + n2.toString());
        int i1 = nodes.get(n1);
        int i2 = nodes.get(n2);

        matrix[i1][i2] = 1;
        matrix[i2][i1] = 1;
    }

    public void print() {
        for (int[] ints : matrix) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.printf("%d ", ints[j]);
            }
            System.out.print("\n");
        }
    }

    public List<Node> findSteps(Node node) {
        int[] matrixRow = matrix[nodes.get(node)];

        List<Node> possibilities = new ArrayList<>();

        for (int i = 0; i < matrixRow.length; i++) {
            if (matrixRow[i] == 1)
                possibilities.add(indexNodes.get(i));
        }

        return possibilities;
    }
}
