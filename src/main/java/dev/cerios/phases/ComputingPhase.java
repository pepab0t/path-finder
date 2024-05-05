package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Marker;
import dev.cerios.Model;
import dev.cerios.tiles.GameTile;

public class ComputingPhase extends GamePhaseTemplate {

    public ComputingPhase(MainView view, Model model) {
        this(view, model, null);
    }

    public ComputingPhase(MainView view, Model model, Runnable endCallback) {
        super(view, model, endCallback);
    }

    @Override
    public void start() {
        view.setInfoText("Computing ...");
        view.setMouseAdapterToTiles(tile -> null);
        view.enableStartButton(false);

        model.setAfterCompute(steps -> {
            view.setInfoText(String.format("Required %d steps", steps));
            end();
        });

        model.compute(view.getInput(),
                "",
                (i, j) -> {
                    Marker currentMarker = view.getMarker(i, j);
                    if (currentMarker != Marker.END && currentMarker != Marker.START) {
                        GameTile tile = view.getTile(i, j);
//                        tile.setBackground(Marker.RESULT.getColor());
                        tile.stepOn();
                        tile.setMarker(Marker.RESULT);
                    }
                },
                (i, j) -> {
                    view.getTile(i, j).setBackground(Marker.PATH.getColor());
                    System.out.println(i + ", " + j);
                });
    }

    @Override
    protected void end() {
        view.enableStartButton(true);
        super.end();
    }
}
