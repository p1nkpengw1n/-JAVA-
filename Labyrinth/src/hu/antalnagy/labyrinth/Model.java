package hu.antalnagy.labyrinth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Scanner;

class Model {

    boolean checkWin = false;
    boolean checkDeath = false;
    int level;
    private View v;
    Timer timer1;
    Timer timer2;
    private Database db = new Database();

    Model(View v, int level) {
        this.v = v;
        this.level = level;
        readWallsFromInputFile(level);
        timer1 = new Timer(1500, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.checkDragonMovement();
            }
        });
        timer1.start();
        timer2 = new Timer(0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                v.renderPlayerVisibilityRange();
                checkDeath = checkDeath();
                checkWin = checkWin();
            }
        });
        timer2.start();
    }

    private void readWallsFromInputFile(int lvl) {
        try (Scanner sc = new Scanner(new File("src/hu/antalnagy/labyrinth/resources/walls/" + lvl +
                ".txt"))) {
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 12; j++) {
                    if (sc.hasNextLine()) {
                        String[] token = sc.nextLine().split(",");
                        v.renderWalls(Integer.parseInt(token[0]), Integer.parseInt(token[1]));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkDeath() {
        if (Math.abs(v.player.currentPositionX - v.dragon.currentPositionX) < 2 && Math.abs(v.player.currentPositionY - v.dragon.currentPositionY) < 2) {
            timer1.stop();
            timer2.stop();
            Score s = new Score(level, (System.nanoTime() - v.startTime) / 100000000, popUpDialogBox());
            db.flushToDb(s);
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        if (v.player.currentPositionX == 0 && v.player.currentPositionY == 11) {
            timer1.stop();
            timer2.stop();
            if(level == 10) {
                Score s = new Score(level, (System.nanoTime() - v.startTime) / 100000000, popUpDialogBox());
                db.flushToDb(s);
                Application.timer.stop();
            }
            return true;
        }
        return false;
    }

    void disposeWindow(boolean last) {
        if (!last) {
            popUpAlertWin();
            v.setVisible(false);
            v.dispose();
        } else if (level == 10) {
            popUpAlertFinish();
            v.setVisible(false);
            v.dispose();
        } else {
            popUpAlertDeath();
            level = 1;
            v.setVisible(false);
            v.dispose();
        }
    }

    private void popUpAlertDeath() {
        JOptionPane.showMessageDialog(null, "You've been caught, grilled and killed by the dragon's breath!" +
                "\n                   Click on OK to restart from level 1!", "💀😥", JOptionPane.INFORMATION_MESSAGE);
    }

    private void popUpAlertWin() {
        JOptionPane.showMessageDialog(null, "You found the way out! Click on OK to enter your name and start the next level!", "😍😍😍", JOptionPane.INFORMATION_MESSAGE);
    }

    private void popUpAlertFinish() {
        JOptionPane.showMessageDialog(null, "Congratulations! You finished the game!", "🤩🤩🤩", JOptionPane.INFORMATION_MESSAGE);
    }

    private String popUpDialogBox() {
        return JOptionPane.showInputDialog(null,
                "Enter your name:\n",
                "Name please!",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}
