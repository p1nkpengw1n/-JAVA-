package race;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.text.ParseException;
import java.util.stream.Stream;
import java.util.stream.IntStream; 
import java.nio.charset.Charset;
import java.lang.String;

public class Main {

    public static final Charset THE_CHARSET = Charset.forName("UTF-8");
    private static ArrayList<String> lines = new ArrayList<>();

    private static List<Leny> lenyek;

    private static String[] napok;

    public static void verseny(int elolenyekSzama, List<Leny> lenyek) {
        int j = 0;
        System.out.println("Verseny felallas:\n");
        kiir();
        System.out.println("\n");
        while(j < napok.length) {
            switch(napok[j]) {
                case "n":
                    System.out.println(j>0 ? j+1 + ".ik nap: *napos ido*\n" : "Elso nap: *napos ido*\n");
                    for(int i = 0; i < elolenyekSzama; i++) {
                        lenyek.get(i).naposValtozas();
                    }
                    break;
                case "f":
                    System.out.println(j>0 ? j+1 + ".ik nap: *felhos ido*\n" : "Elso nap: *felhos ido*\n");
                    for(int i = 0; i < elolenyekSzama; i++) {
                        lenyek.get(i).felhosValtozas();
                    }
                    break;
                case "e":
                    System.out.println(j>0 ? j+1 + ".ik nap: *esos ido*\n" : "Elso nap: *esos ido*\n");
                    for(int i = 0; i < elolenyekSzama; i++) {
                        lenyek.get(i).esosValtozas();
                    }
                    break;
                default:
                    throw new RuntimeException("Ismeretlen karakter a bemenet utolso soraban!\n");
            }
            kiir();
            ++j;
        }
        nyertesKiir(nyertesek());
    }

    private static void kiir() {
        for(Leny leny: lenyek) {
            System.out.println(leny.toString());
        }
        System.out.println("------------\n");
    }

    private static List<Leny> nyertesek() {
        List<Leny> nyertesek = new ArrayList<>();
        int maxTav = -1;
        for(Leny leny: lenyek) {
            if(leny.eletbenVan() && leny.getTav() > maxTav) {
                maxTav = leny.getTav();
            }
        }
        for(Leny leny: lenyek) {
            if(leny.eletbenVan() && leny.getTav() == maxTav) {
                nyertesek.add(leny);
            }
        }
        return nyertesek;
    }

    private static void nyertesKiir(List<Leny> nyertesekLenyek) {
        List<Leny> nyertesek = nyertesekLenyek;
        if(!nyertesek.isEmpty()) {
            if(nyertesek.size() > 1) {
                System.out.println("Holtverseny! A nyertesek: \n");
                for(Leny nyertes: nyertesek) {
                    System.out.println(nyertes.getNev() + "\n");
                }
            }
            else {
                System.out.println("A nyertes: \n\n" + nyertesek.get(0).getNev() + "\n");
            }
        }
        else {
            System.out.println("Nincs nyertes!\n");
        }
    }

    public static void main(String[] args) throws IOException {
        simFromUI();
        // testLines();
        // testLines2();
        // testLines3();
        // testLines4();
        // testLines5();
        // testLines6();
        // testLines7();
        // testEmptyLines();
    }

