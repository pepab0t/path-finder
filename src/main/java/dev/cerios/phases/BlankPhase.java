package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Model;

public class BlankPhase extends  GamePhaseTemplate{
    @Override
    public void start(MainView view, Model model) {
        view.clear();
        end();
    }
}
