public class Halmaz {

    private String[] elemek;
    private int kapacitas;
    private int elemszam;

    public Halmaz(int kapacitas) {
        elemek = new String[kapacitas];
        this.kapacitas = kapacitas;
    }

    public Halmaz(String[] tomb) {
        this.kapacitas = tomb.length;
        elemek = new String[kapacitas];
        for( int i = 0; i < kapacitas; i++) {
            ujElem(tomb[i]);
        }
    }

    public int getKapacitas() {
        return kapacitas;
    }

    public int getElemszam() {
        return elemszam;
    }

    public int elemPozicioja(String elem) {
        for(int i = 0; i < getElemszam(); i++) {
            if(elemek[i].equals(elem)) {
                return i;
            }
        }
        return -1;
    }

    public boolean letezikE(String elem) {
        if(elemPozicioja(elem) == -1) {
            return false;
        }
        return true;
    }

    public boolean ujElem(String elem) {
        if( getElemszam() == getKapacitas() || letezikE(elem) ) {
            return false;
        }
        elemek[elemszam] = elem;
        elemszam++;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(int i = 0; i < getElemszam(); i++) {
            sb.append(" ");
            sb.append(elemek[i]);
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }

    public String getElem(int poz) {
        if( getElemszam() < poz ) {
            throw new IllegalArgumentException(" A pozicio nem letezik! ");
        }
        return elemek[poz];
    }

    public boolean torolElem(String elem) {
        if(!letezikE(elem)) {
            return false;
        }
        int elemPoz = elemPozicioja(elem);
        //String toroltElem = elemek[elemPoz];
        for( int i = elemPoz; i < getElemszam()-1; i++) {
            elemek[i] = elemek[i+1];
        }
        elemszam--;
        return true;
    }

    public void rendezes() {
        if( getElemszam() == getKapacitas() ) {
            for( int i = 0; i < getKapacitas()-1; i++) {
                for(int j = i+1; j < getKapacitas(); j++) {
                    if(elemek[i].compareTo(elemek[j]) > 0) {
                        String temp = elemek[i];
                        elemek[i] = elemek[j];
                        elemek[j] = temp;
                    }
                }
            }
            int i=0;
            int j=0;
            int[] nums = new int[kapacitas];
            while(i < getKapacitas()){
                try {
                    nums[i] = Integer.parseInt(elemek[i]);
                    i++;
                }
                catch(NumberFormatException nfe) {
                    j=i;
                    i = getKapacitas();
                }
            }
            for( int ind = 0; ind < j-1; ind++) {
                for(int ind2 = ind+1; ind2 < j; ind2++) {
                    if(nums[ind] > nums[ind2]) {
                        int temp = nums[ind];
                        nums[ind] = nums[ind2];
                        nums[ind2] = temp;
                    }
                }
            }
            for( int ind = 0; ind < j; ind++) {
                String number = Integer.toString(nums[ind]);
                elemek[ind] = number;

                /* OR
                StringBuilder sb = new StringBuilder();
                sb.append(nums[ind]);
                elemek[ind] = sb.toString();
                 */
            }
        }
    }

    public static void main(String[] args) {

        Halmaz halmaz1 = new Halmaz(2);

        halmaz1.ujElem("alma");
        halmaz1.ujElem("korte");

        System.out.println(halmaz1);

        halmaz1.ujElem("szilva");
        halmaz1.ujElem("korte");

        System.out.println(halmaz1.elemPozicioja("korte"));

        halmaz1.torolElem("alma");

        System.out.println(halmaz1);

        String[] str = { "alma" , "eper", "alma", "dio" };
        Halmaz halmaz2 = new Halmaz(str);
        System.out.println(halmaz2);

        String[] str2 = { "19", "korte", "szilva", "barack", "243", "1000", "100"};
        Halmaz halmaz3 = new Halmaz(str2);
        halmaz3.rendezes();

        System.out.println(halmaz3);

        Halmaz hze = HalmazMuveletek.egyesites(halmaz2,halmaz3);
        System.out.println(hze);
    }

}
