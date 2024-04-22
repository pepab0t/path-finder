package dev.cerios.tiles;

import dev.cerios.Marker;

import javax.swing.*;

public class GameTile extends JPanel {

    private Marker marker = Marker.NONE;
    private Runnable callback = null;

    public GameTile() {
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
}
