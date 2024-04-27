package dev.cerios;

import dev.cerios.phases.*;

import java.util.LinkedList;

public class Controller {

    private final MainView view;
    private final Model model;
    private int nextPhaseIndex = 0;

    private final LinkedList<GamePhase> phases = new LinkedList<>();

    public Controller(MainView view, Model model) {
        this.view = view;
        this.model = model;

        phases.add(new BlankPhase().onEnd(this::nextPhase));
        phases.add(new StartPointPhase().onEnd(this::nextPhase));
        phases.add(new EndPointPhase().onEnd(this::nextPhase));
        phases.add(new ObstaclePhase().onEnd(this::nextPhase));
        phases.add(new ComputingPhase().onEnd(null));

        view.connectRestartButton(e -> {
            nextPhaseIndex = 0;
            nextPhase();
        });

        view.connectRandomButton(e -> {
            view.generateRandomObstacles();
        });
    }

    private void nextPhase() {
        phases.get(nextPhaseIndex++).start(view, model);
    }

    public void run() {
        view.initGui();
        nextPhase();
        view.run();
    }

}
