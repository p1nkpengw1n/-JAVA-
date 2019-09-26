package hu.antalnagy.algo;

public class Board {
    private final int n;
    private static char[][] board;

    public Board(int n) {
        this.n = n;
        board = new char[n][n];
        init();
    }

    private void init() {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                board[i][j] = '*';
            }
        }
    }

    public void show() {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
    }

    public void placeKnight(String place) {
        String firstChar = place.substring(0,1);
        int first;
        int second = Integer.parseInt(place.substring(1,2)) - 1;
        switch(firstChar) {
            case "A":
                first = 0;
                break;
            case "B":
                first = 1;
                break;
            case "C":
                first = 2;
                break;
            case "D":
                first = 3;
                break;
            case "E":
                first = 4;
                break;
            case "F":
                first = 5;
                break;
            case "G":
                first = 6;
                break;
            case "H":
                first = 7;
                break;
            default:
                throw new IllegalArgumentException();
        }
        board[first][second] = 'K';
    }
}
