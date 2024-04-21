package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;

public class ComputingPhase implements GamePhase {
    private Runnable endCallback;

    @Override
    public void start(MainView view, Model model) {
        view.setMouseAdapterToTiles(tile -> null);
        view.enableStartButton(false);

        model.compute(view.getInput(), "", (i, j) -> view.markTile(i,j, Marker.PATH));
    }

    private void end(MainView view) {
        view.enableStartButton(true);
        if (endCallback != null) endCallback.run();
    }

    @Override
    public ComputingPhase onEnd(Runnable runnable) {
        endCallback = runnable;
        return this;
    }
}
