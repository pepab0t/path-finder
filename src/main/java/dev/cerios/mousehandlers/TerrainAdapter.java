package dev.cerios.mousehandlers;

import dev.cerios.Config;
import dev.cerios.View;
import dev.cerios.Marker;
import dev.cerios.tiles.GameTile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static dev.cerios.Config.TERRAIN_LEVEL;

public class TerrainAdapter extends MouseAdapter {

    private final View view;
    private final Marker marker;

    public TerrainAdapter(View view, Marker marker) {
        this.view = view;
        this.marker = marker;
    }

    private void applyTerrain(GameTile tile) {
        tile.setMarker(marker);
        tile.setBackground(marker.getColor());
    }

    private void eraseTerrain(GameTile tile) {
        if (tile.getMarker().getLevel() == TERRAIN_LEVEL) {
            tile.setMarker(Marker.NONE);
            tile.setBackground(Marker.NONE.getColor());
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();

        if (SwingUtilities.isRightMouseButton(e)) {
            view.getNeighbors(tile).forEach(this::eraseTerrain);
            eraseTerrain(tile);
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            view.getUnmarkedNeighbors(tile).forEach(this::applyTerrain);
            if (!tile.isMarked()) {
                this.applyTerrain(tile);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();

        if (SwingUtilities.isLeftMouseButton(e)) {
            view.getUnmarkedNeighbors(tile).forEach(this::applyTerrain);
            if (!tile.isMarked())
                this.applyTerrain(tile);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            view.getNeighbors(tile).forEach(this::eraseTerrain);
            eraseTerrain(tile);
        } else {
            view.getUnmarkedNeighbors(tile).forEach(t -> t.setBackground(Config.HOVER_COLOR));
            if (!tile.isMarked())
                tile.setBackground(Config.HOVER_COLOR);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();
        view.getUnmarkedNeighbors(tile).forEach(t -> t.setBackground(Marker.NONE.getColor()));
        if (!tile.isMarked())
            tile.setBackground(Marker.NONE.getColor());
    }
}
