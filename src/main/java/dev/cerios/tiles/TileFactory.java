package dev.cerios.tiles;

import dev.cerios.Config;

import java.awt.*;

public class TileFactory {

    public GameTile create(int i, int j) {
        GameTile tile = new GameTile(i, j);
        tile.setPreferredSize(new Dimension(Config.TILE_WIDTH, Config.TILE_HEIGHT));
        tile.setBackground(Config.DEFAULT_COLOR); // Set default color
//        tile.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        return tile;
    }
}
