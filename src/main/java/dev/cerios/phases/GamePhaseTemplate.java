package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;

public abstract class GamePhaseTemplate implements GamePhase{
    private Runnable endCallback;

    @Override
    public abstract void start(MainView view, Model model);

    /**
     * Runs {@code endCallback} if not null.
     */
    protected void end() {
        if (endCallback != null) endCallback.run();
    }

    @Override
    public GamePhase onEnd(Runnable runnable) {
        endCallback = runnable;
        return this;
    }
}
