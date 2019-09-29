package race;

public class Lepegeto extends Leny {
    public Lepegeto(String nev, int viz) {
        super(nev,viz,12);
    }

    @Override
    public void naposValtozas() {
        if(this.eletbenVan()) {
            vizLevonas(2);
            lepes(1);
        }
    }

    @Override
    public void felhosValtozas() {
        if(this.eletbenVan()) {
            vizLevonas(1);
            lepes(2);
        }
    }

    @Override
    public void esosValtozas() {
        if(this.eletbenVan()) {
            vizHozzaadas(3);
            lepes(1);
        }
    }

    @Override
    public String toString() {
        return this.eletbenVan() ? "Leny neve: " + nev + " rendelkezesre allo vizmennyiseg: " + viz + "\n"
            + "Megtett tav: " + tav : "A " + nev + " nevu leny elpusztult :-(";
    }
}