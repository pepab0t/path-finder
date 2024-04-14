package dev.cerios.phases;


import dev.cerios.Config;
import dev.cerios.tiles.GameTile;
import dev.cerios.MainView;
import dev.cerios.Marker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ObstaclePhase implements GamePhase {
    private Runnable endCallback;

    @Override
    public void start(MainView view) {
        view.enableStartButton(true);
        view.setMouseAdapterToTiles(this::createTileListener);
        view.connectStartButton(() -> this.end(view));
    }

    @Override
    public ObstaclePhase onEnd(Runnable runnable) {
        endCallback = runnable;
        return this;
    }

    private void end(MainView view) {
        view.disconnectStartButton();

        if (endCallback == null)
            return;
        endCallback.run();
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
