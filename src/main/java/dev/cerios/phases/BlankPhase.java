package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Model;

public class BlankPhase implements GamePhase{
    private Runnable endCallback;

    @Override
    public void start(MainView view, Model model) {
        view.clear();

        if (endCallback != null)
            endCallback.run();
    }

    @Override
    public GamePhase onEnd(Runnable runnable) {
        endCallback = runnable;
        return this;
    }
}
