package hu.antalnagy.labyrinth;

class Game {

    Model m;

    Game(int level, long startTime) {
        View v = new View(startTime);
        new Controller(v);
        m = new Model(v, level);
    }
}
