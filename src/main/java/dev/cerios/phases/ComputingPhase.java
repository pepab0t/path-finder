package dev.cerios.phases;

import dev.cerios.MainView;


public class ComputingPhase implements GamePhase {
    private Runnable endCallback;

    @Override
    public void start(MainView view) {
        view.setMouseAdapterToTiles(tile -> null);
        view.enableStartButton(false);
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
