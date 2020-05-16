package hu.antalnagy.labyrinth;

import javax.swing.*;
import java.awt.*;

class Menu extends JFrame {
    JButton newGameButton = new JButton("New Game");
    JButton highScoresButton = new JButton("High Scores");

    Menu() {
        super("Menu");
        FlowLayout flowLayout = new FlowLayout();
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(flowLayout);
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(highScoresButton);
        flowLayout.addLayoutComponent("Buttons", buttonsPanel);
        add(buttonsPanel);
        setSize(300, 80);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
