package dev.cerios;

import java.util.Objects;

public class Node {
    private final int i;
    private final int j;

    public Node prev = null;

    public Node(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public int i() {
        return i;
    }

    public int j() {
        return j;
    }

    @Override
    public String toString() {
        return "Node{" +
                "i=" + i +
                ", j=" + j +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return i == node.i && j == node.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }
}
