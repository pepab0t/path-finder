package dev.cerios.tiles;

import dev.cerios.Marker;

import javax.swing.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameTile gameTile)) return false;
        return row == gameTile.row && col == gameTile.col && marker == gameTile.marker;
    }

    @Override
    public int hashCode() {
        return Objects.hash(marker, row, col);
    }
}
