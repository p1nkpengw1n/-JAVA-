package hu.antalnagy.labyrinth;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

class HighScoreTableView extends JFrame {

    JButton newGameButton;

    HighScoreTableView(ArrayList<Score> topTen) {
        super("Hall of Fame");
        String[] columnNames = {"Name",
                "Level",
                "Time"};
        String[][] data = new String[topTen.size()][3];
        int i = 0;
        for(Score score: topTen) {
            data[i][0] = score.getName();
            data[i][1] = String.valueOf(score.getLevel());
            data[i][2] = score.getTime()/10 + "." + score.getTime()%100 + " seconds";
            i++;
        }
        JTable table = new JTable(data, columnNames);
        newGameButton = new JButton("Start New Game");
        add(new JScrollPane(table), BorderLayout.NORTH);
        add(newGameButton, BorderLayout.SOUTH);
        setSize(300, 400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
