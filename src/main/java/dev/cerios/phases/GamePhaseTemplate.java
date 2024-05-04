package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Model;

public abstract class GamePhaseTemplate implements GamePhase {
    protected Runnable endCallback;
    protected final MainView view;
    protected final Model model;

    public GamePhaseTemplate(MainView view, Model model) {
        this(view, model, null);
    }

    public GamePhaseTemplate(MainView view, Model model, Runnable endCallback) {
        this.view = view;
        this.model = model;
        this.endCallback = endCallback;
    }

    @Override
    public abstract void start();

    /**
     * Runs {@code endCallback} if not null.
     */
    protected void end() {
        if (endCallback != null) endCallback.run();
    }
}
