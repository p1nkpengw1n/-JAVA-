package hu.antalnagy.connect4;

import javax.swing.*;
import java.awt.*;

class View extends JFrame {

    private static final long serialVersionUID = 7L;

    private TableSize ts;

    private JPanel gridPanel;
    private JPanel borderPanel;
    JButton[][] gridButtons;
    JButton[] borderButtons;
    JComboBox<String> comboBox;

    int n,m;

    View(TableSize ts, int n, int m) {
        super("Potyogós amőba");
        setSize(700,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        this.n = n;
        this.m = m;
        this.ts = ts;
        comboBox = new JComboBox<>(ts.toStringImpl());
        if(ts == TableSize.SMALL) {
            comboBox.setSelectedIndex(0);
        }
        else if(ts == TableSize.MEDIUM) {
            comboBox.setSelectedIndex(1);
        }
        else {
            comboBox.setSelectedIndex(2);
        }
        GridLayout layoutInner = new GridLayout(n, m, 0, 0);
        gridPanel = new JPanel();
        gridPanel.setLayout(layoutInner);
        gridButtons = new JButton[n][m];
        borderButtons = new JButton[m];
        new JOptionPane();

        addButtonsToGridPanel();

        BorderLayout layoutOuter = new BorderLayout();
        borderPanel = new JPanel();
        borderPanel.setLayout(layoutOuter);

        addButtonsToBorderPanel();

        borderPanel.add(gridPanel,BorderLayout.CENTER);
        add(borderPanel);

        setVisible(true);
    }

    private void addButtonsToGridPanel() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                gridButtons[i][j] = new JButton("");
                gridPanel.add(gridButtons[i][j]);
            }
        }
    }

    private void addButtonsToBorderPanel() {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel,BoxLayout.PAGE_AXIS));
        tempPanel.add(comboBox);
        JPanel tempPanel2 = new JPanel();
        if(ts == TableSize.SMALL) {
            tempPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        else if(ts == TableSize.MEDIUM) {
            tempPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        else {
            tempPanel2.setLayout(new FlowLayout(FlowLayout.CENTER));
        }
        for (int i = 0; i < m; i++) {
            borderButtons[i] = new JButton(String.valueOf(i+1));
            tempPanel2.add(borderButtons[i]);
        }
        tempPanel.add(tempPanel2);
        borderPanel.add(tempPanel,BorderLayout.PAGE_START);
    }
}
