package dev.cerios.phases;

import dev.cerios.MainView;

public interface GamePhase {
    void start(MainView view);
    GamePhase onEnd(Runnable runnable);
}
