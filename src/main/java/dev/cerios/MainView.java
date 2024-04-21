package dev.cerios;

import dev.cerios.tiles.GameTile;
import dev.cerios.tiles.TileFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import java.util.function.Function;

import static dev.cerios.Config.*;

public class MainView extends JFrame {
    private final GameTile[][] gridPanels = new GameTile[ROWS][COLS];
    private final TileFactory tileFactory;

    private final JButton startButton;
    private final JButton restartButton;

    public MainView(TileFactory tileFactory) throws HeadlessException {
        this.tileFactory = tileFactory;
        startButton = new JButton("Start");
        startButton.setEnabled(false);
        restartButton = new JButton("Restart");
    }

    public void initGui() {
        setTitle("Grid Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 0));

        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(ROWS, COLS, X_SPACING, Y_SPACING));

        // Initialize the grid of panels
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                GameTile tile = tileFactory.create();
                gridPanels[i][j] = tile;
                gridPanel.add(tile);
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(startButton);
        buttonPanel.add(restartButton);


        // Add the grid panel and button panel to the frame
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    public void connectStartButton(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public void disconnectStartButton() {
        for (var listener : startButton.getActionListeners()) {
            startButton.removeActionListener(listener);
        }
    }

    public void connectRestartButton(ActionListener listener) {
        restartButton.addActionListener(listener);
    }

    private void applyToAllTiles(Consumer<GameTile> tileConsumer) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                var tile = gridPanels[i][j];
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
        gridPanels[i][j].setMarker(marker);
    }

    public String getAlgorithm() {
        return "bfs";
    }

    public Marker[][] getInput() {
        Marker[][] markers = new Marker[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                markers[i][j] = gridPanels[i][j].getMarker();
            }
        }

        return markers;
    }

    public void clear() {
        applyToAllTiles(tile -> tile.setMarker(Marker.NONE));
    }
}

