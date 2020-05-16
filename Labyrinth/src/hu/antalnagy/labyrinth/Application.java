package hu.antalnagy.labyrinth;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Application {

    static Timer timer;
    static Game g;

    public static void main(String[] args) {
        g = new Game(1, System.nanoTime());
        long startTime = System.nanoTime();
        timer = new Timer(200, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (g.m.checkDeath) {
                    g.m.disposeWindow(true);
                    g = new Game(1, System.nanoTime());
                    timer.restart();
                } else if (g.m.checkWin) {
                    if (g.m.level < 10) {
                        g.m.disposeWindow(false);
                        g = new Game(++g.m.level, startTime);
                    } else {
                        timer.stop();
                        g.m.disposeWindow(true);
                    }
                }
            }
        });
        timer.setInitialDelay(500);
        timer.start();
    }
}