    private static void testLines() throws IOException {
        simulation("./lines.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert("Csuszo",nyertesek.get(0).getNev(),"Hiba a szimulacioban");
        myAssert("Siklo",nyertesek.get(1).getNev(),"Hiba a szimulacioban");
    }

    private static void testLines2() throws IOException {
        simulation("./lines2.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert(1,nyertesek.get(0).getViz(),"Hiba a szimulacioban");
        myAssertFalse(true,lenyek.get(1).eletbenVan(),"Hiba a szimulacioban");
    }

    private static void testLines3() throws IOException {
        simulation("./lines3.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert("Vandor",nyertesek.get(0).getNev(),"Hiba a szimulacioban");
        myAssertFalse(true,lenyek.get(1).getViz() > lenyek.get(0).getViz(),"Hiba a szimulacioban");
    }

    private static void testLines4() throws IOException {
        simulation("./lines4.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert("Vandor",nyertesek.get(0).getNev(),"Hiba a szimulacioban");
        myAssert("Maszo",nyertesek.get(1).getNev(),"Hiba a szimulacioban");
        myAssert(false,lenyek.get(6).eletbenVan(),"Hiba a szimulacioban");
        myAssert(1,lenyek.get(4).getViz(),"Hiba a szimulacioban");
        myAssert(19,lenyek.get(4).getTav(),"Hiba a szimulacioban");
    }

    private static void testLines5() throws IOException {
        simulation("./lines5.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert(true,nyertesek.isEmpty(),"Hiba a szimulacioban");
        myAssertFalse(true,lenyek.get(0).getViz() > 0,"Hiba a szimulacioban");
    }

    private static void testLines6() throws IOException {
        simulation("./lines6.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert("Kuszo",nyertesek.get(0).getNev(),"Hiba a szimulacioban");
        myAssert("Gyalogolo5",nyertesek.get(nyertesek.size()-2).getNev(),"Hiba a szimulacioban");
        myAssert(20,lenyek.get(lenyek.size()-1).getTav(),"Hiba a szimulacioban");
        myAssert(6,lenyek.get(lenyek.size()-1).getViz(),"Hiba a szimulacioban");
        myAssertFalse(true,lenyek.get(0).getViz() < nyertesek.get(0).getViz() && lenyek.get(2).getTav() > nyertesek.get(3).getTav(),"Hiba a szimulacioban");
    }

    private static void testLines7() throws IOException {
        simulation("./lines7.txt");
        List<Leny> nyertesek = new ArrayList<>(nyertesek());
        myAssert("Seta", nyertesek.get(0).getNev(),"Hiba a szimulacioban");
        myAssert(19,lenyek.get(0).getTav(),"Hiba a szimulacioban");
        myAssert(21,lenyek.get(1).getTav(),"Hiba a szimulacioban");
        myAssert(16,lenyek.get(2).getTav(),"Hiba a szimulacioban");
    }

    private static void testEmptyLines() throws IOException {
        simulation("./linesEmpty.txt");
    }

    private static void simFromUI() throws IOException {
        simulation("");
    }

    private static void simulation(String path) throws IOException {
        if(path.isEmpty()) {
            simulateFromUserInput();
            return;
        }
        final Path THE_PATH = Paths.get(path);
        try (Stream<String> stream = Files.lines(THE_PATH,THE_CHARSET)) {  
            stream.forEach(line -> lines.add(line));
        } catch(NoSuchFileException nsfe) {
            throw new FileNotFoundException("Az input file nem talalhato!\n");
        } catch(IOException ioe){
            throw new IOException("Hiba tortent beolvasas kozben!\n",ioe);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ismeretlen hiba.\n");
        }
        try {
            int elolenyekSzama = Integer.parseInt(lines.get(0));
            lenyek = new ArrayList<Leny>(elolenyekSzama);
            for(int i = 1; i <= elolenyekSzama; i++){
                String[] splitted = lines.get(i).split("\\s");
                String name = splitted[0];
                switch(splitted[1]) {
                    case "h":
                        lenyek.add(new Homokjaro(name, Integer.parseInt(splitted[2])));
                        break;
                    case "l":
                        lenyek.add(new Lepegeto(name, Integer.parseInt(splitted[2])));
                        break;
                    case "s":
                        lenyek.add(new Szivacs(name, Integer.parseInt(splitted[2])));
                        break;
                    default:
                        throw new RuntimeException("Ismeretlen karakter a bemeneti sor masodik tokenje!\n");
                }
            }
            napok = lines.get(elolenyekSzama+1).split("(?!^)");
            verseny(elolenyekSzama, lenyek);
        } catch(IndexOutOfBoundsException iooe) {
            throw new RuntimeException("Nincs eleg sor a bemenetben!\n", iooe);
        } catch(NumberFormatException nfe) {
            throw new RuntimeException("Nem szam a sor utolso tokenje!\n", nfe);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Hibas bemenet!\n", e);
        }
    }

    private static void simulateFromUserInput() throws IOException {
        System.out.println("Kerem adja meg a bemeneti file helyet: ");
        Scanner sc = new Scanner(System.in);
        final Path THE_PATH = Paths.get(sc.nextLine());

        try (Stream<String> stream = Files.lines(THE_PATH,THE_CHARSET)) {  
            stream.forEach(line -> lines.add(line));
        } catch(NoSuchFileException nsfe) {
            throw new FileNotFoundException("Az input file nem talalhato!\n");
        } catch(IOException ioe){
            throw new IOException("Hiba tortent beolvasas kozben!\n",ioe);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Ismeretlen hiba.\n");
        }
        try {
            int elolenyekSzama = Integer.parseInt(lines.get(0));
            lenyek = new ArrayList<Leny>(elolenyekSzama);
            for(int i = 1; i <= elolenyekSzama; i++){
                String[] splitted = lines.get(i).split("\\s");
                String name = splitted[0];
                switch(splitted[1]) {
                    case "h":
                        lenyek.add(new Homokjaro(name, Integer.parseInt(splitted[2])));
                        break;
                    case "l":
                        lenyek.add(new Lepegeto(name, Integer.parseInt(splitted[2])));
                        break;
                    case "s":
                        lenyek.add(new Szivacs(name, Integer.parseInt(splitted[2])));
                        break;
                    default:
                        throw new RuntimeException("Ismeretlen karakter a bemeneti sor masodik tokenje!\n");
                }
            }
            napok = lines.get(elolenyekSzama+1).split("(?!^)");
            verseny(elolenyekSzama, lenyek);
        } catch(IndexOutOfBoundsException iooe) {
            throw new RuntimeException("Nincs eleg sor a bemenetben!\n", iooe);
        } catch(NumberFormatException nfe) {
            throw new RuntimeException("Nem szam a sor utolso tokenje!\n", nfe);
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Hibas bemenet!\n", e);
        }
    }

    private static <T> void myAssert(T expected, T actual, String message) {
        myAssert(expected, actual, message, true);
    }
    private static <T> void myAssertFalse(T expected, T actual, String message) {
        myAssert(expected, actual, message, false);
    }
    
    private static <T> void myAssert(T expected, T actual, String message, boolean expectedValue) {
        if(expectedValue != Objects.equals(expected, actual)) {
            System.err.println("\t" + message);
            System.err.println(String.format("\t+Expected: %1$s\n\t-Actual: %2$s", expected, actual));
        }
    }
}