package dev.cerios.mousehandlers;

import dev.cerios.Config;
import dev.cerios.tiles.GameTile;
import dev.cerios.Marker;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClickMouseAdapter extends MouseAdapter {

    private final GameTile tile;
    private final Marker marker;

    public ClickMouseAdapter(GameTile tile, Marker tileMarker) {
        this.tile = tile;
        this.marker = tileMarker;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1 && !tile.isMarked()) {
            tile.setMarker(marker);
            tile.sendClickNotification();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!tile.isMarked()) {
            tile.setBackground(Config.HOVER_COLOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!tile.isMarked()) {
            tile.setBackground(Marker.NONE.getColor());
        }
    }
}
