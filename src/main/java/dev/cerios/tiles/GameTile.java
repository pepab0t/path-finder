package dev.cerios.tiles;

import dev.cerios.Marker;

import javax.swing.*;

public class GameTile extends JPanel {

    private Marker marker = Marker.NONE;
    private Runnable callback = null;
    private final int row;
    private final int col;

    public GameTile(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void sendClickNotification() {
        if (callback == null)
            return;
        callback.run();
    }

    public void setClickObserver(Runnable callback) {
        this.callback = callback;
    }

    public void setMarker(Marker marker) {
        this.setBackground(marker.getColor());
        this.marker = marker;
    }

    public Marker getMarker() {
        return marker;
    }

    public boolean isMarked() {
        return !marker.equals(Marker.NONE);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
