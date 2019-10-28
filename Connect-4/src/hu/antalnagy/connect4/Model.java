package hu.antalnagy.connect4;

import javax.swing.*;

class Model {
    private View v;

    private String[][] gameTable;
    private int n,m;
    private String prevString = "O";

    Model(View v) {
        this.v = v;
        n = v.n;
        m = v.m;
        gameTable = new String[n][m];
        resetTable();
    }

    //at start or in case of new game
    private void resetTable() {
        for(int i = 0; i < n; i++) {
             for(int j = 0; j < m; j++){
                 gameTable[i][j] = "";
             }
        }
    }

    boolean handleButton(int j) {
        if(prevString.equals("O")) {
            addButtonTextToTable(j-1,"X");
            prevString = "X";
            return isWin();
        }
        addButtonTextToTable(j-1,"O");
        prevString = "O";
        return isWin();
    }

    private void addButtonTextToTable(int j, String c) {
        for(int i = n-1; i >= 0; i--) {
            if(gameTable[i][j].equals("")) {
                gameTable[i][j] = c;
                v.gridButtons[i][j].setText(c);
                return;
            }
        }
    }

    private int countCells() {
        int counter =  0;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < m; j++) {
                if(!gameTable[i][j].equals("")) {
                    counter++;
                }
            }
        }
        return counter;
    }

    boolean isDraw() {
        if(m == 5) {
            return countCells() == 8*5;
        }
        else if(m == 6) {
            return countCells() == 10*6;
        }
        else {
            return countCells() == 12*7;
        }
    }

    private boolean isWin() {
        if(countCells() > 6) {

            //check for columns
            for(int j = 0; j < m; j++) {
                String c = gameTable[0][j];
                int counter = 1;
                for(int i = 1; i < n; i++) {
                    if(gameTable[i][j].equals(c)) {
                        counter++;
                        if(counter == 4 && !c.equals("")) {
                            return true;
                        }
                    }
                    else {
                        counter = 1;
                        c = gameTable[i][j];
                    }
                }
            }

            //check for rows
            for(int i = 0; i < n; i++) {
                String c = gameTable[i][0];
                int counter = 1;
                for(int j = 1; j < m; j++) {
                    if(gameTable[i][j].equals(c)) {
                        counter++;
                        if(counter == 4 && !c.equals("")) {
                            return true;
                        }
                    }
                    else {
                        counter = 1;
                        c = gameTable[i][j];
                    }
                }
            }

            //check for descending diagonals
            for(int k = 0; k < m; k++) {
                for(int i = 0; i < n; i++) {
                    String c = gameTable[i][k];
                    int counter = 1;
                    int desc = 1;
                    for(int j = 1; j < m; j++) {
                        if(i+desc < n && j+k < m) {
                            if(gameTable[i+desc][j+k].equals(c)) {
                                counter++;
                                if(counter == 4 && !c.equals("")) {
                                    return true;
                                }
                            }
                            else {
                                counter = 1;
                                c = gameTable[i+desc][j+k];
                            }
                        }
                        desc++;
                    }
                }
            }

            //check for ascending diagonals
            for(int k = 0; k < m; k++) {
                for(int i = 0; i < n; i++) {
                    String c = gameTable[i][m-1-k];
                    int counter = 1;
                    int desc = 1;
                    for(int j = m-2; j >= 0; j--) {
                        if(i+desc < n && j-k >= 0) {
                            if(gameTable[i+desc][j-k].equals(c)) {
                                counter++;
                                if(counter == 4 && !c.equals("")) {
                                    return true;
                                }
                            }
                            else {
                                counter = 1;
                                c = gameTable[i+desc][j-k];
                            }
                        }
                        desc++;
                    }
                }
            }
        }
        return false;
    }

    void popUpAlertWin() {
        JOptionPane.showMessageDialog(null, prevString + " wins! Click on OK to start a new game!", "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
    }
    void popUpAlertDraw() {
        JOptionPane.showMessageDialog(null,"It's a draw! Click on OK to start a new game!", "50-50!", JOptionPane.INFORMATION_MESSAGE);
    }
}