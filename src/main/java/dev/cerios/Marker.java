package dev.cerios;

import java.awt.*;

public enum Marker {
    NONE(Config.DEFAULT_COLOR),
    START(Config.START_COLOR),
    END(Config.END_COLOR),
    OBSTACLE(Config.OBSTACLE_COLOR),
    PATH(Config.PATH_COLOR);

    private Color color;

    Marker(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
