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
import java.util.stream.Collectors;

import static dev.cerios.Config.*;

public class View extends JFrame {
    private final GameTile[][] tiles = new GameTile[ROWS][COLS];
    private final Random random;
    private final TileFactory tileFactory;

    private final JLabel infoLabel;

    private final JButton startButton;
    private final JButton restartButton;
    private final JButton randomButton;
    private final JButton obstacleButton;
    private final JButton terrainButton1;
    private final JButton terrainButton2;
    private final JButton terrainButton3;
    private final JButton terrainButton4;
    private final JButton lastFieldButton;

    public View(TileFactory tileFactory) {
        this(new Random(), tileFactory);
    }

    public View(Random random, TileFactory tileFactory) {
        this.tileFactory = tileFactory;
        this.random = random;

        infoLabel = new JLabel();
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setFont(new Font("Roboto", Font.PLAIN, 24));

        startButton = new JButton("Start");
        startButton.setEnabled(false);

        restartButton = new JButton("Restart");
        randomButton = new JButton("Random");
        obstacleButton = new JButton("Obstacle");

        terrainButton1 = new JButton("Terrain 1");
        terrainButton2 = new JButton("Terrain 2");
        terrainButton3 = new JButton("Terrain 3");
        terrainButton4 = new JButton("Terrain 4");

        lastFieldButton = new JButton("Last Field");
        lastFieldButton.setEnabled(false);
    }

    public void initGui() {
        setTitle("Path finder");
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

        buttonPanel.add(terrainButton1);
        buttonPanel.add(terrainButton2);
        buttonPanel.add(terrainButton3);
        buttonPanel.add(terrainButton4);

        buttonPanel.add(lastFieldButton);

        // Add the grid panel and button panel to the frame
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
        add(infoLabel, BorderLayout.NORTH);
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

    public void disconnectTerrainButton1() {
        disconnectButton(terrainButton1);
    }

    public void disconnectTerrainButton2() {
        disconnectButton(terrainButton2);
    }

    public void disconnectTerrainButton3() {
        disconnectButton(terrainButton3);
    }

    public void disconnectTerrainButton4() {
        disconnectButton(terrainButton4);
    }

    public void connectRestartButton(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    public void connectObstacleButton(ActionListener listener) {
        obstacleButton.addActionListener(listener);
    }

    public void connectTerrainButton1(ActionListener listener) {
        terrainButton1.addActionListener(listener);
    }

    public void connectTerrainButton2(ActionListener listener) {
        terrainButton2.addActionListener(listener);
    }

    public void connectTerrainButton3(ActionListener listener) {
        terrainButton3.addActionListener(listener);
    }

    public void connectTerrainButton4(ActionListener listener) {
        terrainButton4.addActionListener(listener);
    }

    public void connectLastFieldButton(ActionListener listener) {
        lastFieldButton.addActionListener(listener);
    }

    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    public void generateRandomObstacles() {
        applyToAllTiles(tile -> {
            if (tile.getMarker() == Marker.OBSTACLE) {
                tile.setBackground(Marker.NONE.getColor());
                tile.setMarker(Marker.NONE);
            }
            if (tile.getMarker() != Marker.NONE)
                return;

            if (random.nextDouble() > 0.7) {
                tile.setBackground(Marker.OBSTACLE.getColor());
                tile.setMarker(Marker.OBSTACLE);
            }
        });

    }

    private void applyToAllTiles(Consumer<GameTile> tileConsumer) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tileConsumer.accept(tiles[i][j]);
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

    public void setMouseAdapterToTiles(MouseAdapter adapter) {
        setMouseAdapterToTiles(ignored -> adapter);
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

    public void enableObstacleButton(boolean state) {
        obstacleButton.setEnabled(state);
    }

    public void enableTerrainButton(boolean state) {
        terrainButton1.setEnabled(state);
    }

    public void enableLastFieldButton(boolean state) {
        lastFieldButton.setEnabled(state);
    }

    public void markTile(int i, int j, Marker marker) {
        tiles[i][j].setMarker(marker);
    }

    public GameTile getTile(int row, int col) {
        // TODO : handle index out of bounds
        try {
            return tiles[row][col];
        } catch (Exception e) {
            return null;
        }
    }

    public Collection<GameTile> getNeighbors(int row, int col) {
        List<GameTile> neighbors = new ArrayList<>(AREA_VECTORS.length);
        for (int[] vector : AREA_VECTORS) {
            try {
                neighbors.add(tiles[row + vector[0]][col + vector[1]]);
            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }
        return neighbors;
    }

    public Collection<GameTile> getNeighbors(GameTile tile) {
        return getNeighbors(tile.getRow(), tile.getCol());
    }

    public Collection<GameTile> getUnmarkedNeighbors(GameTile tile) {
        return getUnmarkedNeighbors(tile.getRow(), tile.getCol());
    }

    public Collection<GameTile> getUnmarkedNeighbors(int row, int col) {
        return getNeighbors(row, col).stream()
                .filter(t -> t.getMarker().equals(Marker.NONE))
                .collect(Collectors.toSet());
    }

    public Marker getMarker(int i, int j) {
        return tiles[i][j].getMarker();
    }

    public String getAlgorithm() {
        return "bfs";
    }

    public Marker[][] getField() {
        Marker[][] markers = new Marker[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                markers[i][j] = tiles[i][j].getMarker();
            }
        }

        return markers;
    }

    public void setField(Marker[][] field) {
        if (field.length != ROWS || field[0].length != COLS) {
            throw new RuntimeException("Invalid field size");
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                var tile = tiles[i][j];
                var marker = field[i][j];

                tile.setMarker(marker);
                tile.setBackground(marker.getColor());
                tile.repaint();
            }
        }
    }

    public void clear() {
        applyToAllTiles(GameTile::clear);
    }
}

