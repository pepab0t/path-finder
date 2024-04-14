package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.mousehandlers.ClickMouseAdapter;


public class EndPointPhase implements GamePhase {

    private Runnable endCallback;

    private void end() {
        if (endCallback == null)
            return;
        endCallback.run();
    }

    @Override
    public void start(MainView view) {
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.END));
        view.setClickObserverToTiles(this::end);
    }

    @Override
    public EndPointPhase onEnd(Runnable runnable) {
        endCallback = runnable;
        return this;
    }

}
