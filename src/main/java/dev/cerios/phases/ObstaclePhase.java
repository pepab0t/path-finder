package dev.cerios.phases;


import dev.cerios.Config;
import dev.cerios.Model;
import dev.cerios.tiles.GameTile;
import dev.cerios.MainView;
import dev.cerios.Marker;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ObstaclePhase extends GamePhaseTemplate {
    @Override
    public void start(MainView view, Model model) {
        view.enableStartButton(true);
        view.setMouseAdapterToTiles(this::createTileListener);
        view.connectStartButton(e -> this.end(view));
    }

    private void end(MainView view) {
        view.disconnectStartButton();
        super.end();
    }

    private MouseAdapter createTileListener(GameTile tile) {
        return new MouseAdapter() {
            private final Marker marker = Marker.OBSTACLE;

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    tile.setMarker(Marker.NONE);
                    return;
                }
                if (!tile.isMarked()) {
                    tile.setMarker(marker);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (tile.isMarked()) {
                    return;
                }

                if (SwingUtilities.isLeftMouseButton(e)) {
                    tile.setMarker(Marker.OBSTACLE);
                } else {
                    tile.setBackground(Config.HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tile.isMarked()) {
                    return;
                }

                if (!SwingUtilities.isLeftMouseButton(e)) {
                    tile.setBackground(Marker.NONE.getColor());
                }
            }
        };
    }
}
