package dev.cerios.mousehandlers;

import dev.cerios.Config;
import dev.cerios.Main;
import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.tiles.GameTile;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TerrainAdapter extends MouseAdapter {

    private final MainView view;

    public TerrainAdapter(MainView view) {
        this.view = view;
    }

    private void applyTerrain(GameTile tile) {
        tile.setMarker(Marker.TERRAIN);
        tile.setBackground(Marker.TERRAIN.getColor());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();

        view.getUnmarkedNeighbors(tile).forEach(this::applyTerrain);
        if (!tile.isMarked()) {
            this.applyTerrain(tile);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        GameTile tile = (GameTile) e.getSource();

        if (SwingUtilities.isLeftMouseButton(e)) {
            view.getUnmarkedNeighbors(tile).forEach(this::applyTerrain);
            if (!tile.isMarked())
                this.applyTerrain(tile);
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
