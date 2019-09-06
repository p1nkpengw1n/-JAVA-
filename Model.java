import java.io.IOException;
import java.util.Random;

public class Model {
    private int n;
    private int m;
    private static int genNumIncrementer = 1;
    private int aliveCount;

    Random rand;

    private char[][] universe;
    private char[][] nextGen;

    public Model() {
        rand = new Random(System.currentTimeMillis());
        n = rand.nextInt(40) + 10;
        m = rand.nextInt(99) + 2;
        universe = new char[n][n];
        nextGen = new char[n][n];
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public char[][] getUniverse() {
        return universe;
    }

    void populate() {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n;j++) {
                universe[i][j] = rand.nextBoolean() ? 'O' : ' ';
            }
        }
        aliveCount = countAlive();
    }

    void modify() {
        if(2 == n) {
            for(int i=0;i<2;i++) {
                for(int j=0;j<2;j++) {
                    if(0 == i && 0 == j) {
                        int neighbours = 0;
                        if(universe[0][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours<2 || neighbours>3 ? ' ' : 'O';
                        }
                        else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    }
                    else if(0 == i && 1 == j) {
                        int neighbours = 0;
                        if(universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours<2 || neighbours>3 ? ' ' : 'O';
                        }
                        else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    }
                    else if(1 == i && 0 == j) {
                        int neighbours = 0;
                        if(universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[0][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours<2 || neighbours>3 ? ' ' : 'O';
                        }
                        else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    }
                    else {
                        int neighbours = 0;
                        if(universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[0][1] == 'O') {
                            neighbours++;
                        }
                        if(universe[1][0] == 'O') {
                            neighbours++;
                        }
                        if(universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours<2 || neighbours>3 ? ' ' : 'O';
                        }
                        else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    }
                }
            }
        }
        else if(n>2) {
            for(int i=0; i<n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == 0 && j == 0) {
                        int neighbours = 0;
                        if (universe[n - 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j + 1] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = (neighbours < 2 || neighbours > 3) ? ' ' : 'O';
                        } else nextGen[i][j] = (neighbours == 3) ? 'O' : ' ';
                    } else if (i == 0 && j == n - 1) {
                        int neighbours = 0;
                        if (universe[n - 1][n - 2] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[1][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[1][j] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = (neighbours < 2 || neighbours > 3) ? ' ' : 'O';
                        } else nextGen[i][j] = (neighbours == 3) ? 'O' : ' ';
                    } else if (i == n - 1 && j == 0) {
                        int neighbours = 0;
                        if (universe[n - 2][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j + 1] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = (neighbours < 2 || neighbours > 3) ? ' ' : 'O';
                        } else nextGen[i][j] = (neighbours == 3) ? 'O' : ' ';
                    } else if (i == n - 1 && j == n - 1) {
                        int neighbours = 0;
                        if (universe[n - 2][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][n - 2] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours < 2 || neighbours > 3 ? ' ' : 'O';
                        } else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    } else if (i == 0) {
                        int neighbours = 0;

                        if (universe[n - 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[n - 1][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j + 1] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours < 2 || neighbours > 3 ? ' ' : 'O';
                        } else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    } else if (i == n - 1) {
                        int neighbours = 0;

                        if (universe[i - 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[0][j + 1] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours < 2 || neighbours > 3 ? ' ' : 'O';
                        } else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    } else if (j == 0) {
                        int neighbours = 0;

                        if (universe[i - 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][n - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j + 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j + 1] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours < 2 || neighbours > 3 ? ' ' : 'O';
                        } else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    } else if (j == n - 1) {
                        int neighbours = 0;

                        if (universe[i - 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j - 1] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][j] == 'O') {
                            neighbours++;
                        }
                        if (universe[i - 1][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[i][0] == 'O') {
                            neighbours++;
                        }
                        if (universe[i + 1][0] == 'O') {
                            neighbours++;
                        }

                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = neighbours < 2 || neighbours > 3 ? ' ' : 'O';
                        } else nextGen[i][j] = neighbours == 3 ? 'O' : ' ';
                    } else if (i > 0 && i < n - 1 && j > 0 && j < n - 1) {
                        if (universe[i][j] == 'O') {
                            nextGen[i][j] = countNeighbours(i, j) == 2 || countNeighbours(i, j) == 3 ? 'O' : ' ';
                        } else nextGen[i][j] = countNeighbours(i, j) == 3 ? 'O' : ' ';
                    }
                }
            }
        }
        aliveCount = countAlive();
    }

    void modifyCurrent() {
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                universe[i][j] = nextGen[i][j];
            }
        }
    }

    private int countNeighbours(int i,int j) {
        int neighbours = 0;

        if(universe[i-1][j-1] == 'O') {
            neighbours++;
        }
        if(universe[i-1][j] == 'O') {
            neighbours++;
        }
        if(universe[i-1][j+1] == 'O') {
            neighbours++;
        }
        if(universe[i][j-1] == 'O') {
            neighbours++;
        }
        if(universe[i][j+1] == 'O') {
            neighbours++;
        }
        if(universe[i+1][j-1] == 'O') {
            neighbours++;
        }
        if(universe[i+1][j] == 'O') {
            neighbours++;
        }
        if(universe[i+1][j+1] == 'O') {
            neighbours++;
        }

        return neighbours;
    }

    int countAlive() {
        int num = 0;

        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                if(universe[i][j] == 'O') {
                    num++;
                }
            }
        }

        return num;
    }

    // public void play() {
    //     populate();
    //     display();
    //     while(m > 0) {
    //         modify();
    //         modifyCurrent();
    //         display();
    //         --m;
    //     }
    // }
}