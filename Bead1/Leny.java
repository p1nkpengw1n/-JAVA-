package race;

public abstract class Leny {

    protected String nev;
    protected int viz;
    protected int maxViz;
    protected boolean el;
    protected int tav;

    public Leny(String nev, int viz, int maxViz) {
        this.nev = nev;
        this.viz = viz;
        this.maxViz = maxViz;
        this.el = true;
        this.tav = 0;
    }

    protected void vizLevonas(int menny) {
        setViz(this.viz - menny);
    }
    protected void vizHozzaadas(int menny) {
        setViz(this.viz + menny);
    }
    protected void lepes(int menny) {
        setTav(this.tav + menny);
    }

    public boolean eletbenVan() {
        return this.el;
    }

    private void setViz(int menny) {
        if(menny > this.maxViz) {
            this.viz = maxViz;
        }
        else if(menny <= 0) {
            this.viz = 0;
            this.el = false;
        }
        else {
            this.viz = menny;
        }
    }

    private void setTav(int menny) {
        this.tav = menny;
    }

    public int getTav() {
        return this.tav;
    }

    public String getNev() {
        return this.nev;
    }

    public int getViz() {
        return this.viz;
    }

    public abstract void naposValtozas();
    public abstract void felhosValtozas();
    public abstract void esosValtozas();
}