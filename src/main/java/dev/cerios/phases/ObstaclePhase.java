package dev.cerios.phases;


import dev.cerios.Config;
import dev.cerios.Model;
import dev.cerios.tiles.GameTile;
import dev.cerios.MainView;
import dev.cerios.Marker;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class ObstaclePhase extends GamePhaseTemplate {
    private static final int OBSTACLE_MODE = 1;
    private static final int TERRAIN_MODE = 2;

    private int mode = OBSTACLE_MODE;

    public ObstaclePhase(MainView view, Model model) {
        super(view, model);
    }

    public ObstaclePhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
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
            private Collection<GameTile> lastNeighbours = Collections.emptySet();

            @Override
            public void mousePressed(MouseEvent e) {
                switch (mode) {
                    case OBSTACLE_MODE -> {
                        if (SwingUtilities.isRightMouseButton(e)) {
                            tile.setBackground(Marker.NONE.getColor());
                            tile.setMarker(Marker.NONE);
                            return;
                        }
                        if (!tile.isMarked()) {
                            tile.setMarker(Marker.OBSTACLE);
                            tile.setBackground(Marker.OBSTACLE.getColor());
                        }
                    }
                    case TERRAIN_MODE -> {
                        if (tile.getMarker() == Marker.NONE)
                            tile.setMarker(Marker.TERRAIN);
                        view.getUnmarkedNeighbors(tile).forEach(t -> {
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
                            tile.setBackground(Marker.OBSTACLE.getColor());
                            tile.setMarker(Marker.OBSTACLE);
                        } else {
                            tile.setBackground(Config.HOVER_COLOR);
                        }
                    }
                    case TERRAIN_MODE -> {
                        lastNeighbours = view.getUnmarkedNeighbors(tile);

                        if (SwingUtilities.isLeftMouseButton(e)) {
                            lastNeighbours.forEach(t -> t.setMarker(Marker.TERRAIN));
                            tile.setMarker(Marker.TERRAIN);
                        } else {
                            lastNeighbours.forEach(t -> t.setBackground(Config.HOVER_COLOR));
                            tile.setBackground(Config.HOVER_COLOR);
                        }
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                switch (mode) {
                    case OBSTACLE_MODE -> {
                        if (!SwingUtilities.isLeftMouseButton(e) && !tile.isMarked()) {
                            tile.setBackground(Marker.NONE.getColor());
                        }
                    } case TERRAIN_MODE -> {
                        if (!tile.isMarked())
                            tile.setBackground(Marker.NONE.getColor());

                        lastNeighbours.forEach(t -> {
                                t.setMarker(Marker.NONE);
                        });
                    }
                }
            }
        };
    }
}
