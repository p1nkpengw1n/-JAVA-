package hu.antalnagy.labyrinth;

class Player {
    int currentPositionX;
    int currentPositionY;

    Player(int currentPositionX, int currentPositionY) {
        this.currentPositionX = currentPositionX;
        this.currentPositionY = currentPositionY;
    }

    void setCurrentPositionX(int currentPositionX) {
        this.currentPositionX = currentPositionX;
    }

    void setCurrentPositionY(int currentPositionY) {
        this.currentPositionY = currentPositionY;
    }
}
