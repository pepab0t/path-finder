package dev.cerios;

import java.awt.*;

import static dev.cerios.Config.TERRAIN_LEVEL;

public enum Marker {
    NONE(Config.DEFAULT_COLOR, 0),
    START(Config.START_COLOR, 100),
    END(Config.END_COLOR, 100),
    OBSTACLE(Config.OBSTACLE_COLOR, 80),
    PATH(Config.PATH_COLOR, 50),
    RESULT(Config.RESULT_COLOR, 90),
    TERRAIN_1(Config.TERRAIN_1_COLOR, TERRAIN_LEVEL),
    TERRAIN_2(Config.TERRAIN_2_COLOR, TERRAIN_LEVEL),
    TERRAIN_3(Config.TERRAIN_3_COLOR, TERRAIN_LEVEL),
    TERRAIN_4(Config.TERRAIN_4_COLOR, TERRAIN_LEVEL);

    private final Color color;
    private final int level;

    Marker(Color color, int level) {
        this.color = color;
        this.level = level;
    }

    public Color getColor() {
        return color;
    }

    public int getLevel() {
        return level;
    }
}
