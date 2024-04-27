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
    private static final int OBSTACLE_MODE = 1;
    private static final int TERRAIN_MODE = 2;

    private int mode = OBSTACLE_MODE;
    private MainView view;

    @Override
    public void start(MainView view, Model model) {
        this.view = view;

        view.enableStartButton(true);

        view.setMouseAdapterToTiles(this::createTileListener);

        view.connectStartButton(e -> this.end(view));
        view.connectObstacleButton(e -> {
            this.mode = OBSTACLE_MODE;
            System.out.println("obstacles " + this.mode);
        });
        view.connectTerrainButton(e -> {
            this.mode = TERRAIN_MODE;
            System.out.println("terrain " + this.mode);
        });
    }

    private void end(MainView view) {
        view.disconnectStartButton();
        view.disconnectTerrainButton();
        view.disconnectObstacleButton();
        super.end();
    }

    private MouseAdapter createTileListener(GameTile tile) {
        return new MouseAdapter() {
            private final Marker marker = Marker.OBSTACLE;

            @Override
            public void mousePressed(MouseEvent e) {
                switch (mode) {
                    case OBSTACLE_MODE -> {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            tile.setMarker(Marker.NONE);
                            return;
                        }
                        if (!tile.isMarked()) {
                            tile.setMarker(marker);
                        }
                    }
                    case TERRAIN_MODE -> {
                        if (tile.getMarker() == Marker.NONE)
                            tile.setMarker(Marker.TERRAIN);
                        view.getNeighbors(tile.getRow(), tile.getCol()).forEach(t -> {
                            if (t.getMarker() == Marker.NONE)
                                t.setMarker(Marker.TERRAIN);
                        });
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                switch (mode) {
                    case OBSTACLE_MODE -> {
                        if (tile.isMarked()) {
                            return;
                        }

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            tile.setMarker(Marker.OBSTACLE);
                        } else {
                            tile.setBackground(Config.HOVER_COLOR);
                        }
                    }
                    case TERRAIN_MODE -> {
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switch (mode) {
                    case OBSTACLE_MODE -> {
                        if (tile.isMarked()) {
                            return;
                        }

                        if (!SwingUtilities.isLeftMouseButton(e)) {
                            tile.setBackground(Marker.NONE.getColor());
                        }
                    } case TERRAIN_MODE -> {}
                }
            }
        };
    }
}
