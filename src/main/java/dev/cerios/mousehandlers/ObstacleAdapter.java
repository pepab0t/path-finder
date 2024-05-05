package dev.cerios.mousehandlers;

import dev.cerios.Config;
import dev.cerios.Marker;
import dev.cerios.tiles.GameTile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ObstacleAdapter extends MouseAdapter {
    @Override
    public void mousePressed(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();
        if (SwingUtilities.isRightMouseButton(e)) {
            tile.setBackground(Marker.NONE.getColor());
            tile.setMarker(Marker.NONE);
        }
        if (tile.setMarkerWeighted(Marker.OBSTACLE)) {
            tile.setBackground(Marker.OBSTACLE.getColor());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();
        if (tile.canBeMarkedWith(Marker.OBSTACLE)) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                tile.setBackground(Marker.OBSTACLE.getColor());
                tile.setMarker(Marker.OBSTACLE);
            } else {
                tile.setBackground(Config.HOVER_COLOR);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();
        if (!SwingUtilities.isLeftMouseButton(e)) {
            tile.setBackground(tile.getMarker().getColor());
        }
    }
}
