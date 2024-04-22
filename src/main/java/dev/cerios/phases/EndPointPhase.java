package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;
import dev.cerios.mousehandlers.ClickMouseAdapter;


public class EndPointPhase extends GamePhaseTemplate {

    @Override
    public void start(MainView view, Model model) {
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.END));
        view.setClickObserverToTiles(this::end);
    }
}
