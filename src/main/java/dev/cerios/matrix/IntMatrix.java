package dev.cerios.matrix;

public interface IntMatrix {
    void set(int i, int j, int value);
    int get(int i, int j);
    int[] getRow(int row);
    int rowCount();
    int columnCount();
    void print();
}
