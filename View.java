import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;

import java.awt.*;

import java.util.Random;

public class View extends JFrame {
    JPanel mainPanel;
    JTable table;
    JLabel cellCount;
    JLabel roundCount;

    private int r;

    char[][] universe;

    Model m;

    public View(Model m) {
        super("Game of Life");
        this.m = m;
        this.r = 1;
        this.universe = new char[m.getN()][m.getN()];

        cellCount = new JLabel();
        roundCount = new JLabel();
        add(cellCount,BorderLayout.PAGE_START);
        add(roundCount,BorderLayout.LINE_START);
        roundCount.setText("Round: " + r);

        TableModel dataModel = new AbstractTableModel(){
        
            private static final long serialVersionUID = 7L;

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return universe[rowIndex][columnIndex];
            }
        
            @Override
            public int getRowCount() {
                return m.getN();
            }
        
            @Override
            public int getColumnCount() {
                return m.getN();
            }
    
            @Override
            public boolean isCellEditable(int row, int col) { 
                return true; 
            }
    
            @Override
            public void setValueAt(Object value, int row, int col) {
                universe[row][col] = (char) value;
                fireTableCellUpdated(row, col);
            }
        };

        this.table = new JTable(dataModel);
        setSize(m.getN()+700,m.getN()+700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //setLayout(new GridLayout(m.getN(),m.getN()));
        table.setSize(m.getN(),m.getN());
        m.populate();
        cellCount.setText("Alive cell count: " + m.countAlive());
        addSymbols();
        add(table,BorderLayout.CENTER);
        System.out.println(m.getN());
    }

    private void addSymbols() {
        for(int i=0; i<m.getN(); i++) {
            for(int j=0; j<m.getN(); j++) {
                // JTextArea txt = new JTextArea(new Random().nextBoolean() ? "O" : " ");
                // add(txt);
                char c = m.getUniverse()[i][j];
                table.setValueAt(c, i, j);
            }
        }
    }

    void updateUniverse() {
        m.modify();
        m.modifyCurrent();
        for(int i=0; i<m.getN(); i++) {
            for(int j=0; j<m.getN(); j++) {
                // JTextArea txt = new JTextArea(new Random().nextBoolean() ? "O" : " ");
                // add(txt);
                char c = m.getUniverse()[i][j];
                table.setValueAt(c, i, j);
            }
        }
        cellCount.setText("Alive cell count: " + m.countAlive());
        ++r;
        roundCount.setText("Round: " + r);
    }
    
}