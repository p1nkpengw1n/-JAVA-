package race;

public class Homokjaro extends Leny {
    public Homokjaro(String nev, int viz) {
        super(nev,viz,8);
    }

    @Override
    public void naposValtozas() {
        if(this.eletbenVan()) {
            vizLevonas(1);
            lepes(3);
        }
    }
    @Override
    public void felhosValtozas() {
        if(this.eletbenVan()) {
            lepes(1);
        }
    }

    @Override
    public void esosValtozas() {
        if(this.eletbenVan()) {
            vizHozzaadas(3);
        }
    }

    @Override
    public String toString() {
        return this.eletbenVan() ? "Leny neve: " + nev + " rendelkezesre allo vizmennyiseg: " + viz + "\n"
            + "Megtett tav: " + tav : "A " + nev + " nevu leny elpusztult :-(";
    }
}