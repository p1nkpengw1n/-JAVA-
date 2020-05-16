package hu.antalnagy.labyrinth;

import javax.swing.*;
import java.awt.event.ActionEvent;

class Controller {
    private View v;

    Controller(View v) {
        this.v = v;
        setKeyBindings();
    }

    private void setKeyBindings() {
        InputMap inputMap =
                v.borderPanel.getInputMap(JPanel.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("W"), "up arrow");
        inputMap.put(KeyStroke.getKeyStroke("S"), "down arrow");
        inputMap.put(KeyStroke.getKeyStroke("A"), "left arrow");
        inputMap.put(KeyStroke.getKeyStroke("D"), "right arrow");

        v.borderPanel.getActionMap().put("up arrow",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int oldPlayerPositionX = v.player.currentPositionX;
                        v.movePlayer(--oldPlayerPositionX, v.player.currentPositionY);
                    }
                });
        v.borderPanel.getActionMap().put("down arrow",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int oldPlayerPositionX = v.player.currentPositionX;
                        v.movePlayer(++oldPlayerPositionX, v.player.currentPositionY);
                    }
                });
        v.borderPanel.getActionMap().put("left arrow",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int oldPlayerPositionY = v.player.currentPositionY;
                        v.movePlayer(v.player.currentPositionX, --oldPlayerPositionY);
                    }
                });
        v.borderPanel.getActionMap().put("right arrow",
                new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int oldPlayerPositionY = v.player.currentPositionY;
                        v.movePlayer(v.player.currentPositionX, ++oldPlayerPositionY);
                    }
                });
    }
}
