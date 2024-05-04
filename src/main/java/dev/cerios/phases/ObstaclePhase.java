package dev.cerios.phases;


import dev.cerios.Config;
import dev.cerios.Model;
import dev.cerios.mousehandlers.ObstacleAdapter;
import dev.cerios.mousehandlers.TerrainAdapter;
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

    private MouseAdapter obstacleAdapter;
    private MouseAdapter terrainAdapter;

    public ObstaclePhase(MainView view, Model model) {
        this(view, model, null);
    }

    public ObstaclePhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
        this.obstacleAdapter = new ObstacleAdapter();
        this.terrainAdapter = new TerrainAdapter(view);
    }

    @Override
    public void start() {
        view.enableStartButton(true);
        view.enableTerrainButton(true);
        view.enableObstacleButton(true);

        view.setMouseAdapterToTiles(this.obstacleAdapter);

        view.connectStartButton(e -> this.end(view));
        view.connectObstacleButton(e -> {
            view.setMouseAdapterToTiles(this.obstacleAdapter);
            System.out.println("obstacles mode");
        });
        view.connectTerrainButton(e -> {
            view.setMouseAdapterToTiles(this.terrainAdapter);
            System.out.println("terrain mode");
        });
    }

    private void end(MainView view) {
        view.disconnectStartButton();
        view.disconnectTerrainButton();
        view.disconnectObstacleButton();
        view.enableTerrainButton(false);
        view.enableObstacleButton(false);
        view.enableStartButton(false);
        super.end();
    }
}
