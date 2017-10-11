package vertSys;

import java.util.AbstractMap;
import java.util.Iterator;

/**
 * Created by Alexander Görisch on 10.10.2017.
 */
public class NumThread implements Runnable {
    private int nummer;

    protected NumThread(int nummer) {
        this.nummer = nummer;
    }

    public void run() {
        boolean found = false;
        Telefon buch = new Telefon();
        Iterator<AbstractMap.SimpleEntry> it = buch.iterator();
        while (it.hasNext()) {
            AbstractMap.SimpleEntry entry = it.next();
            if (entry.getValue().equals(nummer)) {
                System.out.println(entry.getKey() + " " + entry.getValue());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Kein Eintrag zu " + nummer);
        }
    }
}
