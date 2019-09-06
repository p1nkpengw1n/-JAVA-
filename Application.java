public class Application {
    public static void main(String[] args) {

        Model m = new Model();
        View v = new View(m);
        v.setVisible(true);
        int rounds = m.getM();
        while(rounds > 0) {
            try {
                Thread.sleep(700);
            }catch(InterruptedException ie) {
                System.out.println("Error in main(UI) thread");
            }
            v.updateUniverse();
            m.setM(--rounds);
            rounds = m.getM();
        }
    }
}