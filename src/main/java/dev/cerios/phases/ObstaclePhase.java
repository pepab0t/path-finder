package dev.cerios.phases;


import dev.cerios.Model;
import dev.cerios.mousehandlers.ObstacleAdapter;
import dev.cerios.mousehandlers.TerrainAdapter;
import dev.cerios.View;
import dev.cerios.Marker;

import java.awt.event.MouseAdapter;

public class ObstaclePhase extends GamePhaseTemplate {
    private static final int OBSTACLE_MODE = 1;
    private static final int TERRAIN_MODE = 2;

    private MouseAdapter obstacleAdapter;
    private MouseAdapter terrainAdapter1;
    private MouseAdapter terrainAdapter2;
    private MouseAdapter terrainAdapter3;
    private MouseAdapter terrainAdapter4;

    public ObstaclePhase(View view, Model model) {
        this(view, model, null);
    }

    public ObstaclePhase(View view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
        this.obstacleAdapter = new ObstacleAdapter();
        this.terrainAdapter1 = new TerrainAdapter(view, Marker.TERRAIN_1);
        this.terrainAdapter2 = new TerrainAdapter(view, Marker.TERRAIN_2);
        this.terrainAdapter3 = new TerrainAdapter(view, Marker.TERRAIN_3);
        this.terrainAdapter4 = new TerrainAdapter(view, Marker.TERRAIN_4);
    }

    @Override
    public void start() {
        view.setInfoText("Create OBSTACLES and TERRAIN");
        view.enableStartButton(true);
        view.enableTerrainButton(true);
        view.enableObstacleButton(true);

        view.setMouseAdapterToTiles(this.obstacleAdapter);

        view.connectStartButton(e -> this.end(view));

        view.connectObstacleButton(e -> view.setMouseAdapterToTiles(this.obstacleAdapter));
        view.connectTerrainButton1(e -> view.setMouseAdapterToTiles(this.terrainAdapter1));
        view.connectTerrainButton2(e -> view.setMouseAdapterToTiles(this.terrainAdapter2));
        view.connectTerrainButton3(e -> view.setMouseAdapterToTiles(this.terrainAdapter3));
        view.connectTerrainButton4(e -> view.setMouseAdapterToTiles(this.terrainAdapter4));
    }

    private void end(View view) {
        view.disconnectStartButton();
        view.disconnectTerrainButton1();
        view.disconnectObstacleButton();
        view.enableTerrainButton(false);
        view.enableObstacleButton(false);
        view.enableStartButton(false);
        super.end();
    }
}
