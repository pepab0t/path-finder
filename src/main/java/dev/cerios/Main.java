package dev.cerios;

import dev.cerios.tiles.TileFactory;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        MainView window = new MainView(new TileFactory());

        Controller controller = new Controller(window);

        controller.run();
    }
}