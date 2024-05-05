package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;
import dev.cerios.mousehandlers.ClickMouseAdapter;


public class EndPointPhase extends GamePhaseTemplate {

    public EndPointPhase(MainView view, Model model) {
        super(view, model);
    }

    public EndPointPhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.setInfoText("Place an END POINT");
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.END));
        view.setClickObserverToTiles(this::end);
    }
}
