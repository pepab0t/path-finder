package dev.cerios.tiles;

import dev.cerios.Config;
import dev.cerios.Marker;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static dev.cerios.Config.RESULT_RADIUS;

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

    public boolean setMarkerWeighted(Marker marker) {
        boolean condition = canBeMarkedWith(marker);
        if (condition) this.marker = marker;
        return condition;
    }

    /**
     * @param marker new marker
     * @return boolean meaning if new marker has higher than current marker
     */
    public boolean canBeMarkedWith(Marker marker) {
        return this.marker.getLevel() < marker.getLevel();
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

    public void stepOn() {
        Graphics g = this.getGraphics();
        g.setColor(Config.RESULT_COLOR);
        int d = 2 * RESULT_RADIUS;
        g.fillOval(this.getWidth() / 2 - RESULT_RADIUS, this.getHeight() / 2 - RESULT_RADIUS, d, d);
    }

    public void clear() {
        setBackground(Marker.NONE.getColor());
        repaint();
        setMarker(Marker.NONE);
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
