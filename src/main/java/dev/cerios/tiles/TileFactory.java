package dev.cerios.tiles;

import dev.cerios.Config;

import java.awt.*;

public class TileFactory {

    public GameTile create(int i, int j) {
        GameTile tile = new GameTile(i, j);
        tile.setPreferredSize(new Dimension(Config.TILE_WIDTH, Config.TILE_HEIGHT));
//        tile.setBackground((i + j) % 2 == 0 ? Config.DEFAULT_COLOR : Config.DEFAULT_COLOR_2); // Set default color
//        tile.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        return tile;
    }
}
