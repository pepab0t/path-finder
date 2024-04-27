package dev.cerios;

import dev.cerios.tiles.GameTile;
import dev.cerios.tiles.TileFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static dev.cerios.Config.*;

public class MainView extends JFrame {
    private final GameTile[][] tiles = new GameTile[ROWS][COLS];
    private final Random random;
    private final TileFactory tileFactory;

    private final JButton startButton;
    private final JButton restartButton;
    private final JButton randomButton;
    private final JButton obstacleButton;
    private final JButton terrainButton;

    public MainView(TileFactory tileFactory) {
        this(new Random(), tileFactory);
    }

    public MainView(Random random, TileFactory tileFactory)   {
        this.tileFactory = tileFactory;
        this.random = random;

        startButton = new JButton("Start");
        startButton.setEnabled(false);

        restartButton = new JButton("Restart");
        randomButton = new JButton("Random");
        obstacleButton = new JButton("Obstacle");
        terrainButton = new JButton("Terrain");
    }

    public void initGui() {
        setTitle("Grid Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 0));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ROWS, COLS));

        // Initialize the grid of panels
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                GameTile tile = tileFactory.create(i, j);
                tiles[i][j] = tile;
                gridPanel.add(tile);
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);
        buttonPanel.add(randomButton);
        buttonPanel.add(obstacleButton);
        buttonPanel.add(terrainButton);

        // Add the grid panel and button panel to the frame
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public void connectStartButton(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void connectRandomButton(ActionListener listener) {
        randomButton.addActionListener(listener);
    }

    private void disconnectButton(JButton button) {
        for (var listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

    public void disconnectStartButton() {
        disconnectButton(startButton);
    }

    public void disconnectObstacleButton() {
        disconnectButton(obstacleButton);
    }

    public void disconnectTerrainButton() {
        disconnectButton(terrainButton);
    }

    public void connectRestartButton(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    public void connectObstacleButton(ActionListener listener) {
        obstacleButton.addActionListener(listener);
    }

    public void connectTerrainButton(ActionListener listener) {
        terrainButton.addActionListener(listener);
    }

    public void generateRandomObstacles() {
        applyToAllTiles(tile -> {
            if (tile.getMarker() == Marker.OBSTACLE)
                tile.setMarker(Marker.NONE);
            if (tile.getMarker() != Marker.NONE)
                return;

            if (random.nextDouble() > 0.7) {
                tile.setMarker(Marker.OBSTACLE);
            }
        });

    }

    private void applyToAllTiles(Consumer<GameTile> tileConsumer) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                var tile = tiles[i][j];
                tileConsumer.accept(tile);
            }
        }
    }

    public void setMouseAdapterToTiles(Function<GameTile, MouseAdapter> adapterProducer) {
        applyToAllTiles(tile -> {
            for (MouseListener ml : tile.getMouseListeners()) {
                tile.removeMouseListener(ml);
            }
            tile.addMouseListener(adapterProducer.apply(tile));
        });
    }

    public void setClickObserverToTiles(Runnable callback) {
        applyToAllTiles(tile -> tile.setClickObserver(callback));
    }

    public void run() {
        pack();
        setLocationRelativeTo(null); // Center the window
        setResizable(false);
        setVisible(true);
    }

    public void enableStartButton(boolean state) {
        startButton.setEnabled(state);
    }

    public void markTile(int i, int j, Marker marker) {
        tiles[i][j].setMarker(marker);
    }

    public GameTile getTile(int row, int col) {
        // TODO : handle index out of bounds
        try{
            return tiles[row][col];
        } catch (Exception e) {
            return null;
        }
    }

    public Collection<GameTile> getUnmarkedNeighbors(GameTile tile) {
        return getUnmarkedNeighbors(tile.getRow(), tile.getCol());
    }

    public Collection<GameTile> getUnmarkedNeighbors(int row, int col) {
        Collection<GameTile> neighbors = new HashSet<>();

        try {
            if (!tiles[row+1][col].isMarked())
                neighbors.add(tiles[row+1][col]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row+1][col+1].isMarked())
                neighbors.add(tiles[row+1][col+1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row+1][col-1].isMarked())
                neighbors.add(tiles[row+1][col-1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row][col-1].isMarked())
                neighbors.add(tiles[row][col-1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row][col+1].isMarked())
                neighbors.add(tiles[row][col+1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row-1][col-1].isMarked())
                neighbors.add(tiles[row-1][col-1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row-1][col].isMarked())
                neighbors.add(tiles[row-1][col]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        try {
            if (!tiles[row-1][col+1].isMarked())
                neighbors.add(tiles[row-1][col+1]);
        } catch (ArrayIndexOutOfBoundsException e) {}

        return neighbors;
    }

    public Marker getMarker(int i, int j) {
        return tiles[i][j].getMarker();
    }

    public String getAlgorithm() {
        return "bfs";
    }

    public Marker[][] getInput() {
        Marker[][] markers = new Marker[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                markers[i][j] = tiles[i][j].getMarker();
            }
        }

        return markers;
    }

    public void clear() {
        applyToAllTiles(tile -> tile.setMarker(Marker.NONE));
    }
}

