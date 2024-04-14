package dev.cerios;

import dev.cerios.phases.*;

import java.util.LinkedList;

public class Controller {

    private final MainView view;

    private final LinkedList<GamePhase> phases = new LinkedList<>();

    public Controller(MainView view) {
        this.view = view;

        phases.add(new StartPointPhase().onEnd(this::nextPhase));
        phases.add(new EndPointPhase().onEnd(this::nextPhase));
        phases.add(new ObstaclePhase().onEnd(this::nextPhase));
        phases.add(new ComputingPhase().onEnd(null));
    }

    private void nextPhase() {
        phases.remove().start(view);
    }


    public void run() {
        view.initGui();

        GamePhase phase = phases.remove();
        phase.start(view);

        view.run();
    }

}
