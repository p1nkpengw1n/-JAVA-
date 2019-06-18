public class HalmazMuveletek {
    public static Halmaz egyesites (Halmaz a, Halmaz b) {

        Halmaz egyesitett = new Halmaz(a.getKapacitas() + b.getKapacitas());

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

//    public static Halmaz metszet (Halmaz a, Halmaz b) {
//
//        /*TO-DO: implementáld a halmaz metszeteket,
//        csak közös elemek lehetnek az eredmény halmazban,
//         az egyesítéshez analóg módon rendezd először a két halmazt,
//         majd párhuzamosan járd be őket és vizsgálj egyenlőséget (akkor kell hozzáadni kizárólag az eredményhalmazhoz).
//         */
//    }
}
