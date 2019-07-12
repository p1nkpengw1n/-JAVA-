public class HalmazMuveletekSOLVED {
    public static Halmaz egyesites (Halmaz a, Halmaz b) {

        Halmaz egyesitett = new Halmaz(a.getElemszam() + b.getElemszam());

        int kisebbElemszam = a.getElemszam() < b.getElemszam() ? a.getElemszam() : b.getElemszam();

        a.rendezes();
        b.rendezes();

        int i = 0;
        for( ; i < kisebbElemszam; i++) {
            if(!a.getElem(i).equals(b.getElem(i))) {
                egyesitett.ujElem(a.getElem(i));
                egyesitett.ujElem(b.getElem(i));
            }
            else egyesitett.ujElem(a.getElem(i));
        }
        if( kisebbElemszam == a.getElemszam() ) {
            while( i < b.getElemszam() ) {
                egyesitett.ujElem(b.getElem(i));
                i++;
            }
        }
        else {
            while( i < a.getElemszam() ) {
                egyesitett.ujElem(a.getElem(i));
                i++;
            }
        }

        return egyesitett;
    }

    public static Halmaz metszet (Halmaz a, Halmaz b) {

        int kisebbElemszam = a.getElemszam() < b.getElemszam() ? a.getElemszam() : b.getElemszam();
        Halmaz metszett = new Halmaz(kisebbElemszam);

        a.rendezes();
        b.rendezes();

        int i = 0;
        for( ; i < kisebbElemszam; i++) {
            if(a.getElem(i).equals(b.getElem(i))) {
                metszett.ujElem(a.getElem(i));
            }
        }

        return metszett;
    }
}
