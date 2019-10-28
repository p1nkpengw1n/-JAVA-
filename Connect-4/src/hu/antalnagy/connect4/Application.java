package hu.antalnagy.connect4;

public class Application {

    public static void main(String[] args) {
        newGame("SMALL");
    }

    static void newGame(String ts) {
        switch(ts) {
            case "LARGE": {
                View v = new View(TableSize.LARGE, 12,7);
                Model m = new Model(v);
                new Controller(v, m);
                break;
            }
            case "MEDIUM": {
                View v = new View(TableSize.MEDIUM, 10,6);
                Model m = new Model(v);
                new Controller(v,m);
                break;
            }
            case "SMALL": {
                View v = new View(TableSize.SMALL, 8,5);
                Model m = new Model(v);
                new Controller(v,m);
                break;
            }
        }

    }
}
