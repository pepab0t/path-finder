package dev.cerios.matrix;

public class ArrayMatrix implements IntMatrix {

    private final int rows;
    private final int cols;

    private final int[][] data;

    public ArrayMatrix(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
        this.data = new int[rows][cols];
    }

    public void print() {
        for (int[] ints : data) {
            for (int j = 0; j < data[0].length; j++) {
                System.out.printf("%d ", ints[j]);
            }
            System.out.print("\n");
        }
    }

    @Override
    public void set(int i, int j, int value) {
        data[i][j] = value;
    }

    @Override
    public int get(int i, int j) {
        return data[i][j];
    }

    @Override
    public int[] getRow(int row) {
        return data[row];
    }

    @Override
    public int rowCount() {
        return rows;
    }

    @Override
    public int columnCount() {
        return cols;
    }
}
