package dev.cerios;

import dev.cerios.phases.*;

import java.util.LinkedList;

public class Controller {

    private final MainView view;
    private int nextPhaseIndex = 0;

    private final LinkedList<GamePhase> phases = new LinkedList<>();

    public Controller(MainView view, Model model) {
        this.view = view;

        phases.add(new BlankPhase(view, model, this::nextPhase));
        phases.add(new StartPointPhase(view, model, this::nextPhase));
        phases.add(new EndPointPhase(view, model, this::nextPhase));
        phases.add(new ObstaclePhase(view, model, this::nextPhase));
        phases.add(new ComputingPhase(view, model, null));

        view.connectRestartButton(e -> {
            nextPhaseIndex = 0;
            nextPhase();
        });

        view.connectRandomButton(e -> {
            view.generateRandomObstacles();
        });
    }

    private void nextPhase() {
        phases.get(nextPhaseIndex++).start();
    }

    public void run() {
        view.initGui();
        nextPhase();
        view.run();
    }
}
