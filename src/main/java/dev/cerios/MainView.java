package dev.cerios;

import dev.cerios.tiles.GameTile;
import dev.cerios.tiles.TileFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.util.function.Consumer;
import java.util.function.Function;

import static dev.cerios.Config.*;

public class MainView extends JFrame {
    private final GameTile[][] gridPanels = new GameTile[ROWS][COLS];
    private final TileFactory tileFactory;

    private JButton startButton;

    public MainView(TileFactory tileFactory) throws HeadlessException {
        this.tileFactory = tileFactory;
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
                GameTile tile = tileFactory.create();
                gridPanels[i][j] = tile;
                gridPanel.add(tile);
            }
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        startButton = new JButton("Start");
        startButton.setEnabled(false);
        buttonPanel.add(startButton);

        // Add the grid panel and button panel to the frame
        add(gridPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }

    private void connectButton(JButton button, Runnable callback) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callback.run();
            }
        });
    }

    public void connectStartButton(Runnable callback) {
        connectButton(startButton, callback);
    }

    public void disconnectStartButton() {
        for (var listener : startButton.getActionListeners()) {
            startButton.removeActionListener(listener);
        }
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
        applyToAllTiles(tile -> {
            tile.setClickObserver(callback);
        });
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

    public Marker[][] getInput() {
        Marker[][] markers = new Marker[ROWS][COLS];

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                markers[i][j] = gridPanels[i][j].getMarker();
            }
        }

        return markers;
    }
}

