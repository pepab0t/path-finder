package dev.cerios.phases;

import dev.cerios.MainView;
import dev.cerios.Model;

public interface GamePhase {
    void start(MainView view, Model model);
    GamePhase onEnd(Runnable runnable);
}
