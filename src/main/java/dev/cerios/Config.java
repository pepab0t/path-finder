package dev.cerios;

import java.awt.*;

public class Config {
    public static final int TILE_WIDTH = 20;
    public static final int TILE_HEIGHT = 20;
    public static final int COLS = 32;
    public static final int ROWS = 32;
    public static final int X_SPACING = 1;
    public static final int Y_SPACING = 1;

    public static final Color DEFAULT_COLOR = new Color(255, 255, 255);
    public static final Color OBSTACLE_COLOR = Color.BLACK;
    public static final Color TERRAIN_COLOR = new Color(181, 127, 84);
    public static final Color START_COLOR = Color.GREEN;
    public static final Color END_COLOR = Color.RED;
    public static final Color RESULT_COLOR = Color.BLUE;
    public static final Color HOVER_COLOR = Color.ORANGE;
    public static final Color PATH_COLOR = Color.ORANGE;

//    public static final int[] dr = new int[]{1, -1, 0, 0, 2, -2, 0, 0, 1, 1, -1, -1, 2, 2, -2, -2 };
//    public static final int[] dc = new int[]{0, 0, 1, -1, 0, 0, 2, -2, 1, -1, 1, -1, 2, -2, 2, -2};

    public static final int[] dr = new int[]{1, -1, 0, 0};
    public static final int[] dc = new int[]{0, 0, 1, -1};
}
