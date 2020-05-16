package hu.antalnagy.connect4;

import javax.swing.*;
import java.util.Objects;

class Controller {

    Controller(View v, Model m) {

        for(JButton[] buttonRow : v.gridButtons) {
            for(JButton button : buttonRow) {
                button.setEnabled(false);
            }
        }
        for(JButton button : v.borderButtons) {
            button.addActionListener(e -> {
                boolean isWin = m.handleButton(Integer.parseInt(button.getText()));
                if(isWin) {
                    m.popUpAlertWin();
                    Application.newGame("SMALL");
                    v.setVisible(false);
                    v.dispose();
                }
                else if(m.isDraw()) {
                    m.popUpAlertDraw();
                    Application.newGame("SMALL");
                    v.setVisible(false);
                    v.dispose();
                }
            });
        }
        v.comboBox.addActionListener(e -> {
            JComboBox cb = (JComboBox) e.getSource();
            String[] size = ((String) Objects.requireNonNull(cb.getSelectedItem())).split("_");
            Application.newGame(size[0]);
            v.setVisible(false);
            v.dispose();
        });

    }
}
