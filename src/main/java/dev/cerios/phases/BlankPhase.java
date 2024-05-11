package dev.cerios.phases;

import dev.cerios.View;
import dev.cerios.Model;

public class BlankPhase extends GamePhaseTemplate{

    public BlankPhase(View view, Model model) {
        super(view, model);
    }

    public BlankPhase(View view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.clear();
        view.setInfoText("");
        end();
    }
}
