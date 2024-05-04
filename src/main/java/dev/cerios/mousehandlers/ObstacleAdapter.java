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
        if (!tile.isMarked() || tile.getMarker().equals(Marker.TERRAIN)) {
            tile.setMarker(Marker.OBSTACLE);
            tile.setBackground(Marker.OBSTACLE.getColor());
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();
        if (!tile.isMarked() || tile.getMarker().equals(Marker.TERRAIN)) {
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
            if (!tile.isMarked())
                tile.setBackground(Marker.NONE.getColor());
            else if (tile.getMarker().equals(Marker.TERRAIN)) {
                tile.setBackground(Marker.TERRAIN.getColor());
            }
        }
    }
}
