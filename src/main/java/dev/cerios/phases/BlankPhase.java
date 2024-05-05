package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Model;

public class BlankPhase extends GamePhaseTemplate{

    public BlankPhase(MainView view, Model model) {
        super(view, model);
    }

    public BlankPhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.clear();
        view.setInfoText("");
        end();
    }
}
