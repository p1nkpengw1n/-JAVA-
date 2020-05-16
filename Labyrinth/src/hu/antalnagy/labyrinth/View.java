package hu.antalnagy.labyrinth;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

class View extends JFrame {

    private static final long serialVersionUID = 7L;
    JPanel borderPanel;
    Player player;
    Dragon dragon;
    long startTime;
    private JButton[][] gridButtons;
    private Random rand = new Random();
    private JPanel gridPanel;
    private JTextArea timerPanel;
    private int boardSize = 12;
    private boolean isBlocked = false;

    View(long startTime) {
        super("Labyrinth");
        this.startTime = startTime;
        setSize(900, 900);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GridLayout layoutInner = new GridLayout(boardSize, boardSize);
        gridPanel = new JPanel();
        gridPanel.setLayout(layoutInner);
        gridButtons = new JButton[boardSize][boardSize];

        JButton newGameButton = new JButton("New Game");
        JButton menuButton = new JButton("Menu");

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(newGameButton);
        buttonsPanel.add(menuButton);

        addButtonsToGridPanel();

        BorderLayout layoutOuter = new BorderLayout();
        borderPanel = new JPanel();
        borderPanel.setLayout(layoutOuter);

        borderPanel.add(gridPanel, BorderLayout.CENTER);

        timerPanel = new JTextArea();
        timerPanel.setFont(new Font("Arial", Font.PLAIN, 18));

        Timer timer = new Timer(0, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTimerText();
            }
        });
        timer.setInitialDelay(200);
        timer.start();

        borderPanel.add(timerPanel, BorderLayout.NORTH);
        borderPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(borderPanel);

        player = new Player(11, 0);
        dragon = new Dragon(rand.nextInt(11) + 1, rand.nextInt(9) + 3);

        newGameButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Application.g = new Game(1, System.nanoTime());
                Application.timer.restart();
            }
        });
        menuButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application.g.m.timer1.stop();
                Application.g.m.timer2.stop();
                Application.timer.stop();
                dispose();
                Menu menu = new Menu();
                menu.newGameButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menu.dispose();
                        Application.g = new Game(1, System.nanoTime());
                        Application.timer.restart();
                    }
                });
                menu.highScoresButton.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        menu.dispose();
                        HighScoreTableView hstv = new HighScoreTableView(new Database().loadFromDb());
                        hstv.newGameButton.addActionListener(new AbstractAction() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                hstv.dispose();
                                Application.g = new Game(1, System.nanoTime());
                                Application.timer.restart();
                            }
                        });
                    }
                });
            }
        });

        renderGUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addButtonsToGridPanel() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setEnabled(false);
                gridButtons[i][j].setVisible(false);
                gridPanel.add(gridButtons[i][j]);
            }
        }
    }

    void checkDragonMovement() {
        if (!dragon.isVisible || isBlocked) {
            int c = rand.nextInt(4);
            boolean wallHit;
            if (dragon.currentPositionX == 0 && dragon.currentPositionY == 0) {
                c = rand.nextInt(2) + 1;
            } else if (dragon.currentPositionX == 0 && dragon.currentPositionY == boardSize - 1) {
                if (rand.nextInt(2) == 0) {
                    c = 1;
                } else {
                    c = 3;
                }
            } else if (dragon.currentPositionX == 0) {
                c = rand.nextInt(3) + 1;
            } else if (dragon.currentPositionY == 0 && dragon.currentPositionX == boardSize - 1) {
                if (rand.nextInt(2) == 0) {
                    c = 0;
                } else {
                    c = 2;
                }
            } else if (dragon.currentPositionY == 0) {
                c = rand.nextInt(3);
            } else if (dragon.currentPositionX == boardSize - 1 && dragon.currentPositionY == boardSize - 1) {
                if (rand.nextInt(2) == 0) {
                    c = 0;
                } else {
                    c = 3;
                }
            } else if (dragon.currentPositionX == boardSize - 1) {
                int d = rand.nextInt(3);
                if (d == 0) {
                    c = 0;
                } else if (d == 1) {
                    c = 2;
                } else {
                    c = 3;
                }
            } else if (dragon.currentPositionY == boardSize - 1) {
                int d = rand.nextInt(3);
                if (d == 0) {
                    c = 0;
                } else if (d == 1) {
                    c = 1;
                } else {
                    c = 3;
                }
            }
            int posToChangeTo;
            if (c == 0) {
                posToChangeTo = dragon.currentPositionX - 1;
                wallHit = gridButtons[posToChangeTo][dragon.currentPositionY].getIcon() != null;
            } else if (c == 1) {
                posToChangeTo = dragon.currentPositionX + 1;
                wallHit = gridButtons[posToChangeTo][dragon.currentPositionY].getIcon() != null;
            } else if (c == 2) {
                posToChangeTo = dragon.currentPositionY + 1;
                wallHit = gridButtons[dragon.currentPositionX][posToChangeTo].getIcon() != null;
            } else {
                posToChangeTo = dragon.currentPositionY - 1;
                wallHit = gridButtons[dragon.currentPositionX][posToChangeTo].getIcon() != null;
            }
            if (!wallHit) {
                isBlocked = false;
                try {
                    switch (c) {
                        case 0: //up
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, --dragon.currentPositionX, dragon.currentPositionY);
                            break;
                        case 1: //down
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, ++dragon.currentPositionX, dragon.currentPositionY);
                            break;
                        case 2: //right
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, dragon.currentPositionX, ++dragon.currentPositionY);
                            break;
                        case 3: //left
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, dragon.currentPositionX, --dragon.currentPositionY);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                checkDragonMovement();
            }
        } else {
            int c = -1;
            boolean wallHit;
            if (dragon.currentPositionX < player.currentPositionX && dragon.currentPositionY < player.currentPositionY) {
                if (rand.nextInt(2) == 1) {
                    c = 1;
                } else {
                    c = 2;
                }
            } else if (dragon.currentPositionX < player.currentPositionX && dragon.currentPositionY > player.currentPositionY) {
                if (rand.nextInt(2) == 1) {
                    c = 1;
                } else {
                    c = 3;
                }
            } else if (dragon.currentPositionX > player.currentPositionX && dragon.currentPositionY > player.currentPositionY) {
                if (rand.nextInt(2) == 1) {
                    c = 0;
                } else {
                    c = 3;
                }
            } else if (dragon.currentPositionX > player.currentPositionX && dragon.currentPositionY < player.currentPositionY) {
                if (rand.nextInt(2) == 1) {
                    c = 0;
                } else {
                    c = 2;
                }
            } else if (dragon.currentPositionX == player.currentPositionX && dragon.currentPositionY > player.currentPositionY) {
                c = 3;
            } else if (dragon.currentPositionX == player.currentPositionX && dragon.currentPositionY < player.currentPositionY) {
                c = 2;
            } else if (dragon.currentPositionX > player.currentPositionX) {
                c = 0;
            } else if (dragon.currentPositionX < player.currentPositionX) {
                c = 1;
            }
            int posToChangeTo;
            if (c == 0) {
                posToChangeTo = dragon.currentPositionX - 1;
                wallHit = gridButtons[posToChangeTo][dragon.currentPositionY].getIcon() != null;
            } else if (c == 1) {
                posToChangeTo = dragon.currentPositionX + 1;
                wallHit = gridButtons[posToChangeTo][dragon.currentPositionY].getIcon() != null;
            } else if (c == 2) {
                posToChangeTo = dragon.currentPositionY + 1;
                wallHit = gridButtons[dragon.currentPositionX][posToChangeTo].getIcon() != null;
            } else {
                posToChangeTo = dragon.currentPositionY - 1;
                wallHit = gridButtons[dragon.currentPositionX][posToChangeTo].getIcon() != null;
            }
            if (!wallHit) {
                try {
                    switch (c) {
                        case 0: //up
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, --dragon.currentPositionX, dragon.currentPositionY);
                            break;
                        case 1: //down
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, ++dragon.currentPositionX, dragon.currentPositionY);
                            break;
                        case 2: //right
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, dragon.currentPositionX, ++dragon.currentPositionY);
                            break;
                        case 3: //left
                            moveDragon(dragon.currentPositionX, dragon.currentPositionY, dragon.currentPositionX, --dragon.currentPositionY);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                isBlocked = true;
                checkDragonMovement();
            }
        }
    }

    private void moveDragon(int dragonOldPositionX, int dragonOldPositionY, int dragonPositionX, int dragonPositionY) {
        try {
            //gridButtons[dragonOldPositionX][dragonOldPositionY].setVisible(false);
            gridButtons[dragonOldPositionX][dragonOldPositionY].setIcon(null);
            gridButtons[dragonOldPositionX][dragonOldPositionY].setEnabled(false);
            dragon.setCurrentPositionX(dragonPositionX);
            dragon.setCurrentPositionY(dragonPositionY);
            //gridButtons[dragonPositionX][dragonPositionY].setVisible(true);
            Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/dragon.png"));
            Image newImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            gridButtons[dragonPositionX][dragonPositionY].setIcon(new ImageIcon(newImg));
            gridButtons[dragonPositionX][dragonPositionY].setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderDragon() {
        try {
            //gridButtons[dragon.currentPositionX][dragon.currentPositionY].setVisible(true);
            Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/dragon.png"));
            Image newImg = img.getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            gridButtons[dragon.currentPositionX][dragon.currentPositionY].setIcon(new ImageIcon(newImg));
            gridButtons[dragon.currentPositionX][dragon.currentPositionY].setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderPlayer() {
        try {
            gridButtons[11][0].setVisible(true);
            Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/nathan_drake.png"));
            Image newImg = img.getScaledInstance(74, 69, java.awt.Image.SCALE_SMOOTH);
            gridButtons[11][0].setIcon(new ImageIcon(newImg));
            gridButtons[11][0].setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void movePlayer(int playerPositionX, int playerPositionY) {
        int currentPlayerPositionX = player.currentPositionX;
        int currentPlayerPositionY = player.currentPositionY;
        try {
            if (gridButtons[playerPositionX][playerPositionY].getIcon() == null || (playerPositionX == 0 && playerPositionY == 11)) { //check for walls
                player.setCurrentPositionX(playerPositionX);
                player.setCurrentPositionY(playerPositionY);
                gridButtons[playerPositionX][playerPositionY].setVisible(true);
                gridButtons[currentPlayerPositionX][currentPlayerPositionY].setVisible(false);
                gridButtons[currentPlayerPositionX][currentPlayerPositionY].setIcon(null);
                gridButtons[currentPlayerPositionX][currentPlayerPositionY].setEnabled(false);
                Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/nathan_drake.png"));
                Image newImg = img.getScaledInstance(74, 69, Image.SCALE_SMOOTH);
                gridButtons[playerPositionX][playerPositionY].setIcon(new ImageIcon(newImg));
                gridButtons[playerPositionX][playerPositionY].setEnabled(true);
            }
        } catch (Exception e) {
            player.setCurrentPositionX(currentPlayerPositionX);
            player.setCurrentPositionY(currentPlayerPositionY);
        }
    }

    void renderPlayerVisibilityRange() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i != 0 || j != 11) {
                    gridButtons[i][j].setVisible(false);
                    gridButtons[i][j].setBorder(null);
                    dragon.isVisible = false;
                }
            }
        }
        for (int i = player.currentPositionY - 2; i < player.currentPositionY + 3; i++) {
            managePlayerSight(player.currentPositionX - 2, i);
        }
        for (int i = player.currentPositionY - 2; i < player.currentPositionY + 3; i++) {
            managePlayerSight(player.currentPositionX - 1, i);
        }
        for (int i = player.currentPositionY - 2; i < player.currentPositionY + 3; i++) {
            managePlayerSight(player.currentPositionX, i);
        }
        for (int i = player.currentPositionY - 2; i < player.currentPositionY + 3; i++) {
            managePlayerSight(player.currentPositionX + 1, i);
        }
        for (int i = player.currentPositionY - 2; i < player.currentPositionY + 3; i++) {
            managePlayerSight(player.currentPositionX + 2, i);
        }
    }

    private void managePlayerSight(int i, int j) {
        try {
            gridButtons[i][j].setVisible(true);
            if (i == dragon.currentPositionX && j == dragon.currentPositionY) {
                dragon.isVisible = true;
            }
        } catch (ArrayIndexOutOfBoundsException ignored) {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void renderWalls(int i, int j) {
        try {
            if (gridButtons[i][j].getIcon() == null) { //avoiding wall placement on dragon
                Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/wall.png"));
                Image newImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                gridButtons[i][j].setIcon(new ImageIcon(newImg));
                gridButtons[i][j].setEnabled(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderArrow() {
        try {
            gridButtons[0][11].setVisible(true);
            Image img = ImageIO.read(getClass().getResource("/hu/antalnagy/labyrinth/resources/arrow.png"));
            Image newImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            gridButtons[0][11].setIcon(new ImageIcon(newImg));
            gridButtons[0][11].setEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTimerText() {
        timerPanel.setText("Elapsed time: " +
                ((System.nanoTime() - startTime) / 1000000000) + "." + ((System.nanoTime() - startTime) / 100000000 % 10)
                + " seconds");
    }

    private void renderGUI() {
        renderPlayer();
        renderArrow();
        renderDragon();
    }
}