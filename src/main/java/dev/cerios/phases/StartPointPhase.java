package dev.cerios.phases;

import dev.cerios.tiles.GameTile;
import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.mousehandlers.ClickMouseAdapter;

import java.awt.event.MouseAdapter;

public class StartPointPhase implements GamePhase {

    private Runnable endCallback;

    @Override
    public void start(MainView view) {
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.START));
        view.setClickObserverToTiles(this::end);
    }

    @Override
    public StartPointPhase onEnd(Runnable runnable) {
        this.endCallback = runnable;
        return this;
    }

    private void end() {
        if (endCallback == null)
            return;
        endCallback.run();
    }
}
