package dev.cerios.phases;

import dev.cerios.View;
import dev.cerios.Model;

public abstract class GamePhaseTemplate implements GamePhase {
    protected Runnable endCallback;
    protected final View view;
    protected final Model model;

    public GamePhaseTemplate(View view, Model model) {
        this(view, model, null);
    }

    public GamePhaseTemplate(View view, Model model, Runnable endCallback) {
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
