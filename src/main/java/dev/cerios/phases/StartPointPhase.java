package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;
import dev.cerios.mousehandlers.ClickMouseAdapter;


public class StartPointPhase extends GamePhaseTemplate {
    public StartPointPhase(MainView view, Model model) {
        super(view, model);
    }

    public StartPointPhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.START));
        view.setClickObserverToTiles(this::end);
    }
}
