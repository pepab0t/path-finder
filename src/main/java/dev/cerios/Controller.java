package dev.cerios;

import dev.cerios.phases.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    private final View view;
    private int nextPhaseIndex = 0;

    private final Object lock = new Object();

    private final List<GamePhase> phases = new ArrayList<>(7);

    public Controller(View view, Model model) {
        this.view = view;

        phases.add(new BlankPhase(view, model, this::nextPhase));
        phases.add(new StartPointPhase(view, model, this::nextPhase));
        phases.add(new EndPointPhase(view, model, this::nextPhase));
        phases.add(new ObstaclePhase(view, model, this::nextPhase));
        phases.add(new ComputingPhase(view, model));

        view.connectRestartButton(e -> {
            nextPhaseIndex = 0;
            nextPhase();
        });

        view.connectRandomButton(e -> {
            view.generateRandomObstacles();
        });

        view.connectLastFieldButton(e -> {
            view.setField(model.getLastField());
            nextPhaseIndex = 3;
            nextPhase();
        });
    }

    private void nextPhase() {
        synchronized (lock) {
            phases.get(nextPhaseIndex++).start();
        }
    }

    public void run() {
        view.initGui();
        nextPhase();
        view.run();
    }
}
