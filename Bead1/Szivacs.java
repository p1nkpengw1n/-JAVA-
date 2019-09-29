package race;

public class Szivacs extends Leny {
    public Szivacs(String nev, int viz) {
        super(nev,viz,20);
    }

    @Override
    public void naposValtozas() {
        if(this.eletbenVan()) {
            vizLevonas(4);
        }
        
    }

    @Override
    public void felhosValtozas() {
        if(this.eletbenVan()) {
            vizLevonas(1);
            lepes(1);
        }
    }

    @Override
    public void esosValtozas() {
        if(this.eletbenVan()) {
            vizHozzaadas(6);
            lepes(3);
        }
    }

    @Override
    public String toString() {
        return this.eletbenVan() ? "Leny neve: " + nev + " rendelkezesre allo vizmennyiseg: " + viz + "\n"
            + "Megtett tav: " + tav : "A " + nev + " nevu leny elpusztult :-(";
    }
}