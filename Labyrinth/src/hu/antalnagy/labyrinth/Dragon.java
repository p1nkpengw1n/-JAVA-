package hu.antalnagy.labyrinth;

class Dragon {
    int currentPositionX;
    int currentPositionY;
    boolean isVisible;

    Dragon(int currentPositionX, int currentPositionY) {
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
