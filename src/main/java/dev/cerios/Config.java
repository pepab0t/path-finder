package dev.cerios;

import java.awt.*;

public class Config {
    public static final int TILE_WIDTH = 20;
    public static final int TILE_HEIGHT = 20;
    public static final int COLS = 32;
    public static final int ROWS = 32;
    public static final int X_SPACING = 1;
    public static final int Y_SPACING = 1;
    public static final int RESULT_RADIUS = 5;

    public static final Color DEFAULT_COLOR = new Color(255, 251, 216);
    public static final Color OBSTACLE_COLOR = Color.BLACK;
    public static final Color START_COLOR = Color.GREEN;
    public static final Color END_COLOR = Color.RED;
    public static final Color RESULT_COLOR = new Color(137, 44, 228);
    public static final Color HOVER_COLOR = Color.ORANGE;
    public static final Color PATH_COLOR = new Color(230, 204, 251);
    public static final Color TERRAIN_1_COLOR = new Color(255, 232, 219);
    public static final Color TERRAIN_2_COLOR = new Color(235, 192, 160);
    public static final Color TERRAIN_3_COLOR = new Color(208, 154, 108);
    public static final Color TERRAIN_4_COLOR = new Color(162, 107, 67);

//    public static final int[] dr = new int[]{1, -1, 0, 0, 2, -2, 0, 0, 1, 1, -1, -1, 2, 2, -2, -2 };
//    public static final int[] dc = new int[]{0, 0, 1, -1, 0, 0, 2, -2, 1, -1, 1, -1, 2, -2, 2, -2};

    public static final int[][] SEARCH_VECTORS = new int[][]{
            new int[]{0, 1},
            new int[]{0, -1},
            new int[]{1, 0},
            new int[]{-1, 0},
    };

    public static final int TERRAIN_LEVEL = 40;

    public static final int[][] AREA_VECTORS = new int[][]{
            new int[]{1, 0},
            new int[]{1, -1},
            new int[]{1, 1},
            new int[]{0, -1},
            new int[]{0, 1},
            new int[]{-1, -1},
            new int[]{-1, 0},
            new int[]{-1, 1},
    };
}
