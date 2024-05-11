package dev.cerios.phases;

import dev.cerios.View;
import dev.cerios.Marker;
import dev.cerios.Model;
import dev.cerios.mousehandlers.ClickMouseAdapter;


public class StartPointPhase extends GamePhaseTemplate {
    public StartPointPhase(View view, Model model) {
        super(view, model);
    }

    public StartPointPhase(View view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.setInfoText("Place a START POINT");
        view.setMouseAdapterToTiles(tile -> new ClickMouseAdapter(tile, Marker.START));
        view.setClickObserverToTiles(this::end);
    }

    @Override
    protected void end() {
        view.setInfoText("");
        super.end();
    }
}
